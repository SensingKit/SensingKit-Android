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

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.HashMap;
import java.util.Locale;


/**
 *  An instance of SKAccelerometerData encapsulates measurements related to the Accelerometer sensor.
 */
public class SKAccelerometerData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKAccelerometerData.class.getSimpleName();

    protected final float x;
    protected final float y;
    protected final float z;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param x X-axis value of the Accelerometer sensor
     *
     * @param y Y-axis value of the Accelerometer sensor
     *
     * @param z Z-axis value of the Accelerometer sensor
     */

    public SKAccelerometerData(long timestamp, float x, float y, float z) {

        super(SKSensorType.ACCELEROMETER, timestamp);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Get the csv header of the Accelerometer sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Accelerometer sensor.
     */
    @SuppressWarnings("unused")
    public static String csvHeader() {
        return "timeIntervalSince1970,x,y,z";
    }

    /**
     * Get the accelerator measurements in csv format
     *
     * @return String containing the timestamp and accelerometer measurements in csv format:  timeIntervalSince1970,x,y,z
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f", this.timestamp, this.x, this.y, this.z);
    }

    /**
     * Get the accelerator measurements in dictionary format
     *
     * @return Dictionary containing the time stamp and accelerometer measurements in dictionary format:
     * sensor type, sensor type in string, timeIntervalSince1970, accelerometer in x,y,z
     */
    @Override
    public HashMap getDataInDict() {
        HashMap multiMap = new HashMap<>();
        HashMap<String,Float> accelerationMap = new HashMap<>();

        multiMap.put("sensorType",this.getSensorType());
        multiMap.put("sensorTypeString",this.getSensorType().toString());
        multiMap.put("timestamp",this.timestamp);

        accelerationMap.put("x",this.x);
        accelerationMap.put("y",this.y);
        accelerationMap.put("z",this.z);

        multiMap.put("acceleration",accelerationMap);

        return multiMap;
    }

    /**
     * Get the X-axis acceleromator measurement
     *
     * @return Float containing the X-axis value of the Accelerometer sensor
     */
    @SuppressWarnings("unused")
    public float getX() {
        return this.x;
    }

    /**
     * Get the Y-axis accelerator measurement
     *
     * @return Float containing the y-axis value of the Accelerometer sensor
     */
    @SuppressWarnings("unused")
    public float getY() {
        return this.y;
    }

    /**
     * Get the Z-axis accelerator measurement
     *
     * @return Float containing the z-axis value of the Accelerometer sensor
     */

    @SuppressWarnings("unused")
    public float getZ() {
        return this.z;
    }

}
