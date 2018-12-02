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

import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.DetectedActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 *  An instance of SKMotionActivityData encapsulates measurements related to the Motion Activity sensor.
 *  Activity is classified as Stationary, Walking, Running, Automotive or Cycling.
 */
public class SKMotionActivityData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKMotionActivityData.class.getSimpleName();

    public final class ActivityType {

        public static final int STATIONARY  = DetectedActivity.STILL;
        public static final int WALKING     = DetectedActivity.WALKING;
        public static final int RUNNING     = DetectedActivity.RUNNING;
        public static final int AUTOMOTIVE  = DetectedActivity.IN_VEHICLE;
        public static final int CYCLING     = DetectedActivity.ON_BICYCLE;

        ActivityType() {
            throw new RuntimeException();
        }
    }

    public final class TransitionType {

        public static final int ENTER  = ActivityTransition.ACTIVITY_TRANSITION_ENTER;
        public static final int EXIT  = ActivityTransition.ACTIVITY_TRANSITION_EXIT;

        TransitionType() {
            throw new RuntimeException();
        }
    }

    private final int activityType;
    private final int transitionType;

    /**
     * Initialize the instance
     *
     * @param timestamp    Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param activityType The type of the activity
     * @param transitionType Confidence percentage for the most probable activity
     */
    public SKMotionActivityData(final long timestamp, final int activityType, final int transitionType) {

        super(SKSensorType.MOTION_ACTIVITY, timestamp);

        this.activityType = activityType;
        this.transitionType = transitionType;
    }

    /**
     * Get the csv header of the Motion Activity sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Motion Activity sensor.
     */
    @SuppressWarnings("unused")
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,activity,activityString,transition,transitionString";
    }

    /**
     * Get the Motion Activity sensor data in csv format
     *
     * @return Activity data in csv format: timeIntervalSince1970, activity, activityString
     *
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d,%s", this.timestamp, this.activityType, getActivityString());
    }

    /**
     * Get the Motion Activity sensor data in JSONObject format
     *
     * @return JSONObject containing the Motion Activity sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, activity, activityString, confidence
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
            subJsonObject.put("activity", this.activityType);
            subJsonObject.put("activityString", getActivityString());
            subJsonObject.put("transition", this.transitionType);
            subJsonObject.put("transitionString", this.getTransitionString());

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
     * Get the name of the activity type
     *
     * @return Name of the activity type
     */
    @SuppressWarnings("unused")
    public String getActivityString() {
        return getNameFromActivityType(this.activityType);
    }

    /**
     * Get the activity type
     *
     * @return Activity type
     *
     */
    @SuppressWarnings("unused")
    public int getTransitionType() {
        return this.transitionType;
    }

    /**
     * Get the name of the activity type
     *
     * @return Name of the activity type
     */
    @SuppressWarnings("unused")
    public String getTransitionString() {
        return getNameFromTransitionType(this.transitionType);
    }

    /**
     * Get the name of an activity type
     *
     * @param activityType The type of the activity
     * @return name
     */
    public static String getNameFromActivityType(int activityType) {

        switch (activityType) {

            case ActivityType.STATIONARY:
                return "stationary";

            case ActivityType.WALKING:
                return "walking";

            case ActivityType.RUNNING:
                return "running";

            case ActivityType.AUTOMOTIVE:
                return "automotive";

            case ActivityType.CYCLING:
                return "cycling";

            default:
                return "unsupported";
        }

    }

    /**
     * Get the name of an activity type
     *
     * @param transitionType The type of the transition (i.e. 'enter' or 'exit')
     *
     * @return name
     */
    public static String getNameFromTransitionType(int transitionType) {

        switch (transitionType) {

            case TransitionType.ENTER:
                return "enter";

            case TransitionType.EXIT:
                return "exit";

            default:
                return "unsupported";
        }

    }

}
