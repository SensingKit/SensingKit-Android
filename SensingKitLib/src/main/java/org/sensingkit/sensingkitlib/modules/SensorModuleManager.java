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
import android.util.SparseArray;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorDataListener;

public class SensorModuleManager {

    @SuppressWarnings("unused")
    private static final String TAG = "SensorModuleManager";

    private static final int TOTAL_SENSOR_MODULES = 15;

    private static SensorModuleManager sSensorModuleManager;
    private final Context mApplicationContext;

    private SparseArray<AbstractSensorModule> mSensors;


    public static SensorModuleManager getSensorManager(final Context context) throws SKException {

        if (context == null) {
            throw new SKException(TAG, "Context cannot be null.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (sSensorModuleManager == null) {
            sSensorModuleManager = new SensorModuleManager(context);
        }

        return sSensorModuleManager;
    }

    private SensorModuleManager(final Context context) throws SKException {

        mApplicationContext = context;

        // Init Sensor Array
        mSensors = new SparseArray<>(TOTAL_SENSOR_MODULES);
    }

    protected void registerSensorModule(SensorModuleType moduleType) throws SKException {

        if (isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is already registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Register the SensorModule
        int sensorIndex = moduleType.ordinal();
        AbstractSensorModule sensorModule = createSensorModule(moduleType);
        mSensors.put(sensorIndex, sensorModule);
    }

    protected void deregisterSensorModule(SensorModuleType moduleType) throws SKException {

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Unregister the SensorModule
        int sensorIndex = moduleType.ordinal();
        mSensors.delete(sensorIndex);
    }

    protected boolean isSensorModuleRegistered(SensorModuleType moduleType) throws SKException {

        return (getSensorModule(moduleType) != null);
    }

    protected AbstractSensorModule getSensorModule(SensorModuleType moduleType) {

        int sensorIndex = moduleType.ordinal();
        return mSensors.get(sensorIndex);
    }

    protected AbstractSensorModule createSensorModule(SensorModuleType moduleType) throws SKException {

        AbstractSensorModule sensorModule;

        switch (moduleType) {

            case ACCELEROMETER:
                sensorModule = new Accelerometer(mApplicationContext);
                break;

            case GRAVITY:
                sensorModule = new Gravity(mApplicationContext);
                break;

            case LINEAR_ACCELERATION:
                sensorModule = new LinearAcceleration(mApplicationContext);
                break;

            case GYROSCOPE:
                sensorModule = new Gyroscope(mApplicationContext);
                break;

            case ROTATION:
                sensorModule = new Rotation(mApplicationContext);
                break;

            case MAGNETOMETER:
                sensorModule = new Magnetometer(mApplicationContext);
                break;

            case AMBIENT_TEMPERATURE:
                sensorModule = new AmbientTemperature(mApplicationContext);
                break;

            case STEP_DETECTOR:
                sensorModule = new StepDetector(mApplicationContext);
                break;

            case STEP_COUNTER:
                sensorModule = new StepCounter(mApplicationContext);
                break;

            case LIGHT:
                sensorModule = new Light(mApplicationContext);
                break;

            case LOCATION:
                sensorModule = new Location(mApplicationContext);
                break;

            case ACTIVITY:
                sensorModule = new Activity(mApplicationContext);
                break;

            case BATTERY:
                sensorModule = new Battery(mApplicationContext);
                break;

            case SCREEN_STATUS:
                sensorModule = new ScreenStatus(mApplicationContext);
                break;

            case AUDIO_LEVEL:
                sensorModule = new AudioLevel(mApplicationContext);
                break;

            // Don't forget the break; here

            default:
                throw new SKException(TAG, "Unknown SensorModule", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return sensorModule;
    }

    public void getDataFromSensor(SensorModuleType moduleType) throws SKException {
        throw new SKException(TAG, "This feature is not supported just yet!", SKExceptionErrorCode.UNKNOWN_ERROR);
    }

    public void subscribeToSensor(SensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Subscribe to sensor: " + SensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (!isSensorModuleRegistered(moduleType)) {
            registerSensorModule(moduleType);
        }

        AbstractSensorModule sensorModule = getSensorModule(moduleType);
        sensorModule.registerCallback(dataListener);
    }

    public void unsubscribeFromSensor(SensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Unsubscribe from sensor: " + SensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        AbstractSensorModule sensorModule = getSensorModule(moduleType);
        sensorModule.unregisterCallback(dataListener);
    }

    public void unsubscribeAllFromSensor(SensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Unsubscribe from all sensors.");

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        AbstractSensorModule sensorModule = getSensorModule(moduleType);
        sensorModule.clearCallbacks();
    }

    public void startContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Start sensing with sensor: " + SensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (!isSensorModuleRegistered(moduleType)) {
            registerSensorModule(moduleType);
        }

        SensorModuleInterface sensorModule = getSensorModule(moduleType);

        if (sensorModule.isSensing()) {
            throw new SKException(TAG, "SensorModule is already sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Start Sensing
        sensorModule.startSensing();
    }

    public void stopContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Stop sensing with sensor: " + SensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        SensorModuleInterface sensorModule = getSensorModule(moduleType);

        if (!sensorModule.isSensing()) {
            throw new SKException(TAG, "SensorModule is already not sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Stop Sensing
        sensorModule.stopSensing();

        // Clear all callbacks
        sensorModule.clearCallbacks();

        // Deregister the module
        deregisterSensorModule(moduleType);
    }

    // keeps sensor registered, keeps callbacks alive
    public void pauseContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Pause sensor: " + SensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        if (!isSensorModuleRegistered(moduleType)) {
            throw new SKException(TAG, "SensorModule is not registered", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        SensorModuleInterface sensorModule = getSensorModule(moduleType);

        if (!sensorModule.isSensing()) {
            throw new SKException(TAG, "SensorModule is already not sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Stop Sensing
        sensorModule.stopSensing();
    }

    // Continue from Pause
    public void unpauseContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException {

        Log.i(TAG, "Unpause sensor: " + SensorModuleUtilities.getSensorModuleInString(moduleType) + ".");

        SensorModuleInterface sensorModule = getSensorModule(moduleType);

        if (sensorModule.isSensing()) {
            throw new SKException(TAG, "SensorModule is already sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Start Sensing
        sensorModule.startSensing();
    }

}
