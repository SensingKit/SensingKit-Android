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
import org.sensingkit.sensingkitlib.SKSensorModuleType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKLightData;

public class SKLight extends SKAbstractNativeSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "SKLight";

    private float lastLightSensed = Float.MAX_VALUE;

    public SKLight(final Context context) throws SKException {
        super(context, SKSensorModuleType.LIGHT);
    }

    @Override
    protected SKAbstractData buildData(SensorEvent event)
    {
        return new SKLightData(System.currentTimeMillis(), event.values[0]);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Only post when light value changes

        float light = ((SKLightData)data).getLight();

        boolean shouldPost = (lastLightSensed != light);

        if (shouldPost) {
            this.lastLightSensed = light;
        }

        return shouldPost;
    }

    public void stopSensing() {

        super.stopSensing();

        // Clear last sensed values
        lastLightSensed = Float.MAX_VALUE;
    }

}
