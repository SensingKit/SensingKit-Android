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

package org.sensingkit.sensingkitlib;

import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.data.SKSensorData;


public interface SKSensorDataHandler {
    /**
     *  Data handler to be invoked when new sensor data is available. You can cast the sensorData object
     *  into the actual sensor data (e.g. SKAccelerometerData), based on the reported sensorType.
     *  <br>
     *  parameter sensorType The type of the sensor producing the SKSensorData object.<br>
     *  parameter sensorData The new sensor data produced by the SKSensorType sensor.
     */
    void onDataReceived(final SKSensorType sensorType, final @NonNull SKSensorData sensorData);
}
