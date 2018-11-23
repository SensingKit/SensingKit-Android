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

import com.google.android.gms.location.DetectedActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.HashMap;
import java.util.Locale;

/**
 * An instance of SKMotionActivityData encapsulates measurements related to the Motion Activity sensor.
 * Activity is classified as Stationary, Walking, Running, Automotive, Cycling, Tilt, or Unknown.
 */
public class SKMotionActivityData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKMotionActivityData.class.getName();

    protected final int activityType;
    protected final int confidence;

    /**
     * Initialize the instance
     *
     * @param timestamp    Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param activityType The type of the activity
     * @param confidence   Confidence percentage for the most probable activity
     */
    public SKMotionActivityData(long timestamp, int activityType, int confidence) {

        super(SKSensorType.MOTION_ACTIVITY, timestamp);

        this.activityType = activityType;
        this.confidence = confidence;
    }

    /**
     * Get the csv header of the Motion Activity sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Motion Activity sensor.
     */
    @SuppressWarnings("unused")
    public static String csvHeader() {
        return "timeIntervalSince1970,activity,activityString,confidence";
    }

    /**
     * Get the Motion Activity sensor data in csv format
     *
     * @return Activity data in csv format: timeIntervalSince1970, activity, activityString, confidence
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d,%s,%d", this.timestamp, this.activityType, getActivityString(), this.confidence);
    }

    /**
     * Get the Motion Activity sensor data in JSONObject format
     *
     * @return JSONObject containing the Motion Activity sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, activity, activityString, confidence
     */
    @Override
    public JSONObject getDataInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sensorType", this.getSensorType());
            jsonObject.put("sensorTypeString", this.getSensorType().toString());
            jsonObject.put("timestamp", this.timestamp);

            JSONObject subJsonObject = new JSONObject();
            subJsonObject.put("activity", this.activityType);
            subJsonObject.put("activityString", getActivityString());
            subJsonObject.put("confidence", this.confidence);

            jsonObject.put("motionActivity", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Get the activity type
     *
     * @return Activity type
     */
    @SuppressWarnings("unused")
    public int getActivityType() {
        return this.activityType;
    }

    /**
     * Get the confidence percentage
     *
     * @return Confidence
     */
    @SuppressWarnings("unused")
    public int getConfidence() {
        return this.confidence;
    }

    /**
     * Get the name of the activity type
     *
     * @return Name of the activity type
     */
    @SuppressWarnings("unused")
    public String getActivityString() {
        return getNameFromActivityType(this.activityType);
    }

    /**
     * Get the name of an activity type
     *
     * @param activityType The type of the activity
     * @return name
     */
    public static String getNameFromActivityType(int activityType) {

        switch (activityType) {

            case DetectedActivity.IN_VEHICLE:
                return "in_vehicle";

            case DetectedActivity.ON_BICYCLE:
                return "on_bicycle";

            case DetectedActivity.ON_FOOT:
                return "on_foot";

            case DetectedActivity.STILL:
                return "still";

            case DetectedActivity.UNKNOWN:
                return "unknown";

            case DetectedActivity.TILTING:
                return "tilting";

            case DetectedActivity.WALKING:
                return "walking";

            case DetectedActivity.RUNNING:
                return "running";

            default:
                return "unsupported";
        }

    }

}
