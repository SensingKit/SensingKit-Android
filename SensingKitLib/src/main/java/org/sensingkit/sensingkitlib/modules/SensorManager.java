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

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.model.ModelManager;
import org.sensingkit.sensingkitlib.model.SensorDataBuffer;

public class SensorManager {
    private static final String TAG = "SensorManager";

    private static SensorManager sSensorManager;
    private final Context mApplicationContext;

    private AccelerometerMotionSensor mAccelerometerMotionSensor;

    public static SensorManager getSensorManager(final Context context) throws SKException {
        if (context == null) {
            throw new SKException(TAG, "Context cannot be null.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (sSensorManager == null) {
            sSensorManager = new SensorManager(context);
        }

        return sSensorManager;
    }

    private SensorManager(final Context context) throws SKException {
        mApplicationContext = context;

        // Init Accelerometer Sensing
        mAccelerometerMotionSensor = new AccelerometerMotionSensor(context);
    }

    public void startSensing() {
        mAccelerometerMotionSensor.startSensing();
    }

    public void stopSensing() {
        mAccelerometerMotionSensor.stopSensing();
    }
}
