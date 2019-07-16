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
import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKAmbientTemperatureConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKAmbientTemperatureData;

public class SKAmbientTemperature extends SKAbstractNativeSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKAmbientTemperature.class.getSimpleName();

    public SKAmbientTemperature(final Context context, final @NonNull SKAmbientTemperatureConfiguration configuration) throws SKException {
        super(context, SKSensorType.AMBIENT_TEMPERATURE, configuration);
    }

    @Override
    @NonNull
    protected SKAbstractData buildData(final SensorEvent event) {
        return new SKAmbientTemperatureData(System.currentTimeMillis(), event.values[0]);
    }

    @Override
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKAmbientTemperatureConfiguration((SKAmbientTemperatureConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(final @NonNull SKAbstractData data) {

        // Always post sensor data
        return true;
    }
}
