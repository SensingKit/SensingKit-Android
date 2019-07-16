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

import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorDataHandler;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public abstract class SKAbstractSensor implements SKSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKAbstractSensor.class.getSimpleName();

    protected final Context mApplicationContext;
    protected final SKSensorType mSensorType;
    protected boolean isSensing = false;
    protected SKConfiguration mConfiguration;

    private ArrayList<SKSensorDataHandler> mSensorDataListeners;

    protected SKAbstractSensor(final @NonNull Context context, final SKSensorType sensorType, final @NonNull SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!configuration.isValidForSensor(sensorType)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor " + sensorType.getName() + ".",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        this.mApplicationContext = context;
        this.mSensorType = sensorType;
        this.mConfiguration = configuration;

        // call sensor init method
        initSensor(context, sensorType, configuration);
    }

    protected abstract void initSensor(final @NonNull Context context, final SKSensorType sensorType, final @NonNull SKConfiguration configuration) throws SKException;

    @SuppressWarnings("unused")
    protected abstract void updateSensor(final @NonNull Context context, final SKSensorType sensorType, final @NonNull SKConfiguration configuration) throws SKException;

    @CallSuper
    public void startSensing() throws SKException {
        this.isSensing = true;
    }

    @CallSuper
    public void stopSensing() throws SKException {
        this.isSensing = false;
    }

    public boolean isSensing() {
        return isSensing;
    }

    public SKSensorType getSensorType() {
        return this.mSensorType;
    }

    @NonNull
    public String getSensorName() {
        return mSensorType.getName();
    }

    @Override
    public void setConfiguration(final @NonNull SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!configuration.isValidForSensor(mSensorType)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor " + mSensorType.getName() + ".",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        this.mConfiguration = configuration;

        // call update sensor configuration
        updateSensor(mApplicationContext, mSensorType, configuration);
    }

    public void subscribeSensorDataHandler(final @NonNull SKSensorDataHandler handler) throws SKException {

        // Init the list
        if (this.mSensorDataListeners == null) {
            this.mSensorDataListeners = new ArrayList<>();
        }

        // Register the callback
        if (this.mSensorDataListeners.contains(handler)) {
            throw new SKException(TAG, "SKSensorDataHandler already registered.", SKExceptionErrorCode.DATA_HANDLER_ALREADY_REGISTERED);
        }

        this.mSensorDataListeners.add(handler);
    }

    public void unsubscribeSensorDataHandler(final @NonNull SKSensorDataHandler handler) throws SKException {

        // Unregister the callback
        if (this.mSensorDataListeners == null || !this.mSensorDataListeners.remove(handler)) {
            throw new SKException(TAG, "SKSensorDataHandler is not registered.", SKExceptionErrorCode.DATA_HANDLER_NOT_REGISTERED);
        }

        // Delete the callBackList if it is empty
        if (this.mSensorDataListeners.size() == 0) {
            this.mSensorDataListeners = null;
        }
    }

    public void unsubscribeAllSensorDataHandlers() {

       // Clear all callbacks
       if (this.mSensorDataListeners != null) {
           this.mSensorDataListeners.clear();
           this.mSensorDataListeners = null;
       }
    }

    protected abstract boolean shouldPostSensorData(final @NonNull SKAbstractData data);

    protected boolean shouldDebugSensor() {
        return mConfiguration.getDebugStatus();
    }

    protected void submitSensorData(final @NonNull SKAbstractData data) {

        // If there is a significant change
        if (shouldPostSensorData(data)) {

            if (mSensorDataListeners != null) {

                // CallBack with data as parameter
                for (SKSensorDataHandler callback : mSensorDataListeners) {
                    callback.onDataReceived(mSensorType, data);
                }
            }
        }
    }

    @Override
    public void sensorDeregistered() {
        // Override this method in the sensor subclass if needed.
    }

    @Override
    public String[] getRequiredPermissions() {
        // Override this method in the sensor subclass if a special permission is needed.
        return new String[]{};
    }
}
