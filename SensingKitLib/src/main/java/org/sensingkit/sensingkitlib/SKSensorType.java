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

/**
 *  These constants indicate the type of the sensor.
 */
public enum SKSensorType {
    /**
     *  Measures the device acceleration changes in three-dimensional space. You can use this data to detect both the current orientation of the device (relative to the ground) and any instantaneous changes to that orientation.
     */
    ACCELEROMETER,
    /**
     *  Measures the force of gravity in m/s2 that is applied to a device on all three physical axes (x, y, z).
     */
    GRAVITY,
    /**
     *  Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), excluding the force of gravity.
     */
    LINEAR_ACCELERATION,
    /**
     *  Measures the device's rate of rotation around each of the three spatial axes.
     */
    GYROSCOPE,
    /**
     *  Measures the orientation of a device by providing the three elements of the device's rotation vector.
     */
    ROTATION,
    /**
     *  Measures the ambient geomagnetic field for all three physical axes (x, y, z) in microtesla.
     */
    MAGNETOMETER,
    /**
     * Room temperature in degrees Celcius
     */
    AMBIENT_TEMPERATURE,
    /**
     *  Returns 1.0 each time a step is taken
     */
    STEP_DETECTOR,
    /**
     *  Number of steps taken by the user since the last reboot while activated
     */
    STEP_COUNTER,
    /**
     *  Measures the ambient light level (illumination) in lux.
     */
    LIGHT,
    /**
     *  Location sensor determines the current location of the device using a combination of Cellular, Wi-Fi, Bluetooth and GPS sensors. It provides 2D geographical coordinate information (latitude, longitude), as well as the altitude of the device.
     */
    LOCATION,
    /**
     *  Motion Activity sensor uses an embedded motion co-processor that senses the user's activity classified as Stationary, Walking, Running, Automotive or Cycling.
     *  Assume that Activity is the same as Motion Activity
     */
    ACTIVITY,
    /**
     *  Battery sensor listens to changes in the battery charge state (Charging, Full, Unplugged) as well as in the battery charge level (with 1% precision).
     */
    BATTERY,
    /**
     *  Not defined in IOS docs
     */
    SCREEN_STATUS,
    /**
     *  Microphone sensor can be used to record audio from the environment (up to 4 hours) by converting sound into electrical signal.
     *  Assume that Microphone is the same as AUDIO RECORDER
     */
    AUDIO_RECORDER,
    /**
     *  Maximum level of the audio signal in a buffer
     */
    AUDIO_LEVEL,
    /**
     *  Get data from a bluetooth device
     */
    BLUETOOTH,
    /**
     *  Relative ambient air humidity in percent
     */
    HUMIDITY,
    EDDYSTONE_PROXIMITY,
    IBEACON,
    /**
     *  Atmospheric pressure in hPa (millibar)
     */
    AIR_PRESSURE
}