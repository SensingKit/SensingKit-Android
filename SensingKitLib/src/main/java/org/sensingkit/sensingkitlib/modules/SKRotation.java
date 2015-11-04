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

package org.sensingkit.sensingkitlib.modules;

import android.content.Context;
import android.hardware.SensorEvent;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKRotationData;

public class SKRotation extends SKAbstractNativeSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "SKRotation";

    public SKRotation(final Context context) throws SKException {
        super(context, SKSensorType.ROTATION);
    }

    @Override
    protected SKAbstractData buildData(SensorEvent event)
    {
        if (event.values.length >= 6) {
            return new SKRotationData(System.currentTimeMillis(), event.values[0], event.values[1], event.values[2], event.values[3], event.values[4]);
        }
        else {
            return new SKRotationData(System.currentTimeMillis(), event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }
}
