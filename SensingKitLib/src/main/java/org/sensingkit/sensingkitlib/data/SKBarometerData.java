/*
 * Copyright (c) 2015. Queen Mary University of London
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

package org.sensingkit.sensingkitlib.data;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 *  An instance of SKBarometerData encapsulates measurements related to the Air Pressure sensor.
 */
public class SKBarometerData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKBarometerData.class.getName();

    protected final float pressure;

    /**
     * Initialize the Barometer data instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param pressure Air pressure
     */
    public SKBarometerData(long timestamp, float pressure) {

        super(SKSensorType.BAROMETER, timestamp);

        this.pressure = pressure;
    }

    /**
     * Get the csv header of the Barometer sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Barometer sensor.
     */
    @SuppressWarnings("unused")
    public static String csvHeader() {
        return "timeIntervalSince1970,pressure";
    }

    /**
     * Get the Barometer sensor data in csv format
     *
     * @return Barometer data in csv format: timeIntervalSince1970,pressure
     *
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f", this.timestamp, this.pressure);
    }

    /**
     * Get the Air Pressure measurement along
     *
     * @return Air Pressure measurement
     */
    @SuppressWarnings("unused")
    public float getPressure() {
        return this.pressure;
    }

}
