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

package org.sensingkit.sensingkitlib.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorModuleType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKBatteryData;

public class SKBattery extends SKAbstractSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "SKBattery";

    // Last data sensed
    private int mLastLevelSensed = Integer.MAX_VALUE;
    private int mLastScaleSensed = Integer.MAX_VALUE;
    private int mLastTemperatureSensed = Integer.MAX_VALUE;
    private int mLastVoltageSensed = Integer.MAX_VALUE;
    private int mLastPluggedSensed = Integer.MAX_VALUE;
    private int mLastStatusSensed = Integer.MAX_VALUE;
    private int mLastHealthSensed = Integer.MAX_VALUE;

    private final BroadcastReceiver mBroadcastReceiver;

    public SKBattery(final Context context) throws SKException {
        super(context, SKSensorModuleType.BATTERY);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // Read Battery
                int level = intent.getIntExtra("level", -1);
                int scale = intent.getIntExtra("scale", -1);
                int temperature = intent.getIntExtra("temperature", 0);
                int voltage = intent.getIntExtra("voltage", 0);
                int plugged = intent.getIntExtra("plugged", -1);
                int status = intent.getIntExtra("status", 0);
                int health = intent.getIntExtra("health", 0);

                // Build the data object
                SKAbstractData data = new SKBatteryData(System.currentTimeMillis(), level, scale, temperature, voltage, plugged, status, health);

                // Submit sensor data object
                submitSensorData(data);
            }
        };
    }

    @Override
    public void startSensing() {

        this.isSensing = true;

        registerLocalBroadcastManager();
    }

    @Override
    public void stopSensing() {

        unregisterLocalBroadcastManager();

        this.isSensing = false;

        // Clear last sensed values
        mLastLevelSensed = Integer.MAX_VALUE;
        mLastScaleSensed = Integer.MAX_VALUE;
        mLastTemperatureSensed = Integer.MAX_VALUE;
        mLastVoltageSensed = Integer.MAX_VALUE;
        mLastPluggedSensed = Integer.MAX_VALUE;
        mLastStatusSensed = Integer.MAX_VALUE;
        mLastHealthSensed = Integer.MAX_VALUE;
    }

    private void registerLocalBroadcastManager() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mApplicationContext.registerReceiver(mBroadcastReceiver, filter);
    }

    private void unregisterLocalBroadcastManager() {
        mApplicationContext.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Only post when specific values changed

        int level = ((SKBatteryData)data).getLevel();
        int scale = ((SKBatteryData)data).getScale();
        int temperature = ((SKBatteryData)data).getTemperature();
        int voltage = ((SKBatteryData)data).getVoltage();
        int plugged = ((SKBatteryData)data).getPlugged();
        int status = ((SKBatteryData)data).getBatteryStatus();
        int health = ((SKBatteryData)data).getBatteryHealth();

        // Ignore Temperature and Voltage
        boolean shouldPost = (mLastLevelSensed != level ||
                              mLastScaleSensed != scale ||
                              mLastPluggedSensed != plugged ||
                              mLastStatusSensed != status ||
                              mLastHealthSensed != health );

        if (shouldPost) {
            this.mLastLevelSensed = level;
            this.mLastScaleSensed = scale;
            this.mLastTemperatureSensed = temperature;
            this.mLastVoltageSensed = voltage;
            this.mLastPluggedSensed = plugged;
            this.mLastStatusSensed = status;
            this.mLastHealthSensed = health;
        }

        return shouldPost;
    }

}
