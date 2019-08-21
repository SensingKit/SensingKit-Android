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

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.data.*;

@SuppressWarnings("WeakerAccess")
public final class SKSensorUtilities {

    @SuppressWarnings("unused")
    private static final String TAG = SKSensorUtilities.class.getSimpleName();

    /**
     *  Return a string with a CSV formatted header that describes the data of the particular sensor.
     */
    static @NonNull
    public String csvHeaderForSensor(final SKSensorType sensorType) {

        switch (sensorType) {

            case ACCELEROMETER:
                return SKAccelerometerData.csvHeader();

            case GRAVITY:
                return SKGravityData.csvHeader();

            case LINEAR_ACCELERATION:
                return SKLinearAccelerationData.csvHeader();

            case GYROSCOPE:
                return SKGyroscopeData.csvHeader();

            case ROTATION:
                return SKRotationData.csvHeader();

            case MAGNETOMETER:
                return SKMagnetometerData.csvHeader();

            case AMBIENT_TEMPERATURE:
                return SKAmbientTemperatureData.csvHeader();

            case STEP_DETECTOR:
                return SKStepDetectorData.csvHeader();

            case STEP_COUNTER:
                return SKStepCounterData.csvHeader();

            case LIGHT:
                return SKLightData.csvHeader();

            case LOCATION:
                return SKLocationData.csvHeader();

            case MOTION_ACTIVITY:
                return SKMotionActivityData.csvHeader();

            case BATTERY_STATUS:
                return SKBatteryStatusData.csvHeader();

            case SCREEN_STATUS:
                return SKScreenStatusData.csvHeader();

            case MICROPHONE:
                return SKMicrophoneData.csvHeader();

            case AUDIO_LEVEL:
                return SKAudioLevelData.csvHeader();

            case BLUETOOTH:
                return SKBluetoothData.csvHeader();

            case BEACON_PROXIMITY:
                return SKBeaconProximityData.csvHeader();

            case HUMIDITY:
                return SKHumidityData.csvHeader();

            case BAROMETER:
                return SKBarometerData.csvHeader();

            case NOTIFICATION:
                return SKNotificationData.csvHeader();

            default:
                throw new RuntimeException("csvHeader for Sensor '" + sensorType.getName() + "' is missing.");
        }
    }

    /**
     *  A Boolean value that indicates whether the sensor is available on the device.
     *
     *  @param sensorType The type of the sensor that will be checked.
     *
     *  @return TRUE if the sensor is available on the device, or FALSE if it is not.
     */
    public static boolean isSensorAvailable(final SKSensorType sensorType, final @NonNull Context context) {

        // Get package manager
        PackageManager packageManager = context.getPackageManager();

        switch (sensorType) {

            case ACCELEROMETER:
            case GRAVITY:
            case LINEAR_ACCELERATION:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);

            case GYROSCOPE:
            case ROTATION:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);

            case MAGNETOMETER:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);

            case AMBIENT_TEMPERATURE:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE);

            case STEP_DETECTOR:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);

            case STEP_COUNTER:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);

            case LIGHT:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);

            case LOCATION:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION) &&
                        isGooglePlayServicesAvailable(context);

            case MOTION_ACTIVITY:
                return isGooglePlayServicesAvailable(context);

            case BATTERY_STATUS:
            case SCREEN_STATUS:
                return true;

            case MICROPHONE:
            case AUDIO_LEVEL:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);

            case BLUETOOTH:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);

            case BEACON_PROXIMITY:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);

            case HUMIDITY:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                        packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY);

            case BAROMETER:
                return packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER);

            case NOTIFICATION:
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;

            default:
                throw new RuntimeException("Availability check for Sensor '" + sensorType.getName() + "' is missing.");
        }
    }

    public static boolean isGooglePlayServicesAvailable(final @NonNull Context context) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(context);
        return code == ConnectionResult.SUCCESS;
    }

}
