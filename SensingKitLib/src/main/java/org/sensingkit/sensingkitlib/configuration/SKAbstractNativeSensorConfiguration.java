/*
 * Copyright (c) 2016. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit https://www.sensingkit.org
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

package org.sensingkit.sensingkitlib.configuration;

import android.hardware.SensorManager;

@SuppressWarnings("WeakerAccess")
public abstract class SKAbstractNativeSensorConfiguration extends SKAbstractConfiguration {

    @SuppressWarnings({"unused", "WeakerAccess"})
    public final class SKSensorDelay {

        public static final int FASTEST = SensorManager.SENSOR_DELAY_FASTEST;
        public static final int GAME    = SensorManager.SENSOR_DELAY_GAME;
        public static final int NORMAL  = SensorManager.SENSOR_DELAY_NORMAL;
        public static final int UI      = SensorManager.SENSOR_DELAY_UI;

        SKSensorDelay() {
            throw new RuntimeException();
        }
    }

    protected int samplingRate;

    public SKAbstractNativeSensorConfiguration() {
        super();

        // Set default values
        this.samplingRate = SKSensorDelay.NORMAL;
    }

    public SKAbstractNativeSensorConfiguration(final int samplingRate) {
        super();

        this.samplingRate = samplingRate;
    }

    @SuppressWarnings("unused")
    public int getSamplingRate() {
        return this.samplingRate;
    }

    @SuppressWarnings("unused")
    public void setSamplingRate(final int samplingRate) {
        this.samplingRate = samplingRate;
    }
}
