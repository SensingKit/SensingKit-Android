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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.sensingkit.sensingkitlib.SKException;
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

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            if (locationResult == null) {
                if (shouldDebugSensor()) {Log.i(TAG, "locationResult is null");}
                return;
            }

            // Build the data object
            Location location = locationResult.getLastLocation();
            SKAbstractData data = new SKLocationData(System.currentTimeMillis(), location);

            // Submit sensor data object
            submitSensorData(data);
        }
    };

    public SKLocation(final @NonNull Context context, final @NonNull SKLocationConfiguration configuration) throws SKException {
        super(context, SKSensorType.LOCATION, configuration);
    }

    @Override
    protected void initSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "initSensing [" + mSensorType.getName() + "]");}

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        // configure sensor
        updateSensor(context, sensorType, configuration);
    }

    @Override
    protected void updateSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "updateSensing [" + mSensorType.getName() + "]");}

        // Cast the configuration instance
        SKLocationConfiguration locationConfiguration = (SKLocationConfiguration)mConfiguration;

        // Configure the LocationRequest
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(locationConfiguration.getPriority().getPriorityCode());
        mLocationRequest.setInterval(locationConfiguration.getInterval());
        mLocationRequest.setFastestInterval(locationConfiguration.getFastestInterval());
    }

    @SuppressLint("MissingPermission")
    @Override
    public void startSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "startSensing [" + mSensorType.getName() + "]");}

        super.startSensing();

        // Start the request
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null);
    }

    @Override
    public void stopSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "stopSensing [" + mSensorType.getName() + "]");}

        mFusedLocationClient.removeLocationUpdates(mLocationCallback);

        super.stopSensing();
    }

    @Override
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKLocationConfiguration((SKLocationConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(final @NonNull SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    }

}
