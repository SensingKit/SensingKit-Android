/*
 * Copyright (c) 2017. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit https://www.sensingkit.org
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
import org.sensingkit.sensingkitlib.configuration.SKNotificationConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKNotificationData;

public class SKNotification extends SKAbstractSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKNotification.class.getSimpleName();

    private final BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (isSensing) {

                // Get properties
                String actionType = intent.getStringExtra("actionType");
                long postTime = intent.getLongExtra("postTime", -1);
                String packageName = intent.getStringExtra("packageName");

                // Build the data object
                SKAbstractData data = new SKNotificationData(postTime, actionType, packageName);

                // Submit sensor data object
                submitSensorData(data);
            }

        }
    };

    public SKNotification(final @NonNull Context context, final @NonNull SKNotificationConfiguration configuration) throws SKException {
        super(context, SKSensorType.NOTIFICATION, configuration);
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
        return new SKNotificationConfiguration((SKNotificationConfiguration)mConfiguration);
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

        // Register Receiver
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            IntentFilter filter = new IntentFilter(SKNotificationListenerService.NOTIFICATION_ACTION);
            mApplicationContext.registerReceiver(mNotificationReceiver, filter);
        }
    }

    @Override
    public void stopSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "stopSensing [" + mSensorType.getName() + "]");}

        // Unregister receiver
        mApplicationContext.unregisterReceiver(mNotificationReceiver);

        super.stopSensing();
    }

}
