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

package org.sensingkit.sensingkitlib.sensors;

import org.sensingkit.sensingkitlib.SKSensorType;

public final class SKSensorUtilities {

    @SuppressWarnings("unused")
    private static final String TAG = SKSensorUtilities.class.getName();

    private static final String SENSOR_STRINGS[] = {
            "Accelerometer",
            "Gravity",
            "Linear Acceleration",
            "Gyroscope",
            "Rotation",
            "Magnetometer",
            "Ambient Temperature",
            "Step Detector",
            "Step Counter",
            "Light",
            "Location",
            "Motion Activity",
            "Battery",
            "Screen Status",
            "Microphone",
            "Audio Level",
            "Bluetooth",
            "Beacon Proximity",
            "Humidity",
            "Air Pressure",
    };

    private static final String NONSPACED_SENSOR_STRINGS[] = {
            "Accelerometer",
            "Gravity",
            "LinearAcceleration",
            "Gyroscope",
            "Rotation",
            "Magnetometer",
            "AmbientTemperature",
            "StepDetector",
            "StepCounter",
            "Light",
            "Location",
            "MotionActivity",
            "Battery",
            "ScreenStatus",
            "Microphone",
            "AudioLevel",
            "Bluetooth",
            "BeaconProximity",
            "Humidity",
            "AirPressure",
    };


    @SuppressWarnings("unused")
    public static String getSensorInString(SKSensorType sensorType) {

        return SENSOR_STRINGS[sensorType.ordinal()];
    }

    @SuppressWarnings("unused")
    public static String getSensorInNonspacedString(SKSensorType sensorType) {

        return NONSPACED_SENSOR_STRINGS[sensorType.ordinal()];
    }

}
