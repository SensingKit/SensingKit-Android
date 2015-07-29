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

package org.sensingkit.sensingkitlib;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import org.sensingkit.sensingkitlib.data.SKSensorData;
import org.sensingkit.sensingkitlib.modules.*;

public class SKSensorModuleManager {

    @SuppressWarnings("unused")
    private static final String TAG = "SKSensorModuleManager";

    private static final int TOTAL_SENSOR_MODULES = 17;

    private static SKSensorModuleManager sSensorModuleManager;
    private final Context mApplicationContext;

    private final SparseArray<SKAbstractSensorModule> mSensors;

    public static SKSensorModuleManager getSensorManager(final Context context) throws SKException {

        if (context == null) {
            throw new SKException(TAG, "Context cannot be null.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (sSensorModuleManager == null) {
            sSensorModuleManager = new SKSensorModuleManager(context);
        }

        return sSensorModuleManager;
    }

    private SKSensorModuleManager(final Context context) throws SKException {

        mApplicationContext = context;

        // Init Sensor Array
        mSensors = new SparseArray<>(TOTAL_SENSOR_MODULES);
    }

    public void registerSensorModule(SKSensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Register sensor: " + SKSensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is already registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Register the SensorModule
        int sensorIndex = moduleType.ordinal();
        SKAbstractSensorModule sensorModule = createSensorModule(moduleType);
        mSensors.put(sensorIndex, sensorModule);
    }

    public void deregisterSensorModule(SKSensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Deregister sensor: " + SKSensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (isSensorModuleSensing(moduleType)) {
            throw new SKException(TAG, "SensorModule is currently sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Clear all Callbacks from that sensor
        getSensorModule(moduleType).unsubscribeAllSensorDataListeners();

        // Deregister the SensorModule
        int sensorIndex = moduleType.ordinal();
        mSensors.delete(sensorIndex);
    }

    public boolean isSensorModuleRegistered(SKSensorModuleType moduleType) throws SKException {

        int sensorIndex = moduleType.ordinal();
        return (mSensors.get(sensorIndex) != null);
    }

    public boolean isSensorModuleSensing(SKSensorModuleType moduleType) throws SKException {

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return getSensorModule(moduleType).isSensing();
    }

    protected SKAbstractSensorModule getSensorModule(SKSensorModuleType moduleType) throws SKException {

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        int sensorIndex = moduleType.ordinal();
        return mSensors.get(sensorIndex);
    }

    protected SKAbstractSensorModule createSensorModule(SKSensorModuleType moduleType) throws SKException {

        SKAbstractSensorModule sensorModule;

        switch (moduleType) {

            case ACCELEROMETER:
                sensorModule = new SKAccelerometer(mApplicationContext);
                break;

            case GRAVITY:
                sensorModule = new SKGravity(mApplicationContext);
                break;

            case LINEAR_ACCELERATION:
                sensorModule = new SKLinearAcceleration(mApplicationContext);
                break;

            case GYROSCOPE:
                sensorModule = new SKGyroscope(mApplicationContext);
                break;

            case ROTATION:
                sensorModule = new SKRotation(mApplicationContext);
                break;

            case MAGNETOMETER:
                sensorModule = new SKMagnetometer(mApplicationContext);
                break;

            case AMBIENT_TEMPERATURE:
                sensorModule = new SKAmbientTemperature(mApplicationContext);
                break;

            case STEP_DETECTOR:
                sensorModule = new SKStepDetector(mApplicationContext);
                break;

            case STEP_COUNTER:
                sensorModule = new SKStepCounter(mApplicationContext);
                break;

            case LIGHT:
                sensorModule = new SKLight(mApplicationContext);
                break;

            case LOCATION:
                sensorModule = new SKLocation(mApplicationContext);
                break;

            case ACTIVITY:
                sensorModule = new SKActivity(mApplicationContext);
                break;

            case BATTERY:
                sensorModule = new SKBattery(mApplicationContext);
                break;

            case SCREEN_STATUS:
                sensorModule = new SKScreenStatus(mApplicationContext);
                break;

            case AUDIO_RECORDER:
                sensorModule = new SKAudioRecorder(mApplicationContext);
                break;

            case AUDIO_LEVEL:
                sensorModule = new SKAudioLevel(mApplicationContext);
                break;

            case BLUETOOTH:
                sensorModule = new SKBluetooth(mApplicationContext);
                break;

            // Don't forget the break; here

            default:
                throw new SKException(TAG, "Unknown SensorModule", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return sensorModule;
    }

    public SKSensorData getDataFromSensor(SKSensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Get data from sensor: " + SKSensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        throw new SKException(TAG, "This feature is not supported just yet!", SKExceptionErrorCode.UNKNOWN_ERROR);
    }

    public void subscribeSensorDataListener(SKSensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Subscribe to sensor: " + SKSensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        getSensorModule(moduleType).subscribeSensorDataListener(dataListener);
    }

    public void unsubscribeSensorDataListener(SKSensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Unsubscribe from sensor: " + SKSensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        getSensorModule(moduleType).unsubscribeSensorDataListener(dataListener);
    }

    public void unsubscribeAllSensorDataListeners(SKSensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Unsubscribe from all sensors.");

        getSensorModule(moduleType).unsubscribeAllSensorDataListeners();
    }

    public void startContinuousSensingWithSensor(SKSensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Start sensing with sensor: " + SKSensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (isSensorModuleSensing(moduleType)) {
            throw new SKException(TAG, "SensorModule is already sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Start Sensing
        getSensorModule(moduleType).startSensing();
    }

    public void stopContinuousSensingWithSensor(SKSensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Stop sensing with sensor: " + SKSensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (!isSensorModuleSensing(moduleType)) {
            throw new SKException(TAG, "SensorModule is already not sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        SKSensorModuleInterface sensorModule = getSensorModule(moduleType);

        // Stop Sensing
        sensorModule.stopSensing();
    }

}
