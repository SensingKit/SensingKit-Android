/*
 * Copyright (c) 2016. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit https://www.sensingkit.org
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
 * An instance of SKMicrophoneData encapsulates measurements related to the Microphone sensor.
 */
public class SKMicrophoneData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKMicrophoneData.class.getSimpleName();

    private final String state;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param state     Microphone sensor status (Start, Stop)
     */
    public SKMicrophoneData(final long timestamp, final @NonNull String state) {
        super(SKSensorType.STEP_DETECTOR, timestamp);

        this.state = state;
    }

    /**
     * Get the csv header of the Step Counter sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Step Counter sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,state";
    }

    /**
     * Get Step Detector sensor data in CSV format
     *
     * @return String in CSV format: timeIntervalSince1970
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%s", this.timestamp, this.state);
    }

    /**
     * Get Step Detector sensor data in JSONObject format
     *
     * @return JSONObject containing Step Detector sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, state
     */
    @Override
    @NonNull
    public JSONObject getDataInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sensorType", this.getSensorType());
            jsonObject.put("sensorTypeString", this.getSensorType().toString());
            jsonObject.put("timestamp", this.timestamp);
            jsonObject.put("state", this.state);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Get Microphone Status
     *
     * @return String with the status of the Microphone sensor
     */
    @SuppressWarnings("unused")
    public String getState() {
        return this.state;
    }
}
