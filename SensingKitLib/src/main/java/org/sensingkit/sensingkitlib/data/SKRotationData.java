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
 * An instance of SKRotationData encapsulates measurements related to the Rotation sensor.
 */
public class SKRotationData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKRotationData.class.getSimpleName();

    private final float x;
    private final float y;
    private final float z;
    private final float cos;
    private final float headingAccuracy;

    /**
     * Initialize the instance
     *
     * @param timestamp       Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param x               Rotation axis x-component*sin(theta/2)
     * @param y               Rotation axis y-component*sin(theta/2)
     * @param z               Rotation axis z-component*sin(theta/2)
     * @param cos             Cos(theta)
     *                        <p>
     *                        where theta is the angle of rotation
     * @param headingAccuracy Estimated accuracy in radians
     */
    public SKRotationData(final long timestamp, final float x, final float y, final float z, final float cos, final float headingAccuracy) {
        super(SKSensorType.ROTATION, timestamp);

        this.x = x;
        this.y = y;
        this.z = z;
        this.cos = cos;
        this.headingAccuracy = headingAccuracy;
    }

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param x         Rotation axis x-component*sin(theta/2)
     * @param y         Rotation axis y-component*sin(theta/2)
     * @param z         Rotation axis z-component*sin(theta/2)
     *                  <p>
     *                  where theta is the angle of rotation
     */
    public SKRotationData(final long timestamp, final float x, final float y, final float z) {
        this(timestamp, x, y, z, 0, 0);
    }

    /**
     * Get the csv header of the Rotation sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Rotation sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,x,y,z,cos,headingAccuracy";
    }

    /**
     * Get Rotation sensor data in CSV format
     *
     * @return String in CSV format: timeIntervalSince1970, x, y, z, cos, headingAccuracy
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f,%f,%f", this.timestamp, this.x, this.y, this.z, this.cos, this.headingAccuracy);
    }

    /**
     * Get the Rotation sensor data in JSONObject format
     *
     * @return JSONObject containing the Rotation sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, x, y, z, cos, headingAccuracy
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
            subJsonObject.put("cos", this.cos);
            subJsonObject.put("headingAccuracy", this.headingAccuracy);

            jsonObject.put("rotation", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Get X component of rotation
     *
     * @return X component
     */
    @SuppressWarnings("unused")
    public float getX() {
        return this.x;
    }

    /**
     * Get Y component of rotation
     *
     * @return Y component
     */
    @SuppressWarnings("unused")
    public float getY() {
        return this.y;
    }

    /**
     * Get Z component of rotation
     *
     * @return Z component
     */
    @SuppressWarnings("unused")
    public float getZ() {
        return this.z;
    }

    /**
     * Get cos of the angle of rotation
     *
     * @return Cos(theta)
     */
    @SuppressWarnings("unused")
    public float getCos() {
        return this.cos;
    }

    /**
     * Get heading accuracy of rotation data
     *
     * @return Heading accuracy
     */
    @SuppressWarnings("unused")
    public float getHeadingAccuracy() {
        return this.headingAccuracy;
    }

}
