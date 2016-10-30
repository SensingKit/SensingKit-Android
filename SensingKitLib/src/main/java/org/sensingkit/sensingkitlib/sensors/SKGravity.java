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

package org.sensingkit.sensingkitlib.sensors;

import android.content.Context;
import android.hardware.SensorEvent;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKGravityConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKGravityData;

public class SKGravity extends SKAbstractNativeSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKGravity.class.getName();

    public SKGravity(final Context context, final SKGravityConfiguration configuration) throws SKException {
        super(context, SKSensorType.GRAVITY, configuration);
    }

    @Override
    protected SKAbstractData buildData(SensorEvent event)
    {
        return new SKGravityData(System.currentTimeMillis(), event.values[0], event.values[1], event.values[2]);
    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKGravityConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKMicrophone.",
                    SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        super.setConfiguration(configuration);

        // Cast the configuration instance
        SKGravityConfiguration gravityConfiguration = (SKGravityConfiguration)configuration;

        // Make the required updates on the sensor
    }

    @Override
    public SKConfiguration getConfiguration() {
        return new SKGravityConfiguration((SKGravityConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }
}
