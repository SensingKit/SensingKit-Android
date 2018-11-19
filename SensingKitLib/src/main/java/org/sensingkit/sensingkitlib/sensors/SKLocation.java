/*
 * Copyright (c) 2014. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKLocationConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKLocationData;

public class SKLocation extends SKAbstractSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKLocation.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            if (locationResult == null) {
                return;
            }

            // Build the data object
            Location location = locationResult.getLastLocation();
            SKAbstractData data = new SKLocationData(System.currentTimeMillis(), location);

            // Submit sensor data object
            submitSensorData(data);
        }
    };

    public SKLocation(final Context context, final SKLocationConfiguration configuration) throws SKException {
        super(context, SKSensorType.LOCATION, configuration);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationRequest = LocationRequest.create();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void startSensing() {

        this.isSensing = true;

        // Start the request
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null);
    }

    @Override
    public void stopSensing() {

        mFusedLocationClient.removeLocationUpdates(mLocationCallback);

        this.isSensing = false;
    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKLocationConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKLocation.",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        // Set the configuration
        super.setConfiguration(configuration);

        // Cast the configuration instance
        SKLocationConfiguration locationConfiguration = (SKLocationConfiguration)mConfiguration;

        // Configure the LocationRequest
        mLocationRequest.setPriority(locationConfiguration.getPriority());
        mLocationRequest.setInterval(locationConfiguration.getInterval());
        mLocationRequest.setFastestInterval(locationConfiguration.getFastestInterval());
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
