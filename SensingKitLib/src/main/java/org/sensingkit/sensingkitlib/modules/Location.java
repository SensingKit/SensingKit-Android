/*
 * Copyright (c) 2014. Queen Mary University of London
 * Kleomenis Katevas, k.katevas@qmul.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit http://www.sensingkit.org
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

package org.sensingkit.sensingkitlib.modules;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorDataListener;
import org.sensingkit.sensingkitlib.model.data.AbstractData;
import org.sensingkit.sensingkitlib.model.data.DataInterface;
import org.sensingkit.sensingkitlib.model.data.LocationData;

public class Location extends AbstractGoogleServicesSensorModule implements LocationListener {

    @SuppressWarnings("unused")
    private static final String TAG = "Location";

    public Location(final Context context) throws SKException {
        super(context, SensorModuleType.LOCATION);

        mClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public boolean startSensing() {

        this.isSensing = true;

        mClient.connect();

        return true;
    }

    public void stopSensing() {

        unregisterForLocationUpdates();
        mClient.disconnect();

        this.isSensing = false;
    }

    @Override
    protected void serviceConnected()
    {
        Log.i(TAG, "GoogleApiClient Connected!");

        registerForLocationUpdates();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        // Build the data object
        AbstractData data = new LocationData(System.currentTimeMillis(), location);

        // If there is a significant change
        if (shouldPostSensorData(data)) {

            if (callbackList != null) {

                // CallBack with data as parameter
                for (SKSensorDataListener callback : callbackList) {
                    callback.onDataReceived(moduleType, data);
                }
            }
        }
    }

    private void registerForLocationUpdates() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, locationRequest, this);
    }

    private void unregisterForLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(mClient, this);

    }

    protected boolean shouldPostSensorData(AbstractData data) {

        // Always post sensor data
        return true;
    }

}
