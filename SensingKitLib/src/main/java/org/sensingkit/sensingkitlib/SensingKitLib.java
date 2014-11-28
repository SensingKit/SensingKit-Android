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

import org.sensingkit.sensingkitlib.model.ModelManager;
import org.sensingkit.sensingkitlib.modules.SensorManager;

@SuppressWarnings("unused")
public class SensingKitLib {
    private static final String TAG = "SensingKitLib";

    private static SensingKitLib sSensingKitLib;

    private final Context mApplicationContext;
    private PowerManager.WakeLock mWakeLock;

    private ModelManager mModelManager;
    private SensorManager mSensorManager;

    @SuppressWarnings("unused")
    public static SensingKitLib getSensingKitLib(final Context context) throws SKException {
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
        mModelManager = ModelManager.getModelManager(context);
        mSensorManager = SensorManager.getSensorManager(context);
    }

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

    @SuppressWarnings("unused")
    public void startSensing() throws SKException {
        if (checkWakeLockPermission()) {
            acquireWakeLock();
        } else {
            throw new SKException(TAG, "WakeLock permission does not exist.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Start Sensing
        mSensorManager.startSensing();

        // Start Auto Flushing
        mModelManager.startAutoFlushing();
    }

    @SuppressWarnings("unused")
    public void stopSensing() {
        // Stop Sensing
        mSensorManager.stopSensing();

        // Stop Auto Flushing
        mModelManager.stopAutoFlushing();

        releaseWakeLock();
    }

    private boolean checkWakeLockPermission() throws SKException {
        return SensingKitUtilities.checkPermission(
                mApplicationContext,
                "android.permission.WAKE_LOCK");
    }
}
