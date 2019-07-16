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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.NonNull;

public class SKWakeLockManager {

    @SuppressWarnings("unused")
    private static final String TAG = SKWakeLockManager.class.getSimpleName();
    private static SKWakeLockManager sWakeLockManager = null;

    public static SKWakeLockManager getInstance(final @NonNull Context context) throws SKException {

        if (sWakeLockManager == null) {
            sWakeLockManager = new SKWakeLockManager(context);
        }
        return sWakeLockManager;
    }

    private SKWakeLockManager(final @NonNull Context context) throws SKException {

        if (!SKUtilities.isPermissionGranted(Manifest.permission.WAKE_LOCK, context)) {
            throw new SKException(TAG, "Permission WAKE_LOCK is missing.", SKExceptionErrorCode.PERMISSION_MISSING);
        }

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager == null) {
            throw new SKException(TAG, "Could not access the system service: POWER_SERVICE.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SensingKit:WakeLock");
        mLocksCounter = 0;
    }

    private PowerManager.WakeLock mWakeLock;
    private int mLocksCounter;

//    public boolean isWakeLockActive() {
//        return mWakeLock.isHeld();
//    }

    @SuppressLint("WakelockTimeout")
    public void acquireWakeLock() {

        mLocksCounter++;
        mWakeLock.acquire();
    }

    public void releaseWakeLock() {

        if (mLocksCounter == 0) {
            throw new RuntimeException("WakeLock calls are imbalanced: (mLocksCounter == 0)");
        }

        mWakeLock.release();
        mLocksCounter--;
    }

    @Override
    public @NonNull String toString() {
        return "mLocksCounter: " + mLocksCounter;
    }
}
