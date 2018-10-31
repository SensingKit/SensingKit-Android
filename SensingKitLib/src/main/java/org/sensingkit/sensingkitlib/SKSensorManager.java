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

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;

import org.sensingkit.sensingkitlib.sensors.*;
import org.sensingkit.sensingkitlib.data.*;
import org.sensingkit.sensingkitlib.configuration.*;

@SuppressWarnings({"StaticFieldLeak"})
class SKSensorManager {

    @SuppressWarnings("unused")
    private static final String TAG = SKSensorManager.class.getName();

    private static SKSensorManager sSensorManager;
    private final Context mApplicationContext;

    private final SparseArray<SKAbstractSensor> mSensors;

    static SKSensorManager getSensorManager(final Context context) throws SKException {

        if (context == null) {
            throw new SKException(TAG, "Context cannot be null.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (sSensorManager == null) {
            sSensorManager = new SKSensorManager(context);
        }

        return sSensorManager;
    }

    private SKSensorManager(final Context context) {

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
    void registerSensor(SKSensorType sensorType, SKConfiguration configuration) throws SKException {

        Log.i(TAG, "Register sensor: " + sensorType.getName() + ".");

        if (!isSensorAvailable(sensorType)) {
            throw new SKException(TAG, "Sensor is not available in the device.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "SensorModule is already registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
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
    void deregisterSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Deregister sensor: " + sensorType.getName() + ".");

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor is currently sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Clear all Callbacks from that sensor
        getSensor(sensorType).unsubscribeAllSensorDataListeners();

        // Deregister the Sensor
        getSensor(sensorType).sensorDeregestered();
        int sensorIndex = sensorType.ordinal();
        mSensors.delete(sensorIndex);
    }

    void setConfiguration(SKConfiguration configuration, SKSensorType sensorType) throws SKException {

        if (!isSensorAvailable(sensorType)) {
            throw new SKException(TAG, "Sensor is not available in the device.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // If configuration was not provided, get the Default
        if (configuration == null) {
            configuration = SKSensorManager.defaultConfigurationForSensor(sensorType);
        }

        // TODO
    }

    SKConfiguration getConfiguration(SKSensorType sensorType) throws SKException {

        if (!isSensorAvailable(sensorType)) {
            throw new SKException(TAG, "Sensor is not available.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // TODO
        return null;
    }

    /**
     *  A Boolean value that indicates whether the sensor is available on the device.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is available on the device, or FALSE if it is not.
     */
    boolean isSensorAvailable(SKSensorType sensorType) throws SKException {

        // Get package manager
        PackageManager packageManager = mApplicationContext.getPackageManager();

        switch (sensorType) {

            case ACCELEROMETER:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);

            case GRAVITY:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);

            case LINEAR_ACCELERATION:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);

            case GYROSCOPE:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);

            case ROTATION:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);

            case MAGNETOMETER:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);

            case AMBIENT_TEMPERATURE:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE);

            case STEP_DETECTOR:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);

            case STEP_COUNTER:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);

            case LIGHT:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);

