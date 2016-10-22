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
import android.os.PowerManager;

import org.sensingkit.sensingkitlib.data.SKSensorData;


public class SensingKitLib implements SensingKitLibInterface {

    @SuppressWarnings("unused")
    private static final String TAG = SensingKitLib.class.getName();

    private static SensingKitLib sSensingKitLib;

    private final Context mApplicationContext;
    private PowerManager.WakeLock mWakeLock;

    private SKSensorManager mSensorManager;

    @SuppressWarnings("unused")
    public static SensingKitLibInterface getSensingKitLib(final Context context) throws SKException {

        if (context == null) {
            throw new SKException(TAG, "Context cannot be null", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (sSensingKitLib == null) {
            sSensingKitLib = new SensingKitLib(context);
        }

        return sSensingKitLib;
    }

    private SensingKitLib(final Context context) throws SKException {
        mApplicationContext = context;
        mSensorManager = SKSensorManager.getSensorManager(context);
    }

    /**
     *  Initializes and registers a sensor into the library with a default sensor configuration.
     *
     *  @param sensorType The type of the sensor that will be initialized and registered in the library.
     */
    @Override
    public void registerSensor(SKSensorType sensorType) throws SKException {
        mSensorManager.registerSensor(sensorType);
    }

    /**
     *  Deregisters a sensor from the library. Sensor should not be actively sensing when this method is called. All previously subscribed blocks will also be unsubscribed.
     *
     *  @param sensorType The type of the sensor that will be deregistered.
     */
    @Override
    public void deregisterSensor(SKSensorType sensorType) throws SKException {
        mSensorManager.deregisterSensor(sensorType);
    }

    /**
     *  A Boolean value that indicates whether the sensor is registered.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is registered or FALSE if it is not.
     */
    @Override
    public boolean isSensorRegistered(SKSensorType sensorType) throws SKException {
        return mSensorManager.isSensorRegistered(sensorType);
    }

    @Override
    public SKSensorData getDataFromSensor(SKSensorType sensorType) throws SKException {
        return mSensorManager.getDataFromSensor(sensorType);
    }

    /**
     *  Subscribes for sensor updates using a specified event listener.
     *
     *  @param sensorType  The type of the sensor that the data handler will be subscribed to.
     *  @param dataListener    An event listener that is invoked with each update to handle new sensor data. The block must conform to the SKSensorDataListener type.
     */
    @Override
    public void subscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {
        mSensorManager.subscribeSensorDataListener(sensorType, dataListener);
    }

    /**
     *  Unsubscribes an event listener.
     *
     *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
     *  @param dataListener The event listener to be unsubscribed.
     */
     @Override
    public void unsubscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {
        mSensorManager.unsubscribeSensorDataListener(sensorType, dataListener);
    }

     /**
      *  Unsubscribes all event listenerss.
      *
      *  @param sensorType The type of the sensor for which the event listener will be unsubscribed.
      */
     @Override
    public void unsubscribeAllSensorDataListeners(SKSensorType sensorType) throws SKException {
        mSensorManager.unsubscribeAllSensorDataListeners(sensorType);
    }

    /**
     *  Starts continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be started.
     */
    @Override
    public void startContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {
        mSensorManager.startContinuousSensingWithSensor(sensorType);
    }

    /**
     *  Stops continuous sensing with the specified sensor.
     *
     *  @param sensorType The type of the sensor that will be stopped.
     */
    @Override
    public void stopContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {
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
     *  A Boolean value that indicates whether the sensor is currently sensing.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is currently sensing or FALSE if it is not.
     */
    @Override
    public boolean isSensorSensing(SKSensorType sensorType) throws SKException {
        return mSensorManager.isSensorSensing(sensorType);
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

    //region Wake Lock methods

    private void acquireWakeLock() {
        if ((mWakeLock == null) || (!mWakeLock.isHeld())) {
            PowerManager pm = (PowerManager) mApplicationContext.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WakeLock");
            mWakeLock.acquire();
        }
    }

    private void releaseWakeLock() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
        }
    }

    private boolean checkWakeLockPermission() throws SKException {
        return SKUtilities.checkPermission(
                mApplicationContext,
                "android.permission.WAKE_LOCK");
    }

    //endregion
}
