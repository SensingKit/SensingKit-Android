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

import org.sensingkit.sensingkitlib.modules.SensorModuleType;

public interface SensingKitLibInterface {

    /* One Shot Sensing */

    @SuppressWarnings("unused")
    public void getDataFromSensor(SensorModuleType moduleType) throws SKException;

    /* Continuous Sensing */

    @SuppressWarnings("unused")
    public void subscribeToSensor(SensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException;

    @SuppressWarnings("unused")
    public void unsubscribeFromSensor(SensorModuleType moduleType, SKSensorDataListener dataListener) throws SKException;

    @SuppressWarnings("unused")
    public void unsubscribeAllFromSensor(SensorModuleType moduleType) throws SKException;

    @SuppressWarnings("unused")
    public void startContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException;

    @SuppressWarnings("unused")
    public void stopContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException;

    @SuppressWarnings("unused")
    public void pauseContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException;

    @SuppressWarnings("unused")
    public void unpauseContinuousSensingWithSensor(SensorModuleType moduleType) throws SKException;

    // Configuration

    // TODO: Add Configuration

}
