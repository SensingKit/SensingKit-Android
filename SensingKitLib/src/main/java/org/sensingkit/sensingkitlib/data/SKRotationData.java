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

import org.sensingkit.sensingkitlib.SKSensorModuleType;

import java.util.Locale;

public class SKRotationData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = "SKRotationData";

    protected final float x;
    protected final float y;
    protected final float z;
    protected final float cos;
    protected final float headingAccuracy;

    public SKRotationData(long timestamp, float x, float y, float z, float cos, float headingAccuracy) {

        super(SKSensorModuleType.ROTATION, timestamp);

        this.x = x;
        this.y = y;
        this.z = z;
        this.cos = cos;
        this.headingAccuracy = headingAccuracy;
    }

    public SKRotationData(long timestamp, float x, float y, float z) {
        this(timestamp, x, y, z, 0, 0);
    }

    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%f,%f,%f,%f", this.timestamp, this.x, this.y, this.z, this.cos, this.headingAccuracy);
    }

    @SuppressWarnings("unused")
    public float getX() {
        return this.x;
    }

    @SuppressWarnings("unused")
     public float getY() {
        return this.y;
    }

    @SuppressWarnings("unused")
    public float getZ() {
        return this.z;
    }

    @SuppressWarnings("unused")
    public float getCos() {
        return this.cos;
    }

    @SuppressWarnings("unused")
    public float getHeadingAccuracy() {
        return this.headingAccuracy;
    }

}
