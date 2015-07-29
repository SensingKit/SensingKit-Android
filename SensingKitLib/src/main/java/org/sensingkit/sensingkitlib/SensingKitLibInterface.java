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

package org.sensingkit.sensingkitlib;

import org.sensingkit.sensingkitlib.data.SKSensorData;

@SuppressWarnings("unused")
public interface SensingKitLibInterface {

    /** Sensor Registration */

    void registerSensorModule(SKSensorModuleType moduleType) throws SKException;

    void deregisterSensorModule(SKSensorModuleType moduleType) throws SKException;

    boolean isSensorModuleRegistered(SKSensorModuleType moduleType) throws SKException;

    /** Configuration */
    // TODO: Add Configuration


    /** One Shot Sensing */

    SKSensorData getDataFromSensor(SKSensorModuleType moduleType) throws SKException;


    /** Continuous Sensing */

    void subscribeSensorDataListener(SKSensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException;

    void unsubscribeSensorDataListener(SKSensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException;

    void unsubscribeAllSensorDataListeners(SKSensorModuleType moduleType) throws SKException;

    void startContinuousSensingWithSensor(SKSensorModuleType moduleType) throws SKException;

    void stopContinuousSensingWithSensor(SKSensorModuleType moduleType) throws SKException;

    boolean isSensorModuleSensing(SKSensorModuleType moduleType) throws SKException;

    /** Time */

    long getCurrentTimeMillis();

    long getNanoTime();

}
