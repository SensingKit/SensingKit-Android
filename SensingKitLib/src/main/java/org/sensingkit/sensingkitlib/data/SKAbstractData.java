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

package org.sensingkit.sensingkitlib.data;

import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.SKSensorType;

/*
Abstract class for sensor data
 */
@SuppressWarnings("WeakerAccess")
public abstract class SKAbstractData implements SKSensorData {
    @SuppressWarnings("unused")
    private static final String TAG = SKAbstractData.class.getSimpleName();

    protected final SKSensorType sensorType;
    protected final long timestamp;

    /**
     * Initialize the instance
     *
     * @param sensorType of type SKSensorType
     * @param timestamp  Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     */
    public SKAbstractData(final SKSensorType sensorType, final long timestamp) {
        this.sensorType = sensorType;
        this.timestamp = timestamp;
    }

    /**
     * Get the sensor data in csv format
     *
     * @return String in csv format
     */
    @NonNull
    public String toString() {
        return this.getDataInCSV();
    }

    /**
     * Get the sensor type
     *
     * @return sensor type
     */
    @SuppressWarnings("unused")
    public SKSensorType getSensorType() {
        return sensorType;
    }

    /**
     * Get the timestamp
     *
     * @return timestamp
     */
    @SuppressWarnings("unused")
    public long getTimestamp() {
        return timestamp;
    }
}
