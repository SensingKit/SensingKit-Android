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

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKNotificationConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKNotificationData;

public class SKNotification extends SKAbstractSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKNotification.class.getName();

    private BroadcastReceiver mNotificationReceiver;

    public SKNotification(final Context context, final SKNotificationConfiguration configuration) throws SKException {
        super(context, SKSensorType.NOTIFICATION, configuration);

        // init NotificationReceiver
        mNotificationReceiver = new BroadcastReceiver() {

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
    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKNotificationConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKConfiguration.",
                    SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Set the configuration
        super.setConfiguration(configuration);
    }

    @Override
    public SKConfiguration getConfiguration() {
        return new SKNotificationConfiguration((SKNotificationConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public void startSensing() {

        this.isSensing = true;

        // Register Receiver
        IntentFilter filter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            filter = new IntentFilter(SKNotificationListenerService.NOTIFICATION_ACTION);
        }
        this.mApplicationContext.registerReceiver(mNotificationReceiver, filter);
    }

    @Override
    public void stopSensing() {

        this.isSensing = false;

        // Unregister receiver
        this.mApplicationContext.unregisterReceiver(mNotificationReceiver);
    }

    @Override
    public void sensorDeregestered() {
        super.sensorDeregestered();

        // Release sensor
        // Not required by this sensor.
    }

}