            case LOCATION:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION);

            case MOTION_ACTIVITY:
                return SKMotionActivity.isGooglePlayServicesAvailable(mApplicationContext);

            case BATTERY_STATUS:
                return true;

            case SCREEN_STATUS:
                return true;

            case MICROPHONE:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);

            case AUDIO_LEVEL:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);

            case BLUETOOTH:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);

            case BEACON_PROXIMITY:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);

            case HUMIDITY:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY);

            case BAROMETER:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER);

            case NOTIFICATION:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;

            default:
                throw new SKException(TAG, "Unknown Sensor", SKExceptionErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     *  A Boolean value that indicates whether the sensor is registered.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is registered or FALSE if it is not.
     */
    boolean isSensorRegistered(SKSensorType sensorType) {

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
    boolean isSensorSensing(SKSensorType sensorType) throws SKException {

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return getSensor(sensorType).isSensing();
    }

    private SKAbstractSensor getSensor(SKSensorType sensorType) throws SKException {

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        int sensorIndex = sensorType.ordinal();
        return mSensors.get(sensorIndex);
    }

    private SKAbstractSensor createSensor(SKSensorType sensorType, SKConfiguration configuration) throws SKException {

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
                    throw new SKException(TAG, "STEP_DETECTOR requires Android KitKat (19) or greater.", SKExceptionErrorCode.UNKNOWN_ERROR);
                }

                sensor = new SKStepDetector(mApplicationContext, (SKStepDetectorConfiguration)configuration);
                break;

            case STEP_COUNTER:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    throw new SKException(TAG, "STEP_COUNTER requires Android KitKat (19) or greater.", SKExceptionErrorCode.UNKNOWN_ERROR);
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
                    throw new SKException(TAG, "BEACON_PROXIMITY requires Android Jelly Bean (18) or greater.", SKExceptionErrorCode.UNKNOWN_ERROR);
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
                sensor = new SKNotification(mApplicationContext, (SKNotificationConfiguration)configuration);
                break;

            // Don't forget the break; here

            default:
                throw new SKException(TAG, "Unknown Sensor", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return sensor;
    }

    private static SKConfiguration defaultConfigurationForSensor(SKSensorType sensorType) throws SKException {

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
                throw new SKException(TAG, "Unknown Sensor", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return configuration;
    }

    /**
     *  Subscribes for sensor updates using a specified event listener.
     *
     *  @param sensorType  The type of the sensor that the data handler will be subscribed to.
     *
     *  @param dataListener    An event listener that is invoked with each update to handle new sensor data. The block must conform to the SKSensorDataListener type.
     */
    void subscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Subscribe to sensor: " + sensorType.getName() + ".");

        getSensor(sensorType).subscribeSensorDataListener(dataListener);
    }

    /**
     *  Unsubscribes an event listener.
     *
     *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
     *  @param dataListener The event listener to be unsubscribed.
     */
    void unsubscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Unsubscribe from sensor: " + sensorType.getName() + ".");

        getSensor(sensorType).unsubscribeSensorDataListener(dataListener);
    }

    /**
     *  Unsubscribes all event listenerss.
     *
     *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
     */

    void unsubscribeAllSensorDataListeners(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Unsubscribe from all sensors.");

        getSensor(sensorType).unsubscribeAllSensorDataListeners();
    }

    /**
     *  Starts continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be started.
     */
    void startContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Start sensing with sensor: " + sensorType.getName() + ".");

        if (isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor [" + sensorType.getName() + "] is already sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Start Sensing
        getSensor(sensorType).startSensing();
    }

    /**
     *  Stops continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be stopped.
     */
    void stopContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Stop sensing with sensor: " + sensorType.getName() + ".");

        if (!isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor [" + sensorType.getName() + "] is already not sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
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
                    throw new SKException(TAG, "Sensor [" + mSensors.get(i).getSensorName() + "] is already sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
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
                    throw new SKException(TAG, "Sensor [" + mSensors.get(i).getSensorName() + "] is already not sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
                }

                // Stop Sensing
                mSensors.get(i).stopSensing();
            }
        }
    }

    /**
     *  Return a string with a CSV formatted header that describes the data of the particular sensor.
     */
    static String csvHeaderForSensor(SKSensorType sensorType) throws SKException {

        switch (sensorType) {

            case ACCELEROMETER:
                return SKAccelerometerData.csvHeader();

            case GRAVITY:
                return SKGravityData.csvHeader();

            case LINEAR_ACCELERATION:
                return SKLinearAccelerationData.csvHeader();

            case GYROSCOPE:
                return SKGyroscopeData.csvHeader();

            case ROTATION:
                return SKRotationData.csvHeader();

            case MAGNETOMETER:
                return SKMagnetometerData.csvHeader();

            case AMBIENT_TEMPERATURE:
                return SKAmbientTemperatureData.csvHeader();

            case STEP_DETECTOR:
                return SKStepDetectorData.csvHeader();

            case STEP_COUNTER:
                return SKStepCounterData.csvHeader();

            case LIGHT:
                return SKLightData.csvHeader();

            case LOCATION:
                return SKLocationData.csvHeader();

            case MOTION_ACTIVITY:
                return SKMotionActivityData.csvHeader();

            case BATTERY_STATUS:
                return SKBatteryStatusData.csvHeader();

            case SCREEN_STATUS:
                return SKScreenStatusData.csvHeader();

            case MICROPHONE:
                return SKMicrophoneData.csvHeader();

            case AUDIO_LEVEL:
                return SKAudioLevelData.csvHeader();

            case BLUETOOTH:
                return SKBluetoothData.csvHeader();

            case BEACON_PROXIMITY:
                return SKBeaconProximityData.csvHeader();

            case HUMIDITY:
                return SKHumidityData.csvHeader();

            case BAROMETER:
                return SKBarometerData.csvHeader();

            case NOTIFICATION:
                return SKNotificationData.csvHeader();

            default:
                throw new SKException(TAG, "Unknown Sensor", SKExceptionErrorCode.UNKNOWN_ERROR);
        }
    }

}
