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

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;

public final class SKSensorUtilities {

    @SuppressWarnings("unused")
    private static final String TAG = SKSensorUtilities.class.getName();

    public static String getSensorInString(SKSensorType sensorType) throws SKException {

        switch (sensorType) {

            case ACCELEROMETER:
                return "Accelerometer";

            case GRAVITY:
                return "Gravity";

            case LINEAR_ACCELERATION:
                return "Linear Acceleration";

            case GYROSCOPE:
                return "Gyroscope";

            case ROTATION:
                return "Rotation";

            case MAGNETOMETER:
                return "Magnetometer";

            case AMBIENT_TEMPERATURE:
                return "Ambient Temperature";

            case STEP_DETECTOR:
                return "Step Detector";

            case STEP_COUNTER:
                return "Step Counter";

            case LIGHT:
                return "Light";

            case LOCATION:
                return "Location";

            case MOTION_ACTIVITY:
                return "Motion Activity";

            case BATTERY:
                return "Battery";

            case SCREEN_STATUS:
                return "Screen Status";

            case MICROPHONE:
                return "Microphone";

            case AUDIO_LEVEL:
                return "Audio Level";

            case BLUETOOTH:
                return "Bluetooth";

            case HUMIDITY:
                return "Humidity";

            case AIR_PRESSURE:
                return "Air Pressure";

            case EDDYSTONE_PROXIMITY:
                return "Eddystone Proximity";

            case IBEACON:
                return "iBeacon";

            default:
                throw new SKException(TAG, "Unknown SensorModule", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

    }

}
