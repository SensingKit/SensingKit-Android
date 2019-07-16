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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import android.util.Log;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKBatteryStatusConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKBatteryStatusData;

@SuppressWarnings("unused")
public class SKBatteryStatus extends SKAbstractSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKBatteryStatus.class.getSimpleName();

    // Last data sensed
    private int mLastLevelSensed = Integer.MAX_VALUE;
    private int mLastScaleSensed = Integer.MAX_VALUE;
    private int mLastTemperatureSensed = Integer.MAX_VALUE;
    private int mLastVoltageSensed = Integer.MAX_VALUE;
    private SKBatteryStatusData.SKBatteryPlugged mLastPluggedSensed = null;
    private SKBatteryStatusData.SKBatteryStatus mLastStatusSensed = null;
    private SKBatteryStatusData.SKBatteryHealth mLastHealthSensed = null;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
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
            SKAbstractData data = new SKBatteryStatusData(System.currentTimeMillis(), level, scale, temperature, voltage, plugged, status, health);

            // Submit sensor data object
            submitSensorData(data);
        }
    };

    public SKBatteryStatus(final Context context, SKBatteryStatusConfiguration configuration) throws SKException {
        super(context, SKSensorType.BATTERY_STATUS, configuration);
    }

    @Override
    protected void initSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "initSensing [" + mSensorType.getName() + "]");}
        // Not required for this type of sensor

        // configure sensor
        updateSensor(context, sensorType, configuration);
    }

    @Override
    protected void updateSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "updateSensing [" + mSensorType.getName() + "]");}
        // Not required for this type of sensor
    }

    @Override
    public void startSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "startSensing [" + mSensorType.getName() + "]");}

        super.startSensing();

        registerLocalBroadcastManager();
    }

    @Override
    public void stopSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "stopSensing [" + mSensorType.getName() + "]");}

        unregisterLocalBroadcastManager();

        super.stopSensing();

        // Clear last sensed values
        mLastLevelSensed = Integer.MAX_VALUE;
        mLastScaleSensed = Integer.MAX_VALUE;
        mLastTemperatureSensed = Integer.MAX_VALUE;
        mLastVoltageSensed = Integer.MAX_VALUE;
        mLastPluggedSensed = null;
        mLastStatusSensed = null;
        mLastHealthSensed = null;
    }

    private void registerLocalBroadcastManager() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mApplicationContext.registerReceiver(mBroadcastReceiver, filter);
    }

    private void unregisterLocalBroadcastManager() {
        mApplicationContext.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKBatteryStatusConfiguration((SKBatteryStatusConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(final @NonNull SKAbstractData data) {

        // Only post when specific values changed

        // Cast into SKBatteryStatusData
        SKBatteryStatusData batteryData = (SKBatteryStatusData)data;

        // Read values
        int level = batteryData.getLevel();
        int scale = batteryData.getScale();
        int temperature = batteryData.getTemperature();
        int voltage = batteryData.getVoltage();
        SKBatteryStatusData.SKBatteryPlugged plugged = batteryData.getBatteryPlugged();
        SKBatteryStatusData.SKBatteryStatus status = batteryData.getBatteryStatus();
        SKBatteryStatusData.SKBatteryHealth health = batteryData.getBatteryHealth();

        // Ignore Temperature and Voltage
        boolean shouldPost = (mLastLevelSensed != level ||
                              mLastScaleSensed != scale ||
                              mLastPluggedSensed != plugged ||
                              mLastStatusSensed != status ||
                              mLastHealthSensed != health );

        if (shouldPost) {

            // Save last sensed values
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
