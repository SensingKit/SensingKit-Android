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
 * An instance of SKBluetoothData encapsulates measurements related to one Bluetooth device.
 */
public class SKBluetoothData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKBluetoothData.class.getSimpleName();

    private final String name;
    private final String address;
    private final int rssi;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param name      Device name
     * @param address   Device Address
     * @param rssi      Device RSSI
     */
    public SKBluetoothData(final long timestamp, final @NonNull String name, final @NonNull String address, final int rssi) {
        super(SKSensorType.BLUETOOTH, timestamp);

        this.name = name;
        this.address = address;
        this.rssi = rssi;
    }

    /**
     * Get the csv header of the Bluetooth sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Bluetooth sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,name,address,rssi";
    }

    /**
     * Get Bluetooth data in CSV format
     *
     * @return String formatted as a CSV, containing: timeIntervalSince1970,name,address,rssi
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%s,%s,%d", this.timestamp, this.name, this.address, this.rssi);
    }


    /**
     * Get the Bluetooth data in JSONObject format
     *
     * @return JSONObject containing the bluetooth data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, name,address,rssi
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
            subJsonObject.put("name", this.name);
            subJsonObject.put("address", this.address);
            subJsonObject.put("rssi", this.rssi);

            jsonObject.put("bluetooth", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Get the Bluetooth device name
     *
     * @return Bluetooth device name
     */
    @SuppressWarnings("unused")
    @NonNull
    public String getName() {
        return this.name;
    }

    /**
     * Get the Bluetooth device address
     *
     * @return Bluetooth device address
     */
    @SuppressWarnings("unused")
    @NonNull
    public String getAddress() {
        return this.address;
    }

    /**
     * Get the Bluetooth device RSSI
     *
     * @return Bluetooth device RSSI
     */
    @SuppressWarnings("unused")
    public int getRssi() {
        return this.rssi;
    }
}
