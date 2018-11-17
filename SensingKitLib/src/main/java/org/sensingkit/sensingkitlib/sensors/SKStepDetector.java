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

package org.sensingkit.sensingkitlib.sensors;

import android.content.Context;
import android.hardware.SensorEvent;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKStepDetectorConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKStepDetectorData;

public class SKStepDetector extends SKAbstractNativeSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKStepDetector.class.getName();

    public SKStepDetector(final Context context, final SKStepDetectorConfiguration configuration) throws SKException {
        super(context, SKSensorType.STEP_DETECTOR, configuration);
    }

    @Override
    protected SKAbstractData buildData(SensorEvent event)
    {
        return new SKStepDetectorData(System.currentTimeMillis());
    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKStepDetectorConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKStepDetector.",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        // Set the configuration
        super.setConfiguration(configuration);
    }

    @Override
    public SKConfiguration getConfiguration() {
        return new SKStepDetectorConfiguration((SKStepDetectorConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

}
