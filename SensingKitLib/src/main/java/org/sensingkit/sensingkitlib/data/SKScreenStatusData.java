/*
 * Copyright (c) 2015. Queen Mary University of London
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

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 *  An instance of SKScreenStatusData encapsulates measurements related to the Screen Status sensor.
 */
public class SKScreenStatusData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKScreenStatusData.class.getName();

    public static final int SCREEN_OFF = 0;
    public static final int SCREEN_ON = 1;
    public static final int SCREEN_UNKNOWN = 2;

    protected final int status;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param status Screen status
     */
    public SKScreenStatusData(long timestamp, int status) {

        super(SKSensorType.SCREEN_STATUS, timestamp);

        this.status = status;
    }

    /**
     * Get Screen Status sensor data in CSV format
     *
     * @return String in CSV format: timestamp, screen status string
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%s", this.timestamp, this.getStatusString());
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
     * @return Screen status as a string:  "screen off", "screen on", or "unknown"
     */
    @SuppressWarnings("unused")
    public String getStatusString() {

        switch (this.status) {
            case SCREEN_OFF:
                return "screen off";

            case SCREEN_ON:
                return "screen on";

            default:
                return "unknown";
        }
    }
}
