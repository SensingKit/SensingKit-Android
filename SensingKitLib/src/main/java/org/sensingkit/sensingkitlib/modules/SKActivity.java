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
import org.sensingkit.sensingkitlib.SKSensorModuleType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKActivityData;

public class SKActivity extends SKAbstractGoogleServicesSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "SKActivity";

    private ActivityRecognitionApi mActivityRecognition;
    private PendingIntent mRecognitionPendingIntent;
    private BroadcastReceiver mBroadcastReceiver;

    // Last data sensed
    private int mLastActivityTypeSensed = Integer.MAX_VALUE;
    private int mLastConfidenceSensed = Integer.MAX_VALUE;

    public SKActivity(final Context context) throws SKException {
        super(context, SKSensorModuleType.ACTIVITY);

        mClient = new GoogleApiClient.Builder(context)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mActivityRecognition = ActivityRecognition.ActivityRecognitionApi;

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                ActivityRecognitionResult result = intent.getParcelableExtra(SKActivityRecognitionIntentService.RECOGNITION_RESULT);

                // Get activity from the list of activities
                DetectedActivity activity = getActivity(result);

                // Get the type of activity
                int activityType = activity.getType();

                // Get the confidence percentage for the most probable activity
                int confidence = activity.getConfidence();

                // Build the data object
                SKAbstractData data = new SKActivityData(result.getTime(), activityType, confidence);

                // Submit sensor data object
                submitSensorData(data);
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

    @Override
    public void startSensing() {

        this.isSensing = true;

        mClient.connect();
    }

    @Override
    public void stopSensing() {

        unregisterIntent();
        unregisterLocalBroadcastManager();

        mClient.disconnect();

        this.isSensing = false;

        // Clear last sensed values
        mLastActivityTypeSensed = Integer.MAX_VALUE;
        mLastConfidenceSensed = Integer.MAX_VALUE;
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
            Intent intent = new Intent(mApplicationContext, SKActivityRecognitionIntentService.class);
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
        manager.registerReceiver(mBroadcastReceiver, new IntentFilter(SKActivityRecognitionIntentService.BROADCAST_UPDATE));
    }

    private void unregisterLocalBroadcastManager() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mApplicationContext);
        manager.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Only post when specific values changed

        int activityType = ((SKActivityData)data).getActivityType();
        int confidence = ((SKActivityData)data).getConfidence();

        // Ignore Temperature and Voltage
        boolean shouldPost = (mLastActivityTypeSensed != activityType ||
                              mLastConfidenceSensed != confidence );

        if (shouldPost) {

            this.mLastActivityTypeSensed = activityType;
            this.mLastConfidenceSensed = confidence;
        }

        return shouldPost;
    }

}
