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

import android.location.Location;
import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;


/**
 * An instance of SKLocationData encapsulates measurements related to the Location sensor.
 */
public class SKLocationData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKLocationData.class.getSimpleName();

    private final Location location;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param location  Location object
     */
    public SKLocationData(final long timestamp, final @NonNull Location location) {
        super(SKSensorType.LOCATION, timestamp);

        this.location = location;
    }

    /**
     * Get the csv header of the Location sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Location sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,latitude,longitude,altitude,accuracy";
    }

    /**
     * Get location sensor data in CSV format
     *
     * @return String in CSV format: timeIntervalSince1970, latitude, longitude, altitude, accuracy
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f,%f", this.timestamp,
                this.location.getLatitude(), this.location.getLongitude(), this.location.getAltitude(),
                this.location.getAccuracy());
    }

    /**
     * Get the location sensor data in JSONObject format
     *
     * @return JSONObject containing the location sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, latitude, longitude, altitude, accuracy
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
            subJsonObject.put("latitude", this.location.getLatitude());
            subJsonObject.put("longitude", this.location.getLongitude());
            subJsonObject.put("altitude", this.location.getAltitude());
            subJsonObject.put("accuracy", this.location.getAccuracy());

            jsonObject.put("location", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Get Location object
     *
     * @return Location object
     */
    @SuppressWarnings("unused")
    public Location getLocation() {
        return location;
    }

    /**
     * Get Latitude
     *
     * @return Latitude
     */
    @SuppressWarnings("unused")
    public double getLatitude() {
        return location.getLatitude();
    }

    /**
     * Get Longitude
     *
     * @return Longitude
     */
    @SuppressWarnings("unused")
    public double getLongitude() {
        return location.getLongitude();
    }

    /**
     * Get Altitude
     *
     * @return Altitude
     */
    @SuppressWarnings("unused")
    public double getAltitude() {
        return location.getAltitude();
    }

    /**
     * Get Accuracy
     *
     * @return Accuracy
     */
    @SuppressWarnings("unused")
    public float getAccuracy() {
        return location.getAccuracy();
    }

}
