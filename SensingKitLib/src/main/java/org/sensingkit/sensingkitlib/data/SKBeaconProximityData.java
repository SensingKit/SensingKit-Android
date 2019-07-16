/*
 * Copyright (c) 2015. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
 * Ming-Jiun Huang, ud2601@gmail.com
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

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;
import org.json.JSONException;
import org.json.JSONObject;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

@SuppressWarnings("WeakerAccess")
public class SKBeaconProximityData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKBeaconProximityData.class.getSimpleName();

    private final Beacon mBeacon;

    public SKBeaconProximityData(final long timestamp, final @NonNull Beacon beacon) {
        super(SKSensorType.BEACON_PROXIMITY, timestamp);

        this.mBeacon = beacon;
    }

    /**
     * Get the csv header of the Beacon Proximity sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Beacon Proximity sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
    @NonNull
    public static String csvHeader() {
        return "timeIntervalSince1970,beaconType,manufacturer,id1,id2,id3,rssi,txPower,distance";
    }

    /**
     * Get the Beacon Proximity sensor data in csv format
     *
     * @return String containing the Beacon Proximity sensor data in csv format:
     * sensor type, sensor type in string, timeIntervalSince1970, beaconType, manufacturer, id1,id2,id3,rssi,txPower,distance
     */
    @Override
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d,%d,%s,%d,%d,%d,%d,%f",
                this.timestamp, this.getBeaconTypeCode(), this.getManufacturer(),
                this.getId1().toString(), this.getId2().toInt(), this.getId3().toInt(),
                this.getRssi(), this.getTxPower(), this.getDistance());
    }

    /**
     * Get the Beacon Proximity sensor data in JSONObject format
     *
     * @return JSONObject containing the Beacon Proximity sensor data in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970, beaconType, manufacturer, id1,id2,id3,rssi,txPower,distance
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
            subJsonObject.put("beaconType", this.getBeaconTypeCode());
            subJsonObject.put("manufacturer", this.getManufacturer());
            subJsonObject.put("id1", this.getId1().toString());
            subJsonObject.put("id2", this.getId2().toInt());
            subJsonObject.put("id3", this.getId3().toInt());
            subJsonObject.put("rssi", this.getRssi());
            subJsonObject.put("txPower", this.getTxPower());
            subJsonObject.put("distance", this.getDistance());

            jsonObject.put("beaconProximity", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @SuppressWarnings("unused")
    public int getBeaconTypeCode() {
        return mBeacon.getBeaconTypeCode();
    }

    @SuppressWarnings("unused")
    public int getManufacturer() {
        return mBeacon.getManufacturer();
    }

    @SuppressWarnings("unused")
    public Identifier getId1() {
        return mBeacon.getId1();
    }

    @SuppressWarnings("unused")
    public Identifier getId2() {
        return mBeacon.getId2();
    }

    @SuppressWarnings("unused")
    public Identifier getId3() {
        return mBeacon.getId3();
    }

    @SuppressWarnings("unused")
    public int getRssi() {
        return mBeacon.getRssi();
    }

    @SuppressWarnings("unused")
    public int getTxPower() {
        return mBeacon.getTxPower();
    }

    @SuppressWarnings("unused")
    public String getBluetoothAddress() {
        return mBeacon.getBluetoothAddress();
    }

    @SuppressWarnings("unused")
    public String getBluetoothName() {
        return mBeacon.getBluetoothName();
    }

    @SuppressWarnings("unused")
    public double getDistance() {
        return mBeacon.getDistance();
    }
}
