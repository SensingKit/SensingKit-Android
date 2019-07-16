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

import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorDataHandler;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;

@SuppressWarnings("unused")
public interface SKSensor {

    SKSensorType getSensorType();
    @NonNull String getSensorName();

    void startSensing() throws SKException;
    void stopSensing() throws SKException;
    boolean isSensing();

    void subscribeSensorDataHandler(final @NonNull SKSensorDataHandler handler) throws SKException;
    void unsubscribeSensorDataHandler(final @NonNull SKSensorDataHandler handler) throws SKException;
    void unsubscribeAllSensorDataHandlers();

    void setConfiguration(final @NonNull SKConfiguration configuration) throws SKException;
    @NonNull SKConfiguration getConfiguration();

    void sensorDeregistered();

    String[] getRequiredPermissions();
}
