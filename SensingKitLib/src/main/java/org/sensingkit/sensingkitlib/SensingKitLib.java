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
import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.sensors.SKSensorUtilities;

@SuppressWarnings({"StaticFieldLeak"})
public class SensingKitLib implements SensingKitLibInterface {

    @SuppressWarnings("unused")
    private static final String TAG = SensingKitLib.class.getSimpleName();
    private static SensingKitLib sSensingKitLib;
    private final SKSensorManager mSensorManager;

    /**
     *
     * @param context
     * @return
     */
    @SuppressWarnings("unused")
    public static SensingKitLibInterface sharedSensingKitLib(final @NonNull Context context) {

        if (sSensingKitLib == null) {
            sSensingKitLib = new SensingKitLib(context);
        }

        return sSensingKitLib;
    }

    /**
     *
     * @param context
     */
    private SensingKitLib(final @NonNull Context context) {
        mSensorManager = SKSensorManager.getSensorManager(context);
    }

    /**
     *  A Boolean value that indicates whether the sensor is available on the device.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is available on the device, or FALSE if it is not.
     */
    @Override
    public boolean isSensorAvailable(final SKSensorType sensorType) {
        return mSensorManager.isSensorAvailable(sensorType);
    }

    /**
     *  A Boolean value that indicates whether the sensor is registered.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is registered or FALSE if it is not.
     */
    @Override
    public boolean isSensorRegistered(final SKSensorType sensorType) {
        return mSensorManager.isSensorRegistered(sensorType);
    }

    /**
     *  A Boolean value that indicates whether the sensor is currently sensing.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is currently sensing or FALSE if it is not.
     */
    @Override
    public boolean isSensorSensing(final SKSensorType sensorType) throws SKException {
        return mSensorManager.isSensorSensing(sensorType);
    }

    /**
     *  Initializes and registers a sensor into the library with a default sensor configuration.
     *
     *  @param sensorType The type of the sensor that will be initialized and registered in the library.
     */
    @Override
    public void registerSensor(final SKSensorType sensorType) throws SKException {
        registerSensor(sensorType, null);
    }

    @Override
    public void registerSensor(final SKSensorType sensorType, final SKConfiguration configuration) throws SKException {
        mSensorManager.registerSensor(sensorType, configuration);
    }

    /**
     *  Deregisters a sensor from the library. Sensor should not be actively sensing when this method is called. All previously subscribed blocks will also be unsubscribed.
     *
     *  @param sensorType The type of the sensor that will be deregistered.
     */
    @Override
    public void deregisterSensor(final SKSensorType sensorType) throws SKException {
        mSensorManager.deregisterSensor(sensorType);
    }

    /**
     *
     * @param configuration A configuration object that conforms to SKConfiguration. If no configuration is specified, it will default to a pre-determined sensor configuration.
     * @param sensorType The type of the sensor that will be configured.
     * @throws SKException
     */
    @Override
    public void setConfiguration(final SKConfiguration configuration, final SKSensorType sensorType) throws SKException {
        mSensorManager.setConfiguration(configuration, sensorType);
    }

    /**
     *
     * @param sensorType The type of the sensor.
     * @return
     * @throws SKException
     */
    @Override
    public @NonNull SKConfiguration getConfiguration(final SKSensorType sensorType) throws SKException {
        return mSensorManager.getConfiguration(sensorType);
    }

    /**
     *  Subscribes for sensor updates using a specified data handler.
     *
     *  @param sensorType  The type of the sensor that the data handler will be subscribed to.
     *  @param dataHandler A data handler that is invoked with each update to handle new sensor data. The block must conform to the SKSensorDataHandler type.
     */
    @Override
    public void subscribeSensorDataHandler(final SKSensorType sensorType, final @NonNull SKSensorDataHandler dataHandler) throws SKException {
        mSensorManager.subscribeSensorDataHandler(sensorType, dataHandler);
    }

    /**
     *  Unsubscribes a data handler.
     *
     *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
     *  @param dataHandler The data handler to be unsubscribed.
     */
    @Override
    public void unsubscribeSensorDataHandler(final SKSensorType sensorType, final @NonNull SKSensorDataHandler dataHandler) throws SKException {
        mSensorManager.unsubscribeSensorDataHandler(sensorType, dataHandler);
    }

    /**
     *  Unsubscribes all data handlers.
     *
     *  @param sensorType The type of the sensor for which the data handlers will be unsubscribed.
     */
    @Override
    public void unsubscribeAllSensorDataHandlers(final SKSensorType sensorType) throws SKException {
        mSensorManager.unsubscribeAllSensorDataHandlers(sensorType);
    }

    /**
     *  A string with a CSV formatted header that describes the data of the particular sensor. This method is useful in combination with the toString() or csvString() instance method of an SKSensorData object.
     *
     *  @param sensorType The type of the sensor for which the CSV Header will be returned.
     *
     *  @return A String with the CSV header.
     */
    @Override
    public @NonNull String csvHeaderForSensor(final SKSensorType sensorType) {
        return SKSensorUtilities.csvHeaderForSensor(sensorType);
    }

    /**
     *
     * @param sensorType
     * @return
     * @throws SKException
     */
    @Override
    public boolean isPermissionGrantedForSensor(final SKSensorType sensorType) throws SKException {
        return mSensorManager.isPermissionGrantedForSensor(sensorType);
    }

    /**
     *
     * @param sensorType
     * @param activity
     * @throws SKException
     */
    @Override
    public void requestPermissionForSensor(final SKSensorType sensorType, final @NonNull Activity activity) throws SKException {
        mSensorManager.requestPermissionForSensor(sensorType, activity);
    }

    /**
     *
     * @param activity
     */
    @Override
    public void requestPermissionForAllRegisteredSensors(final @NonNull Activity activity) {
        mSensorManager.requestPermissionForAllRegisteredSensors(activity);
    }

    /**
     *  Starts continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be started.
     */
    @Override
    public void startContinuousSensingWithSensor(final SKSensorType sensorType) throws SKException {
        mSensorManager.startContinuousSensingWithSensor(sensorType);
    }

    /**
     *  Stops continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be stopped.
     */
    @Override
    public void stopContinuousSensingWithSensor(final SKSensorType sensorType) throws SKException {
        mSensorManager.stopContinuousSensingWithSensor(sensorType);
    }

    /**
     *  Starts continuous sensing with all registered sensors.
     */
    @Override
    public void startContinuousSensingWithAllRegisteredSensors() throws SKException {
        mSensorManager.startContinuousSensingWithAllRegisteredSensors();
    }

    /**
     *  Starts continuous sensing with all registered sensors.
     */
    @Override
    public void stopContinuousSensingWithAllRegisteredSensors() throws SKException {
        mSensorManager.stopContinuousSensingWithAllRegisteredSensors();
    }

    /**
     *  Get the current time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC).
     *
     *  @return current time in milliseconds
     */
    @Override
    public long getCurrentTimeMillis() {
        return SKUtilities.getCurrentTimeMillis();
    }

    /**
     *  Get the current time in nanoseconds (the current value of the running Java Virtual Machine's high-resolution time source)
     *
     *  @return current time in nanoseconds
     */
    @Override
    public long getNanoTime() {
        return SKUtilities.getNanoTime();
    }

}
