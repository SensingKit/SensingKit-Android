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
 * An instance of SKLinearAccelerationData encapsulates measurements related to the Linear Acceleration sensor.
 * Measures the acceleration force in mass/seconds**2, excluding the force of gravity
 */
public class SKLinearAccelerationData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKLinearAccelerationData.class.getSimpleName();

    private final float x;
    private final float y;
    private final float z;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param x         Force in X direction
     * @param y         Force in Y direction
     * @param z         Force in Z direction
     */
    public SKLinearAccelerationData(final long timestamp, final float x, final float y, final float z) {
        super(SKSensorType.LINEAR_ACCELERATION, timestamp);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Get the csv header of the Linear Acceleration sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Linear Acceleration sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,x,y,z";
    }

    /**
     * Get Linear Acceleration sensor data in CSV format
     *
     * @return String in CSV format: timeIntervalSince1970, x force, y force, z force
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f", this.timestamp, this.x, this.y, this.z);
    }

    /**
     * Get the Linear Acceleration sensor data in JSONObject format
     *
     * @return JSONObject containing the Linear Acceleration sensor data in JSONObject format:
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

            jsonObject.put("linearAcceleration", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Get Linear Acceleration force in X direction
     *
     * @return force in X direction
     */
    @SuppressWarnings("unused")
    public float getX() {
        return this.x;
    }

    /**
     * Get Linear Acceleration force in Y direction
     *
     * @return force in Y direction
     */
    @SuppressWarnings("unused")
    public float getY() {
        return this.y;
    }

    /**
     * Get Linear Acceleration force in Z direction
     *
     * @return force in Z direction
     */
    @SuppressWarnings("unused")
    public float getZ() {
        return this.z;
    }

}
