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
import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.configuration.SKConfiguration;

@SuppressWarnings("unused")
public interface SensingKitLibInterface {

    /**
     *  A Boolean value that indicates whether the sensor is available on the device.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is available on the device, or FALSE if it is not.
     */
    boolean isSensorAvailable(final SKSensorType sensorType);

    /**
     *  A Boolean value that indicates whether the sensor is registered.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is registered or FALSE if it is not.
     */
    boolean isSensorRegistered(final SKSensorType sensorType);

    /**
     *  A Boolean value that indicates whether the sensor is currently sensing.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is currently sensing or FALSE if it is not.
     */
    boolean isSensorSensing(final SKSensorType sensorType) throws SKException;

    /**
     *  Initializes and registers a sensor into the library with a default sensor configuration.
     *
     *  @param sensorType The type of the sensor that will be initialized and registered in the library.
     */
    void registerSensor(final SKSensorType sensorType) throws SKException;

    /**
     *  Initializes and registers a sensor into the library with a custom sensor configuration.
     *
     *  @param sensorType The type of the sensor that will be initialized and registered in the library.
     *  @param configuration A configuration object that conforms to SKConfiguration. If no configuration is specified, it will default to a pre-determined sensor configuration.
     */
    void registerSensor(final SKSensorType sensorType, final SKConfiguration configuration) throws SKException;

    /**
     *  Deregisters a sensor from the library. Sensor should not be actively sensing when this method is called. All previously subscribed data handlers will also be unsubscribed.
     *
     *  @param sensorType The type of the sensor that will be deregistered.
     */
    void deregisterSensor(final SKSensorType sensorType) throws SKException;

    /**
     *  Provides custom configuration to a sensor.
     *
     *  @param configuration A configuration object that conforms to SKConfiguration. If no configuration is specified, it will default to a pre-determined sensor configuration.
     *  @param sensorType The type of the sensor that will be configured.
     */
    void setConfiguration(final SKConfiguration configuration, final SKSensorType sensorType) throws SKException;

    /**
     *  Gets the configuration of a sensor.
     *
     *  @param sensorType The type of the sensor.
     */
    @NonNull
    SKConfiguration getConfiguration(final SKSensorType sensorType) throws SKException;

    /**
     *  Subscribes for sensor updates using a specified data handler.
     *
     *  @param sensorType  The type of the sensor that the data handler will be subscribed to.
     *  @param dataHandler A data handler that is invoked with each update to handle new sensor data. The block must conform to the SKSensorDataHandler type.
     */
    void subscribeSensorDataHandler(final SKSensorType sensorType, final @NonNull SKSensorDataHandler dataHandler) throws SKException;

    /**
     *  Unsubscribes a data handler.
     *
     *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
     *  @param dataHandler The data handler to be unsubscribed.
     */
    void unsubscribeSensorDataHandler(final SKSensorType sensorType, final @NonNull SKSensorDataHandler dataHandler) throws SKException;

    /**
     *  Unsubscribes all data handlers.
     *
     *  @param sensorType The type of the sensor for which the data handlers will be unsubscribed.
     */
    void unsubscribeAllSensorDataHandlers(final SKSensorType sensorType) throws SKException;

    /**
     *  A string with a CSV formatted header that describes the data of the particular sensor. This method is useful in combination with the toString() or csvString() instance method of an SKSensorData object.
     *
     *  @param sensorType The type of the sensor for which the CSV Header will be returned.
     *
     *  @return A String with the CSV header.
     */
    @NonNull
    String csvHeaderForSensor(final SKSensorType sensorType);

    /**
     * TODO:
     *
     * @param sensorType
     * @return
     * @throws SKException
     */
    boolean isPermissionGrantedForSensor(final SKSensorType sensorType) throws SKException;

    /**
     * TODO:
     *
     * @param sensorType
     * @param activity
     * @throws SKException
     */
    void requestPermissionForSensor(final SKSensorType sensorType, final @NonNull Activity activity) throws SKException ;

    /**
     * TODO:
     *
     * @param activity
     */
    void requestPermissionForAllRegisteredSensors(final @NonNull Activity activity);

    /**
     *  Starts continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be started.
     */
    void startContinuousSensingWithSensor(final SKSensorType sensorType) throws SKException;

    /**
     *  Stops continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be stopped.
     */
    void stopContinuousSensingWithSensor(final SKSensorType sensorType) throws SKException;

    /**
     *  Starts continuous sensing with all registered sensors.
     */
    void startContinuousSensingWithAllRegisteredSensors() throws SKException;

    /**
     *  Starts continuous sensing with all registered sensors.
     */
    void stopContinuousSensingWithAllRegisteredSensors() throws SKException;

    /**
     *  Get the current time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC).
     *
     *  @return current time in milliseconds
     */
    long getCurrentTimeMillis();

    /**
     *  Get the current time in nanoseconds (the current value of the running Java Virtual Machine's high-resolution time source)
     *
     *  @return current time in nanoseconds
     */
    long getNanoTime();
}
