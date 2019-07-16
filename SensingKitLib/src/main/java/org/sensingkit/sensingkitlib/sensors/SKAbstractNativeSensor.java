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

package org.sensingkit.sensingkitlib.sensors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.annotation.NonNull;
import android.util.Log;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKAbstractNativeSensorConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;

@SuppressWarnings("WeakerAccess")
public abstract class SKAbstractNativeSensor extends SKAbstractSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKAbstractNativeSensor.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;

    protected SKAbstractNativeSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) throws SKException {
        super(context, sensorType, configuration);
    }

    @Override
    protected void initSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "initSensor");}

        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager == null) {
            throw new SKException(TAG, "Could not access the system service: SENSOR_SERVICE.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        mSensor = mSensorManager.getDefaultSensor(getSensorType(sensorType));

        if (mSensor == null) {
            throw new SKException(TAG, "Sensor [" + sensorType.getName() + "] is not available in the device.", SKExceptionErrorCode.SENSOR_NOT_AVAILABLE);
        }

        mSensorEventListener = new SensorEventListener() {

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Ignore
            }

            @Override
            public void onSensorChanged(SensorEvent event) {

                // Build the data object
                SKAbstractData data = buildData(event);

                // Submit sensor data object
                submitSensorData(data);
            }
        };

        // configure sensor
        updateSensor(context, sensorType, configuration);
    }

    @Override
    protected void updateSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "updateSensor [" + sensorType.getName() + "]");}

        // Not required for this type of sensor
    }

    @Override
    public void startSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "startSensing [" + mSensorType.getName() + "]");}

        super.startSensing();

        // Get the configuration
        SKAbstractNativeSensorConfiguration configuration = (SKAbstractNativeSensorConfiguration)mConfiguration;

        boolean status = mSensorManager.registerListener(mSensorEventListener, mSensor, configuration.getSamplingRate());

        if (!status) {
            throw new SKException(TAG, "Sensor '" + getSensorName() + "' could not be started.", SKExceptionErrorCode.SENSOR_ERROR);
        }
    }

    @Override
    public void stopSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "stopSensing [" + mSensorType.getName() + "]");}

        mSensorManager.unregisterListener(mSensorEventListener);

        super.stopSensing();
    }

    @NonNull
    protected abstract SKAbstractData buildData(final SensorEvent event);

    @SuppressLint("InlinedApi")  // There is a check in SKSensorManager
    private static int getSensorType(final SKSensorType sensorType) {

        switch (sensorType) {

            case ACCELEROMETER:
                return Sensor.TYPE_ACCELEROMETER;

            case GRAVITY:
                return Sensor.TYPE_GRAVITY;

            case LINEAR_ACCELERATION:
                return Sensor.TYPE_LINEAR_ACCELERATION;

            case GYROSCOPE:
                return Sensor.TYPE_GYROSCOPE;

            case ROTATION:
                return Sensor.TYPE_ROTATION_VECTOR;

            case MAGNETOMETER:
                return Sensor.TYPE_MAGNETIC_FIELD;

            case AMBIENT_TEMPERATURE:
                return Sensor.TYPE_AMBIENT_TEMPERATURE;

            case STEP_DETECTOR:
                return Sensor.TYPE_STEP_DETECTOR;

            case STEP_COUNTER:
                return Sensor.TYPE_STEP_COUNTER;

            case LIGHT:
                return Sensor.TYPE_LIGHT;

            case HUMIDITY:
                return Sensor.TYPE_RELATIVE_HUMIDITY;

            case BAROMETER:
                return Sensor.TYPE_PRESSURE;

            default:
                throw new RuntimeException("Sensor '" + sensorType.getName() + "' is not a native sensor.");
        }
    }

}