/*
 * Copyright (c) 2014. Queen Mary University of London
 * Kleomenis Katevas, k.katevas@qmul.ac.uk
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

import org.sensingkit.sensingkitlib.SKSensorModuleType;

import java.util.Locale;

public class SKActivityData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = "SKActivityData";

    protected final int activityType;
    protected final int confidence;

    public SKActivityData(long timestamp, int activityType, int confidence) {

        super(SKSensorModuleType.ACTIVITY, timestamp);

        this.activityType = activityType;
        this.confidence = confidence;
    }

    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d,%s,%d", this.timestamp, this.activityType, getActivityString(), this.confidence);
    }

    @SuppressWarnings("unused")
    public int getActivityType() {
        return this.activityType;
    }

    @SuppressWarnings("unused")
    public int getConfidence() {
        return this.confidence;
    }

    @SuppressWarnings("unused")
    public String getActivityString() {
        return getNameFromActivityType(this.activityType);
    }

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
