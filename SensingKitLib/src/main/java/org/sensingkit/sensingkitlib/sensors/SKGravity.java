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
import android.support.annotation.NonNull;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKGravityConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKGravityData;

public class SKGravity extends SKAbstractNativeSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKGravity.class.getSimpleName();

    public SKGravity(final @NonNull Context context, final @NonNull SKGravityConfiguration configuration) throws SKException {
        super(context, SKSensorType.GRAVITY, configuration);
    }

    @Override
    @NonNull
    protected SKAbstractData buildData(final @NonNull SensorEvent event)
    {
        return new SKGravityData(System.currentTimeMillis(), event.values[0], event.values[1], event.values[2]);
    }

    @Override
    public void setConfiguration(final @NonNull SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKGravityConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKGravity.",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        // Set the configuration
        super.setConfiguration(configuration);
    }

    @Override
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKGravityConfiguration((SKGravityConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(final @NonNull SKAbstractData data) {

        // Always post sensor data
        return true;
    }
}
