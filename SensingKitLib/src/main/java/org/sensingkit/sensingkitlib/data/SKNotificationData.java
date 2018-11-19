/*
 * Copyright (c) 2017. Kleomenis Katevas
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


import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.HashMap;
import java.util.Locale;

public class SKNotificationData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKNotificationData.class.getName();

    private final String actionType;
    private final String packageName;

    /**
     * TODO
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     */
    public SKNotificationData(long timestamp, String actionType, String packageName) {

        super(SKSensorType.NOTIFICATION, timestamp);

        this.actionType = actionType;
        this.packageName = packageName;
    }

    /**
     * Get the csv header of the Notification sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Notification sensor.
     */
    @SuppressWarnings("unused")
    public static String csvHeader() {
        return "timeIntervalSince1970,actionType,packageName";
    }

    /**
     * Get the Notification sensor data in csv format
     *
     * @return Notification data in csv format: timeIntervalSince1970,actionType,packageName
     *
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%s,%s", this.timestamp, this.actionType, this.packageName);
    }


    /**
     * Get the Notification sensor data in dictionary format
     *
     * @return Dictionary containing the Notification sensor data in dictionary format:
     * sensor type, sensor type in string, timeIntervalSince1970, actionType, packageName
     */
    @Override
    public HashMap getDataInDict() {
        HashMap multiMap = new HashMap<>();
        HashMap notificationMap = new HashMap<>();

        multiMap.put("sensorType",this.getSensorType());
        multiMap.put("sensorTypeString",this.getSensorType().toString());
        multiMap.put("timestamp",this.timestamp);

        notificationMap.put("actionType",this.actionType);
        notificationMap.put("packageName",this.packageName);

        multiMap.put("notification",notificationMap);

        return(multiMap);
    }

    /**
     * Get the notification action type (posted, removed)
     *
     * @return actionType (posted/removed)
     */
    @SuppressWarnings("unused")
    public String getActionType() {
        return this.actionType;
    }

    /**
     * Get the notification package name
     *
     * @return package name
     */
    @SuppressWarnings("unused")
    public String getPackageName() {
        return this.packageName;
    }
}
