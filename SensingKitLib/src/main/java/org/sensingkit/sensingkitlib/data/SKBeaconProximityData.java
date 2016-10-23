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

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

public class SKBeaconProximityData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKBeaconProximityData.class.getName();

    protected final Beacon mBeacon;

    public SKBeaconProximityData(long timestamp, Beacon beacon) {
        super(SKSensorType.BEACON_PROXIMITY, timestamp);
        this.mBeacon = beacon;
    }

    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d,%d,%s,%d,%d,%d,%d,%f",
                this.timestamp, this.getBeaconTypeCode(), this.getManufacturer(),
                this.getId1().toUuid().toString(), this.getId2().toInt(), this.getId3().toInt(),
                this.getRssi(), this.getTxPower(), this.getDistance() );
    }

    @SuppressWarnings("unused")
    public int getBeaconTypeCode() {
        return mBeacon.getBeaconTypeCode();
    }

    @SuppressWarnings("unused")
    public int getManufacturer() {
        return mBeacon.getManufacturer();
    }

    @SuppressWarnings("unused")
    public Identifier getId1(){
        return mBeacon.getId1();
    }

    @SuppressWarnings("unused")
    public Identifier getId2(){
        return mBeacon.getId2();
    }

    @SuppressWarnings("unused")
    public Identifier getId3(){
        return mBeacon.getId3();
    }

    @SuppressWarnings("unused")
    public int getRssi(){
        return mBeacon.getRssi();
    }

    @SuppressWarnings("unused")
    public int getTxPower(){
        return mBeacon.getTxPower();
    }

    @SuppressWarnings("unused")
    public String getBluetoothAddress() {
        return mBeacon.getBluetoothAddress();
    }

    @SuppressWarnings("unused")
    public String getBluetoothName() {
        return mBeacon.getBluetoothName();
    }

    @SuppressWarnings("unused")
    public double getDistance() {
        return mBeacon.getDistance();
    }
}
