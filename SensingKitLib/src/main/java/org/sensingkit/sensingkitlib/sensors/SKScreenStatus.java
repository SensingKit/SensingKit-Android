/*
 * Copyright (c) 2015. Kleomenis Katevas
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
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKScreenStatusConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKScreenStatusData;

public class SKScreenStatus extends SKAbstractSensor {

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            // Read Status
            int status;

            String action = intent.getAction();

            if (action == null) {
                status = SKScreenStatusData.SCREEN_UNKNOWN;
            }
            else {
                switch (action) {
                    case Intent.ACTION_SCREEN_OFF:
                        status = SKScreenStatusData.SCREEN_OFF;
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        status = SKScreenStatusData.SCREEN_ON;
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        status = SKScreenStatusData.SCREEN_UNLOCKED;
                        break;
                    default:
                        status = SKScreenStatusData.SCREEN_UNKNOWN;
                        break;
                }
            }

            // Build the data object
            SKAbstractData data = new SKScreenStatusData(System.currentTimeMillis(), status);

            // Submit sensor data object
            submitSensorData(data);
        }
    };

    @SuppressWarnings("unused")
    private static final String TAG = SKScreenStatus.class.getSimpleName();

    public SKScreenStatus(final @NonNull Context context, final @NonNull SKScreenStatusConfiguration configuration) throws SKException {
        super(context, SKSensorType.SCREEN_STATUS, configuration);
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
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKScreenStatusConfiguration((SKScreenStatusConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(final @NonNull SKAbstractData data) {

        // Always post sensor data
        return true;
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
    }

    private void registerLocalBroadcastManager() {

        // Register for SCREEN_STATUS ON, OFF and UNLOCKED (USER_PRESENT) notifications
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
    }

    private void unregisterLocalBroadcastManager() {
        mApplicationContext.unregisterReceiver(mBroadcastReceiver);
    }
}
