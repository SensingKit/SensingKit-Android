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

package org.sensingkit.sensingkitlib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;

import org.sensingkit.sensingkitlib.sensors.*;
import org.sensingkit.sensingkitlib.configuration.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"StaticFieldLeak"})
class SKSensorManager {

    @SuppressWarnings("unused")
    private static final String TAG = SKSensorManager.class.getSimpleName();

    private static SKSensorManager sSensorManager;
    private final Context mApplicationContext;

    private final SparseArray<SKAbstractSensor> mSensors;

    static SKSensorManager getSensorManager(final @NonNull Context context) {

        if (sSensorManager == null) {
            sSensorManager = new SKSensorManager(context);
        }

        return sSensorManager;
    }

    private SKSensorManager(final @NonNull Context context) {

        mApplicationContext = context;

        // Init Sensor Array
        mSensors = new SparseArray<>(SKSensorType.getLength());
    }


    /**
     *  Initializes and registers a sensor into the library with a default sensor configuration.
     *
     *  @param sensorType The type of the sensor that will be initialized and registered in the library.
     *  @param configuration TODO
     */
    void registerSensor(final SKSensorType sensorType, SKConfiguration configuration) throws SKException {

        Log.i(TAG, "Register sensor: " + sensorType.getName() + ".");

        if (!SKUtilities.isSensorAvailable(sensorType, mApplicationContext)) {
            throw new SKException(TAG, "Sensor is not available in the device.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
        }

        if (isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is already registered.", SKExceptionErrorCode.SENSOR_ALREADY_REGISTERED);
        }

        // If configuration was not provided, get the Default
        if (configuration == null) {
            configuration = SKSensorManager.defaultConfigurationForSensor(sensorType);
        }

        // Register the Sensor
        int sensorIndex = sensorType.ordinal();
        SKAbstractSensor sensor = createSensor(sensorType, configuration);
        mSensors.put(sensorIndex, sensor);
    }

    /**
     *  Deregisters a sensor from the library. Sensor should not be actively sensing when this method is called. All previously subscribed blocks will also be unsubscribed.
     *
     *  @param sensorType The type of the sensor that will be deregistered.
     */
    void deregisterSensor(final SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Deregister sensor: " + sensorType.getName() + ".");

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.SENSOR_NOT_REGISTERED);
        }

        if (isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor is currently sensing.", SKExceptionErrorCode.SENSOR_CURRENTLY_SENSING);
        }

        // Clear all Callbacks from that sensor
        getSensor(sensorType).unsubscribeAllSensorDataHandlers();

