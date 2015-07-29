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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorModuleType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKScreenStatusData;

public class SKScreenStatus extends SKAbstractSensorModule {

    private final BroadcastReceiver mBroadcastReceiver;

    @SuppressWarnings("unused")
    private static final String TAG = "SKScreenStatus";

    public SKScreenStatus(final Context context) throws SKException {
        super(context, SKSensorModuleType.SCREEN_STATUS);

        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                // Read Status
                int status;

                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    status = SKScreenStatusData.SCREEN_OFF;
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    status = SKScreenStatusData.SCREEN_ON;
                } else {
                    status = SKScreenStatusData.SCREEN_UNKNOWN;
                }

                // Build the data object
                SKAbstractData data = new SKScreenStatusData(System.currentTimeMillis(), status);

                // Submit sensor data object
                submitSensorData(data);
            }
        };
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
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
    }

    private void registerLocalBroadcastManager() {

        // Register for SCREEN_STATUS ON and OFF notifications
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    private void unregisterLocalBroadcastManager() {
        mApplicationContext.unregisterReceiver(mBroadcastReceiver);
    }
}
