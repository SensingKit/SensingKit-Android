/*
 * Copyright (c) 2015. Queen Mary University of London
 * Kleomenis Katevas, k.katevas@qmul.ac.uk
 * Ming-Jiun Huang, ud2601@gmail.com
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

package org.sensingkit.sensingkitlib.data;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

public class SKiBeaconData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKiBeaconData.class.getName();

    protected final String namespaceId;
    protected final String instanceId;
    protected final int rssi;
    protected final int txPower;

    public SKiBeaconData(long timestamp, String namespaceId, String instanceId, int rssi, int txPower) {
        super(SKSensorType.IBEACON, timestamp);
        this.namespaceId = namespaceId; //**
        this.instanceId = instanceId; //**
        this.rssi = rssi;
        this.txPower = txPower;
    }

    @Override
    public String getDataInCSV() {
        //TODO Check format
        return String.format(Locale.US, "%d,%s,%s,%d,%d", this.timestamp, this.namespaceId, this.instanceId, this.rssi, this.txPower );
    }

    @SuppressWarnings("unused")
    public String getNamespaceId(){
        return this.namespaceId;
    }

    @SuppressWarnings("unused")
    public String getInstanceId(){
        return this.instanceId;
    }

    @SuppressWarnings("unused")
    public int getRssi(){
        return this.rssi;
    }

    @SuppressWarnings("unused")
    public int getTxPower(){
        return this.txPower;
    }
}
