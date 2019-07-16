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

import java.util.ArrayList;
import java.util.Locale;

/**
 * An instance of SKBeaconProximityCollectionData encapsulates measurements related to all the Beacon devices.
 */
public class SKBeaconProximityCollectionData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKBeaconProximityCollectionData.class.getSimpleName();

    private final ArrayList<SKBeaconProximityData> mDevices;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param devices   One SKBeaconProximityData object for each Beacon device
     */
    public SKBeaconProximityCollectionData(final long timestamp, final @NonNull ArrayList<SKBeaconProximityData> devices) {
        super(SKSensorType.BEACON_PROXIMITY, timestamp);

        this.mDevices = devices;
    }

    /**
     * Get the csv header of the Beacon Proximity sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Beacon Proximity sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return SKBeaconProximityData.csvHeader();
    }

    /**
     * Get the data for all Beacon devices in CSV format
     *
     * @return String formatted as follows: timeIntervalSince1970,device1 data, device2 data,,,
     */
    @Override
    @NonNull
    public String getDataInCSV() {

        // Calculate capacity and init StringBuilder
        int capacity = 10 * mDevices.size();
        StringBuilder stringBuilder = new StringBuilder(capacity);

        // Add deviceData
        for (SKBeaconProximityData deviceData : mDevices) {

            stringBuilder.append(String.format(Locale.US, "%s\n", deviceData.getDataInCSV()));
        }

        // Delete last \n
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        // Return in String
        return stringBuilder.toString();
    }


    /**
     * Get the data for all Beacon devices in JSONObject format
     *
     * @return JSONObject formatted as follows: device1 data, device, data,,,
     */
    @Override
    @NonNull
    public JSONObject getDataInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            // Add deviceData
            int i = 1;
            for (SKBeaconProximityData deviceData : mDevices) {
                jsonObject.put("beaconDeviceNo" + i++, deviceData.getDataInJSON());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Get Beacon device data
     *
     * @return an ArrayList containing an SKBeaconProximityData object for each Beacon device
     */
    @SuppressWarnings("unused")
    public ArrayList<SKBeaconProximityData> getDevices() {
        return this.mDevices;
    }
}
