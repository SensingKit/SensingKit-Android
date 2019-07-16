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

import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SKNotificationListenerService extends NotificationListenerService {

    public static final String NOTIFICATION_ACTION = "org.sensingkit.SensingKit-Android.SKNotificationListenerService";

    @SuppressWarnings("unused")
    private static final String TAG = SKNotificationListenerService.class.getSimpleName();

    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Connected!");
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        sendNotification("posted", sbn);
    }

    @Override
    public void onNotificationRemoved(final StatusBarNotification sbn) {
        sendNotification("removed", sbn);
    }

    private void sendNotification(final @NonNull String actionType, final @NonNull StatusBarNotification sbn) {

        // Build intent
        Intent i = new Intent(NOTIFICATION_ACTION);
        i.putExtra("actionType", actionType);
        i.putExtra("postTime", sbn.getPostTime());
        i.putExtra("packageName", sbn.getPackageName());

        // send the broadcast to the SKNotification.NotificationServiceReceiver
        this.sendBroadcast(i);
    }
}
