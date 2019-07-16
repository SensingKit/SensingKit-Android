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

import org.json.JSONException;
import org.json.JSONObject;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 * An instance of SKLightData encapsulates measurements related to the Light sensor.
 */
public class SKLightData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKLightData.class.getSimpleName();

    private final float light;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param light     Ambient light in lux
     */
    public SKLightData(final long timestamp, final float light) {
        super(SKSensorType.LIGHT, timestamp);

        this.light = light;
    }

    /**
     * Get the csv header of the Light sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Light sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,lux";
    }

    /**
     * Get light sensor data in CSV format
     *
     * @return String in CSV format: timeIntervalSince1970, ambient light in lux
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f", this.timestamp, this.light);
    }

    /**
     * Get light sensor data in JSONObject format
     *
     * @return JSONObject containing the light sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, light
     */
    @Override
    @NonNull
    public JSONObject getDataInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sensorType", this.getSensorType());
            jsonObject.put("sensorTypeString", this.getSensorType().toString());
            jsonObject.put("timestamp", this.timestamp);
            jsonObject.put("light", this.light);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Get light sensor data
     *
     * @return Ambient light in lux
     */
    @SuppressWarnings("unused")
    public float getLight() {
        return this.light;
    }

}
