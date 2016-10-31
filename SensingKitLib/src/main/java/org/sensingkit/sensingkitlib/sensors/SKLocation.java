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

package org.sensingkit.sensingkitlib.sensors;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKLocationConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKLocationData;

public class SKLocation extends SKAbstractGoogleServicesSensor implements LocationListener {

    @SuppressWarnings("unused")
    private static final String TAG = SKLocation.class.getName();

    public SKLocation(final Context context, final SKLocationConfiguration configuration) throws SKException {
        super(context, SKSensorType.LOCATION, configuration);

        mClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void startSensing() {

        this.isSensing = true;

        mClient.connect();
    }

    @Override
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
        SKAbstractData data = new SKLocationData(System.currentTimeMillis(), location);

        // Submit sensor data object
        submitSensorData(data);
    }

    private void registerForLocationUpdates() {

        // Cast the configuration instance
        SKLocationConfiguration locationConfiguration = (SKLocationConfiguration)mConfiguration;

        // Configure the LocationRequest
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(locationConfiguration.getPriority());
        locationRequest.setInterval(locationConfiguration.getInterval());
        locationRequest.setFastestInterval(locationConfiguration.getFastestInterval());

        // Start the request
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, locationRequest, this);
    }

    private void unregisterForLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(mClient, this);
    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKLocationConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKLocation.",
                    SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Set the configuration
        super.setConfiguration(configuration);
    }

    @Override
    public SKConfiguration getConfiguration() {
        return new SKLocationConfiguration((SKLocationConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

}
