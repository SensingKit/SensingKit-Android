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

import org.sensingkit.sensingkitlib.SKSensorType;

public class SKAudioLevelConfiguration extends SKAbstractConfiguration {

    public SKAudioLevelConfiguration() {
        super();

        // Set default values
    }

    @SuppressWarnings("unused")
    public SKAudioLevelConfiguration(SKAudioLevelConfiguration configuration) {
        super();
    }

    public boolean isValidForSensor(final SKSensorType sensorType) {
        return (sensorType == SKSensorType.AUDIO_LEVEL);
    }
}
