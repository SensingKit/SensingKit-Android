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

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 * An instance of SKGyroscopeData encapsulates measurements related to the Gyroscope sensor.
 */
public class SKGyroscopeData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKGyroscopeData.class.getSimpleName();

    private final float x;
    private final float y;
    private final float z;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param x         X-axis of the Gyroscope data
     * @param y         Y-axis of the Gyroscope data
     * @param z         Z-axis of the Gyroscope data
     */
    public SKGyroscopeData(final long timestamp, final float x, final float y, final float z) {
        super(SKSensorType.GYROSCOPE, timestamp);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Get the csv header of the Gyroscope sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Gyroscope sensor.
     */
    @SuppressWarnings("unused")
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,x,y,z";
    }

    /**
     * Get the Gyroscope data in csv format
     *
     * @return String in csv format: timeIntervalSince1970, X-axis, Y-axis, Z-axis
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f", this.timestamp, this.x, this.y, this.z);
    }

    /**
     * Get the Gyroscope sensor data in JSONObject format
     *
     * @return JSONObject containing the Gyroscope sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, x-axis, y-axis, z-axis
     */
    @Override
    @NonNull
    public JSONObject getDataInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sensorType", this.getSensorType());
            jsonObject.put("sensorTypeString", this.getSensorType().toString());
            jsonObject.put("timestamp", this.timestamp);

            JSONObject subJsonObject = new JSONObject();
            subJsonObject.put("x", this.x);
            subJsonObject.put("y", this.y);
            subJsonObject.put("z", this.z);

            jsonObject.put("gyroscope", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Get the X-axis of the Gyroscope sensor data
     *
     * @return X-axis value
     */
    @SuppressWarnings("unused")
    public float getX() {
        return this.x;
    }

    /**
     * Get the Y-axis of the Gyroscope sensor data
     *
     * @return Y-axis value
     */
    @SuppressWarnings("unused")
    public float getY() {
        return this.y;
    }

    /**
     * Get the Z-axis of the Gyroscope sensor data
     *
     * @return Z-axis value
     */
    @SuppressWarnings("unused")
    public float getZ() {
        return this.z;
    }

}
