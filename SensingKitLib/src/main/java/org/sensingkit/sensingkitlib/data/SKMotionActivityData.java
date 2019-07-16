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

    @SuppressWarnings("WeakerAccess")
    public enum SKActivityType {

        /**
         * TODO
         */
        STATIONARY("Still", DetectedActivity.STILL),

        /**
         * TODO
         */
        WALKING("Walking", DetectedActivity.WALKING),

        /**
         * TODO
         */
        RUNNING("Running", DetectedActivity.RUNNING),

        /**
         * TODO
         */
        AUTOMOTIVE("Automotive", DetectedActivity.IN_VEHICLE),

        /**
         * TODO
         */
        CYCLING("Cycling", DetectedActivity.ON_BICYCLE);

        private final @NonNull String activityType;
        private final int activityTypeCode;

        SKActivityType(final @NonNull String activityType, final int activityTypeCode) {
            this.activityType = activityType;
            this.activityTypeCode = activityTypeCode;
        }

        public static SKActivityType valueOf(final int activityTypeCode) {

            switch (activityTypeCode) {

                case DetectedActivity.STILL:
                    return STATIONARY;

                case DetectedActivity.WALKING:
                    return WALKING;

                case DetectedActivity.RUNNING:
                    return RUNNING;

                case DetectedActivity.IN_VEHICLE:
                    return AUTOMOTIVE;

                case DetectedActivity.ON_BICYCLE:
                    return CYCLING;

                default:
                    throw new RuntimeException("Unsupported SKActivityType with code: " + activityTypeCode);
            }
        }

        @SuppressWarnings("unused")
        public @NonNull String getActivityType() {
            return this.activityType;
        }

        @SuppressWarnings("unused")
        public int getActivityTypeCode() {
            return this.activityTypeCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getActivityType();
        }
    }

    public enum SKTransitionType {

        /**
         * TODO
         */
        ENTER("Enter", ActivityTransition.ACTIVITY_TRANSITION_ENTER),

        /**
         * TODO
         */
        EXIT("Exit", ActivityTransition.ACTIVITY_TRANSITION_EXIT);

        private final @NonNull String transitionType;
        private final int transitionTypeCode;

        SKTransitionType(final @NonNull String transitionType, final int transitionTypeCode) {
            this.transitionType = transitionType;
            this.transitionTypeCode = transitionTypeCode;
        }

        @SuppressWarnings("WeakerAccess")
        public static SKTransitionType valueOf(final int transitionTypeCode) {

            switch (transitionTypeCode) {

                case ActivityTransition.ACTIVITY_TRANSITION_ENTER:
                    return ENTER;

                case ActivityTransition.ACTIVITY_TRANSITION_EXIT:
                    return EXIT;

                default:
                    throw new RuntimeException("Unsupported SKTransitionType with code: " + transitionTypeCode);
            }
        }

        @SuppressWarnings("unused")
        public @NonNull String getTransitionType() {
            return this.transitionType;
        }

        @SuppressWarnings("unused")
        public int getTransitionTypeCode() {
            return this.transitionTypeCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getTransitionType();
        }
    }

    private final SKActivityType activityType;
    private final SKTransitionType transitionType;

    /**
     * Initialize the instance
     *
     * @param timestamp    Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param activityTypeCode The type of the activity
     * @param transitionTypeCode TODO
     */
    public SKMotionActivityData(final long timestamp, final int activityTypeCode, final int transitionTypeCode) {

        super(SKSensorType.MOTION_ACTIVITY, timestamp);

        this.activityType = SKActivityType.valueOf(activityTypeCode);
        this.transitionType = SKTransitionType.valueOf(transitionTypeCode);
    }

    /**
     * Get the csv header of the Motion Activity sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Motion Activity sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,activityCode,activityString,transitionCode,transitionString";
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
        return String.format(Locale.US, "%d,%d,%s", this.timestamp, this.activityType.getActivityTypeCode(), this.activityType.getActivityType());
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
            subJsonObject.put("activity", this.activityType.getActivityTypeCode());
            subJsonObject.put("activityString", this.activityType.getActivityType());
            subJsonObject.put("transition", this.transitionType.getTransitionTypeCode());
            subJsonObject.put("transitionString", this.transitionType.getTransitionType());

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
    public SKActivityType getActivityType() {
        return this.activityType;
    }

    /**
     * Get the activity type
     *
     * @return Activity type
     *
     */
    @SuppressWarnings("unused")
    public SKTransitionType getTransitionType() {
        return this.transitionType;
    }

}