        // Deregister the Sensor
        getSensor(sensorType).sensorDeregistered();
        int sensorIndex = sensorType.ordinal();
        mSensors.delete(sensorIndex);
    }

    void setConfiguration(SKConfiguration configuration, final SKSensorType sensorType) throws SKException {

        if (!SKUtilities.isSensorAvailable(sensorType, mApplicationContext)) {
            throw new SKException(TAG, "Sensor is not available in the device.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
        }

        // If configuration was not provided, get the Default
        if (configuration == null) {
            configuration = SKSensorManager.defaultConfigurationForSensor(sensorType);
        }

        // TODO
    }

    SKConfiguration getConfiguration(final SKSensorType sensorType) throws SKException {

        if (!SKUtilities.isSensorAvailable(sensorType, mApplicationContext)) {
            throw new SKException(TAG, "Sensor is not available in the device.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
        }

        // TODO
        return null;
    }

    boolean isPermissionGrantedForSensor(final SKSensorType sensorType) throws SKException {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "Request permissions is only required for Android 6.0 (API level 23) or greater.");
            return true;
        }

        SKSensor sensor = getSensor(sensorType);
        String permission = sensor.getRequiredPermission();

        // if null, no permission is required
        if (permission == null) {
            return true;
        }
        else {
            // else check permission
            return SKUtilities.checkPermission(permission, mApplicationContext);
        }
    }

    void requestPermissionForSensor(final SKSensorType sensorType, final @NonNull Activity activity) throws SKException {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "Request permissions is only required for Android 6.0 (API level 23) or greater.");
            return;
        }

        SKSensor sensor = getSensor(sensorType);
        String permission = sensor.getRequiredPermission();
        if (permission != null) {
            // request permissions
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
        }

    }

    // TODO documentation
    void requestPermissionForAllRegisteredSensors(final @NonNull Activity activity) throws SKException {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "Request permissions is only required for Android 6.0 (API level 23) or greater.");
            return;
        }

        // List that hold all permissions
        List<String> permissionsList = new ArrayList<>();

        for (int i = 0; i < SKSensorType.getLength(); i++) {

            SKSensor sensor = mSensors.get(i);
            if (sensor != null) {

                // append permission
                permissionsList.add(sensor.getRequiredPermission());
            }
        }

        // request permissions
        if (permissionsList.size() > 0) {

            // convert list to array
            String[] permissions = permissionsList.toArray(new String[0]);

            // request permissions
            ActivityCompat.requestPermissions(activity, permissions, 0);
        }
    }

    /**
     *  A Boolean value that indicates whether the sensor is registered.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is registered or FALSE if it is not.
     */
    boolean isSensorRegistered(final SKSensorType sensorType) {

        int sensorIndex = sensorType.ordinal();
        return (mSensors.get(sensorIndex) != null);
    }

    /**
     *  A Boolean value that indicates whether the sensor is currently sensing.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is currently sensing or FALSE if it is not.
     */
    boolean isSensorSensing(final SKSensorType sensorType) throws SKException {

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.SENSOR_NOT_REGISTERED);
        }

        return getSensor(sensorType).isSensing();
    }

    private SKAbstractSensor getSensor(final SKSensorType sensorType) throws SKException {

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.SENSOR_NOT_REGISTERED);
        }

        int sensorIndex = sensorType.ordinal();
        return mSensors.get(sensorIndex);
    }

    private SKAbstractSensor createSensor(final SKSensorType sensorType, final SKConfiguration configuration) throws SKException {

        SKAbstractSensor sensor;

        switch (sensorType) {

            case ACCELEROMETER:
                sensor = new SKAccelerometer(mApplicationContext, (SKAccelerometerConfiguration)configuration);
                break;

            case GRAVITY:
                sensor = new SKGravity(mApplicationContext, (SKGravityConfiguration)configuration);
                break;

            case LINEAR_ACCELERATION:
                sensor = new SKLinearAcceleration(mApplicationContext, (SKLinearAccelerationConfiguration)configuration);
                break;

            case GYROSCOPE:
                sensor = new SKGyroscope(mApplicationContext, (SKGyroscopeConfiguration)configuration);
                break;

            case ROTATION:
                sensor = new SKRotation(mApplicationContext, (SKRotationConfiguration)configuration);
                break;

            case MAGNETOMETER:
                sensor = new SKMagnetometer(mApplicationContext, (SKMagnetometerConfiguration)configuration);
                break;

            case AMBIENT_TEMPERATURE:
                sensor = new SKAmbientTemperature(mApplicationContext, (SKAmbientTemperatureConfiguration)configuration);
                break;

            case STEP_DETECTOR:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    throw new SKException(TAG, "STEP_DETECTOR requires Android KitKat (19) or greater.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
                }

                sensor = new SKStepDetector(mApplicationContext, (SKStepDetectorConfiguration)configuration);
                break;

            case STEP_COUNTER:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    throw new SKException(TAG, "STEP_COUNTER requires Android KitKat (19) or greater.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
                }

                sensor = new SKStepCounter(mApplicationContext, (SKStepCounterConfiguration)configuration);
                break;

            case LIGHT:
                sensor = new SKLight(mApplicationContext, (SKLightConfiguration)configuration);
                break;

            case LOCATION:
                sensor = new SKLocation(mApplicationContext, (SKLocationConfiguration)configuration);
                break;

            case MOTION_ACTIVITY:
                sensor = new SKMotionActivity(mApplicationContext, (SKMotionActivityConfiguration)configuration);
                break;

            case BATTERY_STATUS:
                sensor = new SKBatteryStatus(mApplicationContext, (SKBatteryStatusConfiguration)configuration);
                break;

            case SCREEN_STATUS:
                sensor = new SKScreenStatus(mApplicationContext, (SKScreenStatusConfiguration)configuration);
                break;

            case MICROPHONE:
                sensor = new SKMicrophone(mApplicationContext, (SKMicrophoneConfiguration)configuration);
                break;

            case AUDIO_LEVEL:
                sensor = new SKAudioLevel(mApplicationContext, (SKAudioLevelConfiguration)configuration);
                break;

            case BLUETOOTH:
                sensor = new SKBluetooth(mApplicationContext, (SKBluetoothConfiguration)configuration);
                break;

            case BEACON_PROXIMITY:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    throw new SKException(TAG, "BEACON_PROXIMITY requires Android Jelly Bean (18) or greater.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
                }

                sensor = new SKBeaconProximity(mApplicationContext, (SKBeaconProximityConfiguration)configuration);
                break;

            case HUMIDITY:
                sensor = new SKHumidity(mApplicationContext, (SKHumidityConfiguration)configuration);
                break;

            case BAROMETER:
                sensor = new SKBarometer(mApplicationContext, (SKBarometerConfiguration)configuration);
                break;

            case NOTIFICATION:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    throw new SKException(TAG, "NOTIFICATION requires Android Jelly Bean (18) or greater.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
                }

                sensor = new SKNotification(mApplicationContext, (SKNotificationConfiguration)configuration);
                break;

            // Don't forget the break; here

            default:
                throw new RuntimeException();
        }

        return sensor;
    }

    private static SKConfiguration defaultConfigurationForSensor(final SKSensorType sensorType) {

        SKConfiguration configuration;

        switch (sensorType) {

            case ACCELEROMETER:
                configuration = new SKAccelerometerConfiguration();
                break;

            case GRAVITY:
                configuration = new SKGravityConfiguration();
                break;

            case LINEAR_ACCELERATION:
                configuration = new SKLinearAccelerationConfiguration();
                break;

            case GYROSCOPE:
                configuration = new SKGyroscopeConfiguration();
                break;

            case ROTATION:
                configuration = new SKRotationConfiguration();
                break;

            case MAGNETOMETER:
                configuration = new SKMagnetometerConfiguration();
                break;

            case AMBIENT_TEMPERATURE:
                configuration = new SKAmbientTemperatureConfiguration();
                break;

            case STEP_DETECTOR:
                configuration = new SKStepDetectorConfiguration();
                break;

            case STEP_COUNTER:
                configuration = new SKStepCounterConfiguration();
                break;

            case LIGHT:
                configuration = new SKLightConfiguration();
                break;

            case LOCATION:
                configuration = new SKLocationConfiguration();
                break;

            case MOTION_ACTIVITY:
                configuration = new SKMotionActivityConfiguration();
                break;

            case BATTERY_STATUS:
                configuration = new SKBatteryStatusConfiguration();
                break;

            case SCREEN_STATUS:
                configuration = new SKScreenStatusConfiguration();
                break;

            case MICROPHONE:
                configuration = new SKMicrophoneConfiguration();
                break;

            case AUDIO_LEVEL:
                configuration = new SKAudioLevelConfiguration();
                break;

            case BLUETOOTH:
                configuration = new SKBluetoothConfiguration();
                break;

            case BEACON_PROXIMITY:
                configuration = new SKBeaconProximityConfiguration();
                break;

            case HUMIDITY:
                configuration = new SKHumidityConfiguration();
                break;

            case BAROMETER:
                configuration = new SKBarometerConfiguration();
                break;

            case NOTIFICATION:
                configuration = new SKNotificationConfiguration();
                break;

            // Don't forget the break; here

            default:
                throw new RuntimeException();
        }

        return configuration;
    }

    void subscribeSensorDataHandler(final SKSensorType sensorType, final SKSensorDataHandler dataHandler) throws SKException {

        Log.i(TAG, "Subscribe to sensor: " + sensorType.getName() + ".");

        getSensor(sensorType).subscribeSensorDataHandler(dataHandler);
    }

    void unsubscribeSensorDataHandler(final SKSensorType sensorType, final SKSensorDataHandler dataHandler) throws SKException {

        Log.i(TAG, "Unsubscribe from sensor: " + sensorType.getName() + ".");

        getSensor(sensorType).unsubscribeSensorDataHandler(dataHandler);
    }

    void unsubscribeAllSensorDataHandlers(final SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Unsubscribe from all sensors.");

        getSensor(sensorType).unsubscribeAllSensorDataHandlers();
    }

    /**
     *  Starts continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be started.
     */
    void startContinuousSensingWithSensor(final SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Start sensing with sensor: " + sensorType.getName() + ".");

        if (isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor [" + sensorType.getName() + "] is already sensing.", SKExceptionErrorCode.SENSOR_CURRENTLY_SENSING);
        }

        // Start Sensing
        getSensor(sensorType).startSensing();
    }

    /**
     *  Stops continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be stopped.
     */
    void stopContinuousSensingWithSensor(final SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Stop sensing with sensor: " + sensorType.getName() + ".");

        if (!isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor [" + sensorType.getName() + "] is already not sensing.", SKExceptionErrorCode.SENSOR_CURRENTLY_NOT_SENSING);
        }

        SKSensor sensor = getSensor(sensorType);

        // Stop Sensing
        sensor.stopSensing();
    }

    /**
     *  Starts continuous sensing with all registered sensors.
     */
    void startContinuousSensingWithAllRegisteredSensors() throws SKException {

        for (int i = 0; i < SKSensorType.getLength(); i++){
            if(mSensors.get(i) != null){
                Log.i(TAG, "Start sensing with sensor: " + mSensors.get(i).getSensorName() + ".");

                if (isSensorSensing(mSensors.get(i).getSensorType())) {
                    throw new SKException(TAG, "Sensor [" + mSensors.get(i).getSensorName() + "] is already sensing.", SKExceptionErrorCode.SENSOR_CURRENTLY_SENSING);
                }

                // Start Sensing
                mSensors.get(i).startSensing();
            }
        }
    }

    /**
     *  Stops continuous sensing with all registered sensors.
     */
    void stopContinuousSensingWithAllRegisteredSensors() throws SKException {
        for (int i = 0; i < SKSensorType.getLength(); i++) {
            if (mSensors.get(i) != null) {
                Log.i(TAG, "Stop sensing with sensor: " + mSensors.get(i).getSensorName() + ".");

                if (!isSensorSensing(mSensors.get(i).getSensorType())) {
                    throw new SKException(TAG, "Sensor [" + mSensors.get(i).getSensorName() + "] is already not sensing.", SKExceptionErrorCode.SENSOR_CURRENTLY_NOT_SENSING);
                }

                // Stop Sensing
                mSensors.get(i).stopSensing();
            }
        }
    }

}
