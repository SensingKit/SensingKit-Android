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

package org.sensingkit.sensingkitlib.data;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 *  An instance of SKAirPressureData encapsulates measurements related to the Air Pressure sensor.
 */
public class SKAmbientTemperatureData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKAmbientTemperatureData.class.getName();

    protected final float temperature;

    /**
     * Initialize Ambient Temperature data instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param temperature In degrees Celsius
     */
    public SKAmbientTemperatureData(long timestamp, float temperature) {

        super(SKSensorType.AMBIENT_TEMPERATURE, timestamp);

        this.temperature = temperature;
    }

    /**
     * Get the csv header of the Ambient Temperature sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Ambient Temperature sensor.
     */
    @SuppressWarnings("unused")
    public static String csvHeader() {
        return "timeIntervalSince1970,temperature";
    }

    /**
     * Get the Ambient Temperature sensor data in csv format
     *
     * @return Ambient Temperature data in csv format: timeIntervalSince1970,temperature
     *
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f", this.timestamp, this.temperature);
    }

    /**
     * Get only the Ambient Temperature
     *
     * @return Ambient Temperature
     */
    @SuppressWarnings("unused")
    public float getTemperature() {
        return this.temperature;
    }

}
