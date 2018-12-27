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

public enum SKExceptionErrorCode {

    /**
     * Unknown error.
     */
    UNKNOWN_ERROR,


    // Sensor Availability

    /**
     * Sensor is not available.
     */
    SENSOR_NOT_AVAILABLE,


    // Sensor Registration

    /**
     * Sensor is already registered.
     */
    SENSOR_ALREADY_REGISTERED,

    /**
     * Sensor is not registered.
     */
    SENSOR_NOT_REGISTERED,

    /**
     * Sensor could not be initialized.
     */
    SENSOR_ERROR,


    // Sensor Sensing

    /**
     * Sensor is currently sensing.
     */
    SENSOR_CURRENTLY_SENSING,

    /**
     * Sensor is currently not sensing.
     */
    SENSOR_CURRENTLY_NOT_SENSING,


    // Sensor Data Handlers

    /**
     * Sensor Data Handler is already registered.
     */
    DATA_HANDLER_ALREADY_REGISTERED,

    /**
     * Sensor Data Handler is not registered
     */
    DATA_HANDLER_NOT_REGISTERED,


    // Sensor Configuration

    /**
     * Configuration is not compatible with the registered sensor.
     */
    CONFIGURATION_NOT_VALID,


    // Permissions

    /**
     * Permission is missing from the manifest file
     */
    PERMISSION_MISSING,

    /**
     * File writer does not have access to write in the this path
     */
    FILE_WRITER_PERMISSION_DENIED,

    /**
     * File already exists
     */
    FILE_ALREADY_EXISTS
}
