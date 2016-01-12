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

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;


/**
 *  An instance of SKMagnetometerData encapsulates measurements related to the Magnetometer sensor.
 *  Measures the magnetic force in micro-Tesla
 */

public class SKMagnetometerData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = "SKMagnetometerData";

    protected final float x;
    protected final float y;
    protected final float z;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param x Force in X-direction
     *
     * @param y Force in Y-direction
     *
     * @param z Force in Z-direction
     */
    public SKMagnetometerData(long timestamp, float x, float y, float z) {

        super(SKSensorType.MAGNETOMETER, timestamp);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Get Magnetometer sensor data in CSV format
     *
     * @return String in CSV format: timestamp, x force, y force, z force
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f", this.timestamp, this.x, this.y, this.z);
    }

    /**
     * Get Magnetometer force in X direction
     *
     * @return force in X direction
     */
    @SuppressWarnings("unused")
    public float getX() {
        return this.x;
    }

    /**
     * Get Magnetometer force in Y direction
     *
     * @return force in Y direction
     */
    @SuppressWarnings("unused")
    public float getY() {
        return this.y;
    }

    /**
     * Get Magnetometer force in Z direction
     *
     * @return force in Z direction
     */
    @SuppressWarnings("unused")
    public float getZ() {
        return this.z;
    }

}
