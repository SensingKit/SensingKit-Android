/*
 * Copyright (c) 2016. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit https://www.sensingkit.org
 *
 * SensingKit-Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SensingKit-Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SensingKit-Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sensingkit.sensingkitlib.sensors;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKBeaconProximityConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKBeaconProximityConfiguration.SKBeaconType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKBeaconProximityCollectionData;
import org.sensingkit.sensingkitlib.data.SKBeaconProximityData;

import java.util.ArrayList;

@SuppressWarnings("ResourceType")
public class SKBeaconProximity extends SKAbstractSensor implements BeaconConsumer {

    @SuppressWarnings("unused")
    private static final String TAG = SKBeaconProximity.class.getSimpleName();

    private static final String BEACON_RANGING_IDENTIFIER = "org.sensingkit.beaconRangingIdentifier";

    private BeaconManager mBeaconManager;
    private Region mRegion;

    private final RangeNotifier mRangeNotifier = (beacons, region) -> {

        if (beacons.size() > 0) {

            // Get the timestamp
            long timestamp = System.currentTimeMillis();

            // Create the SKBeaconProximityData objects and add them to a new list
            ArrayList<SKBeaconProximityData> deviceData = new ArrayList<>(beacons.size());

            for (Beacon beacon : beacons) {
                deviceData.add(new SKBeaconProximityData(timestamp, beacon));
            }

            // Build the data object
            SKAbstractData data = new SKBeaconProximityCollectionData(timestamp, deviceData);

            // Submit sensor data object
            submitSensorData(data);
        }
    };

    public SKBeaconProximity(final @NonNull Context context, final @NonNull SKBeaconProximityConfiguration configuration) throws SKException {
        super(context, SKSensorType.BEACON_PROXIMITY, configuration);
    }

    @Override
    protected void initSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "initSensing [" + mSensorType.getName() + "]");}

        // Init BeaconManager
        mBeaconManager = BeaconManager.getInstanceForApplication(mApplicationContext);
        //BeaconManager.setDebug(true);  // Debug only

        // configure sensor
        updateSensor(context, sensorType, configuration);
    }

    @Override
    protected void updateSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "updateSensing [" + mSensorType.getName() + "]");}

        // Unbind first if needed
        if (mBeaconManager.isBound(this)) {
            mBeaconManager.unbind(this);
        }

        // Cast the configuration instance
        SKBeaconProximityConfiguration beaconProximityConfiguration = (SKBeaconProximityConfiguration) configuration;

        // Configure BeaconParsers
        mBeaconManager.getBeaconParsers().clear();
        String layout = SKBeaconProximity.getBeaconLayout(beaconProximityConfiguration.getBeaconType());
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(layout));

        // Configure Region
        Identifier id1 = (beaconProximityConfiguration.getFilterId1() != null? Identifier.parse(beaconProximityConfiguration.getFilterId1()) : null);
        Identifier id2 = (beaconProximityConfiguration.getFilterId2() != null? Identifier.parse(beaconProximityConfiguration.getFilterId2()) : null);
        Identifier id3 = (beaconProximityConfiguration.getFilterId3() != null? Identifier.parse(beaconProximityConfiguration.getFilterId3()) : null);
        mRegion = new Region(BEACON_RANGING_IDENTIFIER, id1, id2, id3);

        // Bind
        mBeaconManager.bind(this);
    }

    @Override
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKBeaconProximityConfiguration((SKBeaconProximityConfiguration)mConfiguration);
    }

    @Override
    public void onBeaconServiceConnect() {
        if (shouldDebugSensor()) {Log.i(TAG, "onBeaconServiceConnect [" + mSensorType.getName() + "]");}
    }

    @Override
    public Context getApplicationContext() {
        return mApplicationContext;
    }

    @Override
    public void unbindService(final @NonNull ServiceConnection serviceConnection) {
        if (shouldDebugSensor()) {Log.i(TAG, "unbindService [" + mSensorType.getName() + "]");}

        mApplicationContext.unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(final @NonNull Intent intent, final @NonNull ServiceConnection serviceConnection, final int i) {
        if (shouldDebugSensor()) {Log.i(TAG, "bindService [" + mSensorType.getName() + "]");}

        return mApplicationContext.bindService(intent, serviceConnection, i);
    }

    @Override
    protected boolean shouldPostSensorData(final @NonNull SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public void startSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "startSensing [" + mSensorType.getName() + "]");}

        super.startSensing();

        // Register the callback
        mBeaconManager.removeAllRangeNotifiers();
        mBeaconManager.addRangeNotifier(mRangeNotifier);

        try {
            mBeaconManager.startRangingBeaconsInRegion(mRegion);

        } catch (RemoteException ex) {
            Log.e(TAG, ex.toString());
            throw new SKException(TAG, "Beacon Proximity sensor could not be started on this device.", SKExceptionErrorCode.SENSOR_ERROR);
        }
    }

    @Override
    public void stopSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "stopSensing [" + mSensorType.getName() + "]");}

        try {
            mBeaconManager.stopRangingBeaconsInRegion(mRegion);
            super.stopSensing();

        } catch (RemoteException ex) {
            throw new SKException(TAG, "Beacon Proximity sensor could not be stopped.", SKExceptionErrorCode.SENSOR_ERROR);
        }
    }

    @Override
    public void sensorDeregistered() {
        super.sensorDeregistered();

        // Remove callback
        mBeaconManager.removeRangeNotifier(mRangeNotifier);

        // Release sensor
        mBeaconManager.unbind(this);
    }

    private static String getBeaconLayout(final SKBeaconType beaconType) {

        switch (beaconType) {

            case ALTBEACON:
                return BeaconParser.ALTBEACON_LAYOUT;

            case IBEACON:
                return "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

            case EDDYSTONE_UID:
                return BeaconParser.EDDYSTONE_UID_LAYOUT;

            //case EDDYSTONE_TLM:
            //    return BeaconParser.EDDYSTONE_TLM_LAYOUT;

            //case EDDYSTONE_URL:
            //    return BeaconParser.EDDYSTONE_URL_LAYOUT;

            default:
                throw new RuntimeException();
        }
    }

    @Override
    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    }
}
