/*
 * Copyright (c) 2015. Kleomenis Katevas
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
 * An instance of SKScreenStatusData encapsulates measurements related to the Screen Status sensor.
 */
public class SKScreenStatusData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKScreenStatusData.class.getSimpleName();

    public static final int SCREEN_OFF = 0;
    public static final int SCREEN_ON = 1;
    public static final int SCREEN_UNLOCKED = 2;
    public static final int SCREEN_UNKNOWN = 3;

    private final int status;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param status    Screen status
     */
    public SKScreenStatusData(final long timestamp, final int status) {
        super(SKSensorType.SCREEN_STATUS, timestamp);

        this.status = status;
    }

    /**
     * Get the csv header of the Screen Status sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Screen Status sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,status,statusString";
    }

    /**
     * Get Screen Status sensor data in CSV format
     *
     * @return String in CSV format: timestamp, status, statusString
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d,%s", this.timestamp, this.getStatus(), this.getStatusString());
    }

    /**
     * Get Screen Status data in JSONObject format
     *
     * @return JSONObject containing the Screen Status data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, status, statusString
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
            subJsonObject.put("status", this.getStatus());
            subJsonObject.put("statusString", this.getStatusString());

            jsonObject.put("screenStatus", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Get screen status
     *
     * @return Screen status as an int
     */
    @SuppressWarnings("unused")
    public int getStatus() {
        return this.status;
    }

    /**
     * Get screen status as a string
     *
     * @return Screen status as a string:  "off", "on", "unlocked" or "unknown"
     */
    @SuppressWarnings("unused")
    public String getStatusString() {

        switch (this.status) {
            case SCREEN_OFF:
                return "off";

            case SCREEN_ON:
                return "on";

            case SCREEN_UNLOCKED:
                return "unlocked";

            default:
                return "unknown";
        }
    }
}
