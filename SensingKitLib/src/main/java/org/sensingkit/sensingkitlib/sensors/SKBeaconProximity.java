/*
 * Copyright (c) 2016. Queen Mary University of London
 * Kleomenis Katevas, k.katevas@qmul.ac.uk
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
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKBeaconProximityConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKBeaconProximityCollectionData;
import org.sensingkit.sensingkitlib.data.SKBeaconProximityData;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("ResourceType")
public class SKBeaconProximity extends SKAbstractSensor implements BeaconConsumer {

    private BeaconManager mBeaconManager;
    private Region mRegion;

    @SuppressWarnings("unused")
    private static final String TAG = SKBluetooth.class.getName();

    public SKBeaconProximity(final Context context, final SKBeaconProximityConfiguration configuration) throws SKException {
        super(context, SKSensorType.BEACON_PROXIMITY, configuration);

        mBeaconManager = BeaconManager.getInstanceForApplication(context);

        if (mBeaconManager == null) {
            throw new SKException(TAG, "Beacon Proximity sensor is not supported from the device.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        //mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        mRegion = new Region("myRangingUniqueId", null, null, null);  // Add UUID

        mBeaconManager.bind(this);

        // TODO: mBeaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {

        mBeaconManager.addRangeNotifier(new RangeNotifier() {

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
        });
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

        }

    }

    @Override
    public void stopSensing() {

        try {
            mBeaconManager.stopRangingBeaconsInRegion(mRegion);
        } catch (RemoteException ex) {

        }

        this.isSensing = false;
    }
}
