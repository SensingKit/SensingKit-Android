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

import android.app.IntentService;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;

public class SKMotionActivityIntentService extends IntentService {

    public static final String ACTIVITY_TRANSITION_ACTION = "org.sensingkit.SensingKit-Android.SKMotionActivityIntentService";

    @SuppressWarnings("unused")
    private static final String TAG = SKMotionActivityIntentService.class.getSimpleName();

    private final LocalBroadcastManager mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

    public SKMotionActivityIntentService() {

        // Set the label for the service's background thread
        super("SKMotionActivityIntentService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {

        if (intent == null) {
            return;
        }

        // If the intent contains an update
        if (ActivityTransitionResult.hasResult(intent)) {

            // Get the update
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

            if (result == null) {
                return;
            }

            for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                // chronological sequence of events.

                Intent i = new Intent(ACTIVITY_TRANSITION_ACTION);
                i.putExtra("activityType", event.getActivityType());
                i.putExtra("transitionType", event.getTransitionType());
                i.putExtra("elapsedRealTimeNanos", event.getElapsedRealTimeNanos());

                // send the broadcast to the SKMotionActivity.BroadcastReceiver
                mLocalBroadcastManager.sendBroadcast(i);
            }
        }
    }
}