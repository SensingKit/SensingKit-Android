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

@SuppressWarnings("unused")
public interface SKConfiguration {

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isValidForSensor(final SKSensorType sensorType);

    void setRequestWakeLock(final boolean requestWakeLock);
    boolean getRequestWakeLock();

    void setDebugStatus(final boolean debugStatus);
    boolean getDebugStatus();
}
