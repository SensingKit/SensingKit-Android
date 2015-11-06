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
    private static final String TAG = "SensingKitLib";

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

    @Override
    public void registerSensor(SKSensorType sensorType) throws SKException {
        mSensorManager.registerSensor(sensorType);
    }

    @Override
    public void deregisterSensor(SKSensorType sensorType) throws SKException {
        mSensorManager.deregisterSensor(sensorType);
    }

    @Override
    public boolean isSensorRegistered(SKSensorType sensorType) throws SKException {
        return mSensorManager.isSensorRegistered(sensorType);
    }

    @Override
    public SKSensorData getDataFromSensor(SKSensorType sensorType) throws SKException {
        return mSensorManager.getDataFromSensor(sensorType);
    }

    @Override
    public void subscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {
        mSensorManager.subscribeSensorDataListener(sensorType, dataListener);
    }

    @Override
    public void unsubscribeSensorDataListener(SKSensorType sensorType, SKSensorDataListener dataListener) throws SKException {
        mSensorManager.unsubscribeSensorDataListener(sensorType, dataListener);
    }

    @Override
    public void unsubscribeAllSensorDataListeners(SKSensorType sensorType) throws SKException {
        mSensorManager.unsubscribeAllSensorDataListeners(sensorType);
    }

    @Override
    public void startContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {
        mSensorManager.startContinuousSensingWithSensor(sensorType);
    }

    @Override
    public void stopContinuousSensingWithSensor(SKSensorType sensorType) throws SKException {
        mSensorManager.stopContinuousSensingWithSensor(sensorType);
    }

    @Override
    public boolean isSensorSensing(SKSensorType sensorType) throws SKException {
        return mSensorManager.isSensorSensing(sensorType);
    }

    @Override
    public long getCurrentTimeMillis() {
        return SKUtilities.getCurrentTimeMillis();
    }

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
