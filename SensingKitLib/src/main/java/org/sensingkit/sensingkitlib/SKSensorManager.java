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
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;

import org.sensingkit.sensingkitlib.data.SKSensorData;
import org.sensingkit.sensingkitlib.sensors.*;

public class SKSensorManager {

    @SuppressWarnings("unused")
    private static final String TAG = SKSensorManager.class.getName();

    private static final int TOTAL_SENSORS = 19;

    private static SKSensorManager sSensorManager;
    private final Context mApplicationContext;

    private final SparseArray<SKAbstractSensor> mSensors;

    public static SKSensorManager getSensorManager(final Context context) throws SKException {

        if (context == null) {
            throw new SKException(TAG, "Context cannot be null.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (sSensorManager == null) {
            sSensorManager = new SKSensorManager(context);
        }

        return sSensorManager;
    }

    private SKSensorManager(final Context context) throws SKException {

        mApplicationContext = context;

        // Init Sensor Array
        mSensors = new SparseArray<>(TOTAL_SENSORS);
    }


    /**
     *  Initializes and registers a sensor into the library with a default sensor configuration.
     *
     *  @param sensorType The type of the sensor that will be initialized and registered in the library.
     */
    public void registerSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Register sensor: " + SKSensorUtilities.getSensorInString(sensorType) + ".");

        if (isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "SensorModule is already registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Register the Sensor
        int sensorIndex = sensorType.ordinal();
        SKAbstractSensor sensor = createSensor(sensorType);
        mSensors.put(sensorIndex, sensor);
    }

    /**
     *  Deregisters a sensor from the library. Sensor should not be actively sensing when this method is called. All previously subscribed blocks will also be unsubscribed.
     *
     *  @param sensorType The type of the sensor that will be deregistered.
     */
    public void deregisterSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Deregister sensor: " + SKSensorUtilities.getSensorInString(sensorType) + ".");

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor is currently sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Clear all Callbacks from that sensor
        getSensor(sensorType).unsubscribeAllSensorDataListeners();

        // Deregister the Sensor
        int sensorIndex = sensorType.ordinal();
        mSensors.delete(sensorIndex);
    }

