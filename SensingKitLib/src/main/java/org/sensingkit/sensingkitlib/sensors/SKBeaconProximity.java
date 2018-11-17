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

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKBeaconProximityConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKBeaconProximityCollectionData;
import org.sensingkit.sensingkitlib.data.SKBeaconProximityData;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("ResourceType")
public class SKBeaconProximity extends SKAbstractSensor implements BeaconConsumer {

    @SuppressWarnings("unused")
    private static final String TAG = SKBeaconProximity.class.getName();

    private static final String BEACON_IDENTIFIER = "org.sensingkit.beaconIdentifier";

    private BeaconManager mBeaconManager;
    private Region mRegion;

    private final RangeNotifier mRangeNotifier = new RangeNotifier() {

        @Override
        public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

            if (!beacons.isEmpty()) {

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
        }
    };

    public SKBeaconProximity(final Context context, final SKBeaconProximityConfiguration configuration) throws SKException {
        super(context, SKSensorType.BEACON_PROXIMITY, configuration);

    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKBeaconProximityConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKBeaconProximity.",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        // Set the configuration
        super.setConfiguration(configuration);

        // Init MediaRecorder
        if (mBeaconManager == null) {
            mBeaconManager = BeaconManager.getInstanceForApplication(mApplicationContext);
        }
        else {
            // Reset Beacon Parsers
            mBeaconManager.getBeaconParsers().clear();
        }

        // Cast the configuration instance
        SKBeaconProximityConfiguration beaconProximityConfiguration = (SKBeaconProximityConfiguration)configuration;

        // Configure BeaconParsers
        String layout = SKBeaconProximity.getBeaconLayout(beaconProximityConfiguration.getBeaconType());

        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(layout));

        // Configure Region
        mRegion = new Region(
                BEACON_IDENTIFIER,
                beaconProximityConfiguration.getFilterId1(),
                beaconProximityConfiguration.getFilterId2(),
                beaconProximityConfiguration.getFilterId3());

        // Bind
        mBeaconManager.bind(this);
    }

    @Override
    public SKConfiguration getConfiguration() {
        return new SKBeaconProximityConfiguration((SKBeaconProximityConfiguration)mConfiguration);
    }

    @Override
    public void onBeaconServiceConnect() {

        // Register the callback
        mBeaconManager.addRangeNotifier(mRangeNotifier);
    }

    @Override
    public Context getApplicationContext() {
        return mApplicationContext;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        mApplicationContext.unbindService(serviceConnection);
    }

    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return mApplicationContext.bindService(intent, serviceConnection, i);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public void startSensing() throws SKException {

        this.isSensing = true;

        try {
            mBeaconManager.startRangingBeaconsInRegion(mRegion);

        } catch (RemoteException ex) {
            Log.e(TAG, ex.toString());
            throw new SKException(TAG, "Beacon Proximity sensor could not be started on this device.", SKExceptionErrorCode.SENSOR_ERROR);
        }

    }

    @Override
    public void stopSensing() throws SKException {

        try {
            mBeaconManager.stopRangingBeaconsInRegion(mRegion);
            this.isSensing = false;

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

    private static String getBeaconLayout(final int beaconType) {

        switch (beaconType) {

            case SKBeaconProximityConfiguration.BeaconType.ALTBEACON:
                return "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";

            case SKBeaconProximityConfiguration.BeaconType.IBEACON:
                return "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

            case SKBeaconProximityConfiguration.BeaconType.EDDYSTONE_UID:
                return "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19";

            //case SKBeaconProximityConfiguration.BeaconType.EDDYSTONE_TLM:
            //    return "x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15";

            //case SKBeaconProximityConfiguration.BeaconType.EDDYSTONE_URL:
            //    return "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-21v";

            default:
                throw new RuntimeException();
        }
    }
}
