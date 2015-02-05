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
import org.sensingkit.sensingkitlib.model.data.AbstractData;
import org.sensingkit.sensingkitlib.model.data.BatteryData;

public class Battery extends AbstractSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "Battery";

    // Last data sensed
    private int lastLevelSensed = Integer.MAX_VALUE;
    private int lastScaleSensed = Integer.MAX_VALUE;
    private int lastTemperatureSensed = Integer.MAX_VALUE;
    private int lastVoltageSensed = Integer.MAX_VALUE;
    private int lastPluggedSensed = Integer.MAX_VALUE;
    private int lastStatusSensed = Integer.MAX_VALUE;
    private int lastHealthSensed = Integer.MAX_VALUE;

    private BroadcastReceiver broadcastReceiver;

    public Battery(final Context context) throws SKException {
        super(context, SensorModuleType.BATTERY);

        broadcastReceiver = new BroadcastReceiver() {
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
                AbstractData data = new BatteryData(System.currentTimeMillis(), level, scale, temperature, voltage, plugged, status, health);

                // Submit sensor data object
                submitSensorData(data);
            }
        };
    }

    @Override
    public boolean startSensing() {

        this.isSensing = true;

        registerLocalBroadcastManager();

        return true;
    }

    @Override
    public void stopSensing() {

        unregisterLocalBroadcastManager();

        this.isSensing = false;

        // Clear last sensed values
        lastLevelSensed = Integer.MAX_VALUE;
        lastScaleSensed = Integer.MAX_VALUE;
        lastTemperatureSensed = Integer.MAX_VALUE;
        lastVoltageSensed = Integer.MAX_VALUE;
        lastPluggedSensed = Integer.MAX_VALUE;
        lastStatusSensed = Integer.MAX_VALUE;
        lastHealthSensed = Integer.MAX_VALUE;
    }

    private void registerLocalBroadcastManager() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mApplicationContext.registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterLocalBroadcastManager() {
        mApplicationContext.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected boolean shouldPostSensorData(AbstractData data) {

        // Only post when specific values changed

        int level = ((BatteryData)data).getLevel();
        int scale = ((BatteryData)data).getScale();
        int temperature = ((BatteryData)data).getTemperature();
        int voltage = ((BatteryData)data).getVoltage();
        int plugged = ((BatteryData)data).getPlugged();
        int status = ((BatteryData)data).getBatteryStatus();
        int health = ((BatteryData)data).getBatteryHealth();

        // Ignore Temperature and Voltage
        boolean shouldPost = (lastLevelSensed != level ||
                              lastScaleSensed != scale ||
                              lastPluggedSensed != plugged ||
                              lastStatusSensed != status ||
                              lastHealthSensed != health );

        if (shouldPost) {

            this.lastLevelSensed = level;
            this.lastScaleSensed = scale;
            this.lastTemperatureSensed = temperature;
            this.lastVoltageSensed = voltage;
            this.lastPluggedSensed = plugged;
            this.lastStatusSensed = status;
            this.lastHealthSensed = health;
        }

        return shouldPost;
    }

}
