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

import androidx.annotation.NonNull;

/**
 *  These constants indicate the type of the sensor.
 */
public enum SKSensorType {
    /**
     *  Measures the device acceleration changes in three-dimensional space. You can use this data to detect both the current orientation of the device (relative to the ground) and any instantaneous changes to that orientation.
     */
    ACCELEROMETER ("Accelerometer", "Accelerometer"),

    /**
     *  Measures the force of gravity in m/s2 that is applied to a device on all three physical axes (x, y, z).
     */
    GRAVITY ("Gravity", "Gravity"),

    /**
     *  Measures the acceleration force in m/s2 that is applied to a device on all three physical axes (x, y, and z), excluding the force of gravity.
     */
    LINEAR_ACCELERATION ("Linear Acceleration", "LinearAcceleration"),

    /**
     *  Measures the device's rate of rotation around each of the three spatial axes.
     */
    GYROSCOPE ("Gyroscope", "Gyroscope"),

    /**
     *  Measures the orientation of a device by providing the three elements of the device's rotation vector.
     */
    ROTATION ("Rotation", "Rotation"),

    /**
     *  Measures the ambient geomagnetic field for all three physical axes (x, y, z) in microtesla.
     */
    MAGNETOMETER ("Magnetometer", "Magnetometer"),

    /**
     * Room temperature in degrees Celcius.
     */
    AMBIENT_TEMPERATURE ("Ambient Temperature", "AmbientTemperature"),

    /**
     *  Returns 1.0 each time a step is taken
     */
    STEP_DETECTOR ("Step Detector", "StepDetector"),

    /**
     *  Number of steps taken by the user since the last reboot while activated.
     */
    STEP_COUNTER ("Step Counter", "StepCounter"),

    /**
     *  Measures the ambient light level (illumination) in lux.
     */
    LIGHT ("Light", "Light"),

    /**
     *  Location sensor determines the current location of the device using a combination of Cellular, Wi-Fi, Bluetooth and GPS sensors. It provides 2D geographical coordinate information (latitude, longitude), as well as the altitude of the device.
     */
    LOCATION ("Location", "Location"),

    /**
     *  Motion Activity sensor uses an embedded motion co-processor that senses the user's activity classified as Stationary, Walking, Running, Automotive or Cycling.
     *  Assume that Activity is the same as Motion Activity
     */
    MOTION_ACTIVITY ("Motion Activity", "MotionActivity"),

    /**
     *  Battery Status sensor listens to changes in the battery charge state (Charging, Full, Unplugged) as well as in the battery charge level (with 1% precision).
     */
    BATTERY_STATUS("Battery Status", "BatteryStatus"),

    /**
     *  Senses the status of the screen (On / Off).
     */
    SCREEN_STATUS ("Screen Status", "ScreenStatus"),

    /**
     *  Microphone sensor can be used to record audio from the environment by converting sound into electrical signal.
     *  Assume that Microphone is the same as AUDIO RECORDER
     */
    MICROPHONE ("Microphone", "Microphone"),

    /**
     *  Maximum level of the audio signal in a buffer.
     */
    AUDIO_LEVEL ("Audio Level", "AudioLevel"),

    /**
     *  Scans for other Bluetooth Classic devices around.
     */
    BLUETOOTH ("Bluetooth", "Bluetooth"),

    /**
     *  Beacon Proximity sensor estimates the proximity of the current device with other iBeacon™, AltBeacon or Eddystone™ beacons in range.
     */
    BEACON_PROXIMITY ("Beacon Proximity", "BeaconProximity"),

    /**
     *  Relative ambient air humidity in percent.
     */
    HUMIDITY ("Humidity", "Humidity"),

    /**
     *  Atmospheric pressure in hPa (millibar).
     */
    BAROMETER ("Barometer", "Barometer"),

    /**
     *  Notifications received in the device.
     */
    NOTIFICATION ("Notification", "Notification");

    private final static int length = SKSensorType.values().length;

    private final String name;
    private final String nonspacedName;

    SKSensorType(final @NonNull String name, final @NonNull String nonspacedName) {
        this.name = name;
        this.nonspacedName = nonspacedName;
    }

    /**
     *
     * @return Total number of sensors supported by SensingKit-Android
     */
    @SuppressWarnings("unused")
    public static int getLength() {
        return SKSensorType.length;
    }

    /**
     *
     * @return The sensor's name
     */
    @SuppressWarnings("unused")
    public @NonNull String getName() {
        return this.name;
    }

    /**
     *
     * @return A monospaced version of the sensor's name
     */
    @SuppressWarnings("unused")
    public @NonNull String getNonspacedName() {
        return this.nonspacedName;
    }

    /**
     *
     * @return The sensor's name
     */
    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }
}
