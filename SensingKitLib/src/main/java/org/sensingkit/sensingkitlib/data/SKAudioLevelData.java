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
 * An instance of SKAudioLevelData encapsulates measurements related to the Audio Level sensor.
 */
public class SKAudioLevelData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKAudioLevelData.class.getSimpleName();

    private final int level;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param level     - Audio Level
     */
    public SKAudioLevelData(final long timestamp, final int level) {
        super(SKSensorType.AUDIO_LEVEL, timestamp);

        this.level = level;
    }

    /**
     * Get the csv header of the Audio Level sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Audio Level sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,level";
    }

    /**
     * Get the audio level measurement in csv format
     *
     * @return String containing the timestamp and audio level measurements in csv format: timeIntervalSince1970,level
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d", this.timestamp, this.level);
    }

    /**
     * Get the audio level measurement in JSONObject format
     *
     * @return JSONObject containing the timestamp and audio level measurements in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, level
     */
    @Override
    @NonNull
    public JSONObject getDataInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sensorType", this.getSensorType());
            jsonObject.put("sensorTypeString", this.getSensorType().toString());
            jsonObject.put("timestamp", this.timestamp);
            jsonObject.put("audioLevel", this.level);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Get the audio level only
     *
     * @return Audio level
     */
    @SuppressWarnings("unused")
    public int getLevel() {
        return this.level;
    }

}
