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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.model.AccelerometerMotionSensorData;

public abstract class AbstractMotionSensor extends AbstractSensor {

    private static final String TAG = "AbstractMotionSensor";

    private final SensorManager mSensorManager;
    private final Sensor mSensor;
    private final SensorEventListener mSensorEventListener;

    protected AbstractMotionSensor(final Context context, final SensorType sensorType) throws SKException {
        super(context, sensorType);

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(SensorUtilities.getSensorType(sensorType));

        mSensorEventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Nothing at the moment
            }

            public void onSensorChanged(SensorEvent event) {

                // Create the data
                AccelerometerMotionSensorData data = new AccelerometerMotionSensorData(
                        event.timestamp,
                        event.values[0],
                        event.values[1],
                        event.values[2]);

                // Add to DataBuffer
                mSensorDataBuffer.addData(data);
            }
        };
    }

    public boolean startSensing() {
        return mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stopSensing() {
        mSensorManager.unregisterListener(mSensorEventListener);
    }
}
