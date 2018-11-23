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
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.location.ActivityTransitionResult;

public class SKMotionActivityIntentService extends IntentService {

    @SuppressWarnings("unused")
    private static final String TAG = SKMotionActivityIntentService.class.getSimpleName();

    static final String RECOGNITION_RESULT = "result";
    static final String BROADCAST_UPDATE = "new_update";

    public SKMotionActivityIntentService() {

        // Set the label for the service's background thread
        super("SKMotionActivityIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent == null) {
            return;
        }

        // If the intent contains an update
        if (ActivityTransitionResult.hasResult(intent)) {

            // Get the update
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

            Intent i = new Intent(BROADCAST_UPDATE);
            i.putExtra(RECOGNITION_RESULT, result);
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
            manager.sendBroadcast(i);
        }

    }

}