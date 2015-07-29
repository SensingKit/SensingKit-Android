/*
 * Copyright (c) 2015. Queen Mary University of London
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

package org.sensingkit.sensingkitlib.modules;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.model.data.SKAbstractData;
import org.sensingkit.sensingkitlib.model.data.SKBluetoothData;

public class SKBluetooth extends SKAbstractSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "SKBluetooth";

    private BluetoothAdapter mBluetoothAdapter;

    protected SKBluetooth(Context context) throws SKException {
        super(context, SKSensorModuleType.BLUETOOTH);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            throw new SKException(TAG, "Bluetooth sensor module is not supported from the device.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND and ACTION_DISCOVERY_FINISHED
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            // Read the action from the intent
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {  // Device Found

                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                String name = device.getName();
                String address = device.getAddress();
                int rssi = (int) intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

                // Create SKBluetoothDevice and add to mBluetoothDevices array
                Log.i(TAG, "Device Found: " + name + ", " + address + ", " + rssi);

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {  // Discovery Finished

                // Build the data object
                SKAbstractData data = new SKBluetoothData(System.currentTimeMillis());

                // Clean the arrayList

                // Submit sensor data object
                submitSensorData(data);

                // Start Discovery again
                mBluetoothAdapter.startDiscovery();
            }
        }
    };

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public void startSensing() throws SKException {

        if (mBluetoothAdapter == null) {
            throw new SKException(TAG, "Bluetooth sensor module is not supported from the device.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (!mBluetoothAdapter.isEnabled()) {
            throw new SKException(TAG, "Bluetooth is not enabled.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        this.isSensing = true;

        registerLocalBroadcastManager();

        // Start Bluetooth Scanning
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public void stopSensing() {

        // Stop Bluetooth Scanning
        mBluetoothAdapter.cancelDiscovery();

        unregisterLocalBroadcastManager();

        this.isSensing = false;
    }

    private void registerLocalBroadcastManager() {
        // Register receivers for ACTION_FOUND and ACTION_DISCOVERY_FINISHED
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter finishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mApplicationContext.registerReceiver(mReceiver, foundFilter);
        mApplicationContext.registerReceiver(mReceiver, finishedFilter);
    }

    private void unregisterLocalBroadcastManager() {
        mApplicationContext.unregisterReceiver(mReceiver);
    }
}
