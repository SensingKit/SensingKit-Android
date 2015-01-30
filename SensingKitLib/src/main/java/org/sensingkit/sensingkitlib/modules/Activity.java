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

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;


import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorDataListener;
import org.sensingkit.sensingkitlib.model.data.AbstractData;
import org.sensingkit.sensingkitlib.model.data.ActivityData;

public class Activity extends AbstractGoogleServicesSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "Activity";

    private ActivityRecognitionApi mActivityRecognition;
    private PendingIntent mRecognitionPendingIntent;
    private BroadcastReceiver broadcastReceiver;

    // Last data sensed
    private int lastActivityTypeSensed = Integer.MAX_VALUE;
    private int lastConfidenceSensed = Integer.MAX_VALUE;

    public Activity(final Context context) throws SKException {
        super(context, SensorModuleType.ACTIVITY);

        mClient = new GoogleApiClient.Builder(context)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mActivityRecognition = ActivityRecognition.ActivityRecognitionApi;

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ActivityRecognitionResult result = intent.getParcelableExtra(ActivityRecognitionIntentService.RECOGNITION_RESULT);

                // Get activity from the list of activities
                DetectedActivity activity = getActivity(result);

                // Get the type of activity
                int activityType = activity.getType();

                // Get the confidence percentage for the most probable activity
                int confidence = activity.getConfidence();

                // Build the data object
                AbstractData data = new ActivityData(result.getTime(), activityType, confidence);

                // If there is a significant change
                if (shouldPostSensorData(data)) {

                    if (callbackList != null) {

                        // CallBack with data as parameter
                        for (SKSensorDataListener callback : callbackList) {
                            callback.onDataReceived(moduleType, data);
                        }
                    }
                }
            }
        };
    }

    private DetectedActivity getActivity(ActivityRecognitionResult result) {

        // Get the most probable activity from the list of activities in the result
        DetectedActivity mostProbableActivity = result.getMostProbableActivity();

        // If the activity is ON_FOOT, choose between WALKING or RUNNING
        if (mostProbableActivity.getType() == DetectedActivity.ON_FOOT) {

            // Iterate through all possible activities. The activities are sorted by most probable activity first.
            for (DetectedActivity activity : result.getProbableActivities()) {

                if (activity.getType() == DetectedActivity.WALKING || activity.getType() == DetectedActivity.RUNNING) {
                    return activity;
                }
            }

            // It is ON_FOOT, but not sure if it is WALKING or RUNNING
            Log.i(TAG, "Activity ON_FOOT, but not sure if it is WALKING or RUNNING.");
            return mostProbableActivity;
        }
        else
        {
            return mostProbableActivity;
        }

    }

    public boolean startSensing() {

        this.isSensing = true;

        mClient.connect();

        return true;
    }

    public void stopSensing() {

        unregisterIntent();
        unregisterLocalBroadcastManager();

        mClient.disconnect();

        this.isSensing = false;

        // Clear last sensed values
        lastActivityTypeSensed = Integer.MAX_VALUE;
        lastConfidenceSensed = Integer.MAX_VALUE;
    }

    @Override
    protected void serviceConnected()
    {
        Log.i(TAG, "GoogleApiClient Connected!");

        registerLocalBroadcastManager();
        registerIntent();
    }

    private void registerIntent() {

        if (mRecognitionPendingIntent == null) {
            Intent intent = new Intent(mApplicationContext, ActivityRecognitionIntentService.class);
            mRecognitionPendingIntent = PendingIntent.getService(mApplicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        mActivityRecognition.requestActivityUpdates(mClient, 0, mRecognitionPendingIntent);
    }

    private void unregisterIntent() {

        mActivityRecognition.removeActivityUpdates(mClient, mRecognitionPendingIntent);
        mRecognitionPendingIntent.cancel();
        mRecognitionPendingIntent = null;
    }

    private void registerLocalBroadcastManager() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mApplicationContext);
        manager.registerReceiver(broadcastReceiver, new IntentFilter(ActivityRecognitionIntentService.BROADCAST_UPDATE));
    }

    private void unregisterLocalBroadcastManager() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mApplicationContext);
        manager.unregisterReceiver(broadcastReceiver);
    }

    protected boolean shouldPostSensorData(AbstractData data) {

        // Only post when specific values changed

        int activityType = ((ActivityData)data).getActivityType();
        int confidence = ((ActivityData)data).getConfidence();

        // Ignore Temperature and Voltage
        boolean shouldPost = (lastActivityTypeSensed != activityType ||
                              lastConfidenceSensed != confidence );

        if (shouldPost) {

            this.lastActivityTypeSensed = activityType;
            this.lastConfidenceSensed = confidence;
        }

        return shouldPost;
    }

}