    /**
     *  A Boolean value that indicates whether the sensor is registered.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is registered or FALSE if it is not.
     */
    public boolean isSensorRegistered(SKSensorType sensorType) throws SKException {

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
    public boolean isSensorSensing(SKSensorType sensorType) throws SKException {

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return getSensor(sensorType).isSensing();
    }

    protected SKAbstractSensor getSensor(SKSensorType sensorType) throws SKException {

        if (!isSensorRegistered(sensorType)) {
            throw new SKException(TAG, "Sensor is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        int sensorIndex = sensorType.ordinal();
        return mSensors.get(sensorIndex);
    }

    protected SKAbstractSensor createSensor(SKSensorType sensorType) throws SKException {

        SKAbstractSensor sensor;

        switch (sensorType) {

            case ACCELEROMETER:
                sensor = new SKAccelerometer(mApplicationContext);
                break;

            case GRAVITY:
                sensor = new SKGravity(mApplicationContext);
                break;

            case LINEAR_ACCELERATION:
                sensor = new SKLinearAcceleration(mApplicationContext);
                break;

            case GYROSCOPE:
                sensor = new SKGyroscope(mApplicationContext);
                break;

            case ROTATION:
                sensor = new SKRotation(mApplicationContext);
                break;

            case MAGNETOMETER:
                sensor = new SKMagnetometer(mApplicationContext);
                break;

            case AMBIENT_TEMPERATURE:
                sensor = new SKAmbientTemperature(mApplicationContext);
                break;

            case STEP_DETECTOR:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    throw new SKException(TAG, "STEP_DETECTOR requires Android KitKat or greater.", SKExceptionErrorCode.UNKNOWN_ERROR);
                }

                sensor = new SKStepDetector(mApplicationContext);
                break;

            case STEP_COUNTER:

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    throw new SKException(TAG, "STEP_COUNTER requires Android KitKat or greater.", SKExceptionErrorCode.UNKNOWN_ERROR);
                }

                sensor = new SKStepCounter(mApplicationContext);
                break;

            case LIGHT:
                sensor = new SKLight(mApplicationContext);
                break;

            case LOCATION:
                sensor = new SKLocation(mApplicationContext);
                break;

            case MOTION_ACTIVITY:
                sensor = new SKMotionActivity(mApplicationContext);
                break;

            case BATTERY:
                sensor = new SKBattery(mApplicationContext);
                break;

            case SCREEN_STATUS:
                sensor = new SKScreenStatus(mApplicationContext);
                break;

            case MICROPHONE:
                sensor = new SKMicrophone(mApplicationContext);
                break;

            case AUDIO_LEVEL:
                sensor = new SKAudioLevel(mApplicationContext);
                break;

            case BLUETOOTH:
                sensor = new SKBluetooth(mApplicationContext);
                break;

            case HUMIDITY:
                sensor = new SKHumidity(mApplicationContext);
                break;

            case AIR_PRESSURE:
                sensor = new SKAirPressure(mApplicationContext);
                break;

            // Don't forget the break; here

            default:
                throw new SKException(TAG, "Unknown Sensor", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        return sensor;
    }

    /**
     * Get data from the sensor
     *
     * @param sensorType of type SKSensorType
     *
     * @return an SKSensorData object
     *
     * @throws SKException
     */
    public SKSensorData getDataFromSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Get data from sensor: " + SKSensorUtilities.getSensorInString(sensorType) + ".");

        throw new SKException(TAG, "This feature is not supported just yet!", SKExceptionErrorCode.UNKNOWN_ERROR);
    }

    /**
     *  Subscribes for sensor updates using a specified event listener.
     *
     *  @param sensorType  The type of the sensor that the data handler will be subscribed to.
     *
     *  @param dataListener    An event listener that is invoked with each update to handle new sensor data. The block must conform to the SKSensorDataListener type.
     */
    public void subscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Subscribe to sensor: " + SKSensorUtilities.getSensorInString(sensorType) + ".");

        getSensor(sensorType).subscribeSensorDataListener(dataListener);
    }

    /**
     *  Unsubscribes an event listener.
     *
     *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
     *  @param dataListener The event listener to be unsubscribed.
     */
    public void unsubscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {

        Log.i(TAG, "Unsubscribe from sensor: " + SKSensorUtilities.getSensorInString(sensorType) + ".");

        getSensor(sensorType).unsubscribeSensorDataListener(dataListener);
    }

    /**
     *  Unsubscribes all event listenerss.
     *
     *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
     */

    public void unsubscribeAllSensorDataListeners(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Unsubscribe from all sensors.");

        getSensor(sensorType).unsubscribeAllSensorDataListeners();
    }

    /**
     *  Starts continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be started.
     */
    public void startContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Start sensing with sensor: " + SKSensorUtilities.getSensorInString(sensorType) + ".");

        if (isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor [" + SKSensorUtilities.getSensorInString(sensorType) + "] is already sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Start Sensing
        getSensor(sensorType).startSensing();
    }

    /**
     *  Stops continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be stopped.
     */
    public void stopContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {

        Log.i(TAG, "Stop sensing with sensor: " + SKSensorUtilities.getSensorInString(sensorType) + ".");

        if (!isSensorSensing(sensorType)) {
            throw new SKException(TAG, "Sensor [" + SKSensorUtilities.getSensorInString(sensorType) + "] is already not sensing.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        SKSensorInterface sensor = getSensor(sensorType);

        // Stop Sensing
        sensor.stopSensing();
    }

    /**
     *  Starts continuous sensing with all registered sensors.
     */
    public void startContinuousSensingWithAllRegisteredSensors() throws SKException {

        for (int i = 0; i < TOTAL_SENSORS; i++){
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
    public void stopContinuousSensingWithAllRegisteredSensors() throws SKException {
        for (int i = 0; i < TOTAL_SENSORS; i++) {
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

}
