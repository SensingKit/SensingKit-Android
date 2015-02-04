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
import org.sensingkit.sensingkitlib.model.data.AbstractData;
import org.sensingkit.sensingkitlib.model.data.StepDetectorData;

public class StepDetector extends AbstractNativeSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "StepDetector";

    public StepDetector(final Context context) throws SKException {
        super(context, SensorModuleType.STEP_DETECTOR);
    }

    @Override
    protected AbstractData buildData(SensorEvent event)
    {
        return new StepDetectorData(System.currentTimeMillis());
    }

    @Override
    protected boolean shouldPostSensorData(AbstractData data) {

        // Always post sensor data
        return true;
    }

}
