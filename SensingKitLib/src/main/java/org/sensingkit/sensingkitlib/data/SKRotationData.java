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
 *  An instance of SKRotationData encapsulates measurements related to the Rotation sensor.
 */
public class SKRotationData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKRotationData.class.getName();

    protected final float x;
    protected final float y;
    protected final float z;
    protected final float cos;
    protected final float headingAccuracy;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param x Rotation axis x-component*sin(theta/2)
     *
     * @param y Rotation axis y-component*sin(theta/2)
     *
     * @param z Rotation axis z-component*sin(theta/2)
     *
     * @param cos Cos(theta)
     *
     * where theta is the angle of rotation
     *
     * @param headingAccuracy Estimated accuracy in radians
     */
    public SKRotationData(long timestamp, float x, float y, float z, float cos, float headingAccuracy) {

        super(SKSensorType.ROTATION, timestamp);

        this.x = x;
        this.y = y;
        this.z = z;
        this.cos = cos;
        this.headingAccuracy = headingAccuracy;
    }

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param x Rotation axis x-component*sin(theta/2)
     *
     * @param y Rotation axis y-component*sin(theta/2)
     *
     * @param z Rotation axis z-component*sin(theta/2)
     *
     * where theta is the angle of rotation
     */
    public SKRotationData(long timestamp, float x, float y, float z) {
        this(timestamp, x, y, z, 0, 0);
    }

    /**
     * Get Rotation sensor data in CSV format
     *
     * @return String in CSV format: timestamp, x, y, z, cos, headingAccuracy
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f,%f,%f", this.timestamp, this.x, this.y, this.z, this.cos, this.headingAccuracy);
    }

    /**
     * Get X component of rotation
     *
     * @return X component
     */
    @SuppressWarnings("unused")
    public float getX() {
        return this.x;
    }

    /**
     * Get Y component of rotation
     *
     * @return Y component
     */
    @SuppressWarnings("unused")
     public float getY() {
        return this.y;
    }

    /**
     * Get Z component of rotation
     *
     * @return Z component
     */
    @SuppressWarnings("unused")
    public float getZ() {
        return this.z;
    }

    /**
     * Get cos of the angle of rotation
     *
     * @return Cos(theta)
     */
    @SuppressWarnings("unused")
    public float getCos() {
        return this.cos;
    }

    /**
     * Get heading accuracy of rotation data
     *
     * @return Heading accuracy
     */
    @SuppressWarnings("unused")
    public float getHeadingAccuracy() {
        return this.headingAccuracy;
    }

}
