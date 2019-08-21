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

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.Task;


import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKMotionActivityConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKMotionActivityData;

import java.util.ArrayList;
import java.util.List;

public class SKMotionActivity extends SKAbstractSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKMotionActivity.class.getSimpleName();

    private ActivityRecognitionClient mActivityRecognitionClient;
    private PendingIntent mActivityRecognitionPendingIntent;
    private List<ActivityTransition> mRegisteredTransitions;
    private LocalBroadcastManager mLocalBroadcastManager;

    public SKMotionActivity(final @NonNull Context context, final @NonNull SKMotionActivityConfiguration configuration) throws SKException {
        super(context, SKSensorType.MOTION_ACTIVITY, configuration);
    }

    @Override
    protected void initSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "initSensing [" + mSensorType.getName() + "]");}

        mActivityRecognitionClient = ActivityRecognition.getClient(mApplicationContext);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mApplicationContext);

        Intent intent = new Intent(mApplicationContext, SKMotionActivityIntentService.class);
        mActivityRecognitionPendingIntent = PendingIntent.getService(mApplicationContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // configure sensor
        updateSensor(context, sensorType, configuration);
    }

    @Override
    protected void updateSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) {
        if (shouldDebugSensor()) {Log.i(TAG, "updateSensing [" + mSensorType.getName() + "]");}

        // Cast the configuration instance
        SKMotionActivityConfiguration motionActivityConfiguration = (SKMotionActivityConfiguration)mConfiguration;

        // update transitions
        mRegisteredTransitions = buildTransitions(motionActivityConfiguration);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int activityType = intent.getIntExtra("activityType", -1);
            int transitionType = intent.getIntExtra("transitionType", -1);
            //long timestamp = intent.getLongExtra("elapsedRealTimeNanos", -1); // TODO: fix

            // Build the data object
            SKAbstractData data = new SKMotionActivityData(System.currentTimeMillis(), activityType, transitionType);

            // Submit sensor data object
            submitSensorData(data);
        }
    };

    private @NonNull List<ActivityTransition> buildTransitions(final @NonNull SKMotionActivityConfiguration configuration) {

        // reset list
        List<ActivityTransition> transitions = new ArrayList<>();

        if (configuration.getTrackStationary()) {
            registerTrackedActivity(transitions, DetectedActivity.STILL);
        }

        if (configuration.getTrackWalking()) {
            registerTrackedActivity(transitions, DetectedActivity.WALKING);
        }

        if (configuration.getTrackRunning()) {
            registerTrackedActivity(transitions, DetectedActivity.RUNNING);
        }

        if (configuration.getTrackAutomotive()) {
            registerTrackedActivity(transitions, DetectedActivity.IN_VEHICLE);
        }

        if (configuration.getTrackCycling()) {
            registerTrackedActivity(transitions, DetectedActivity.ON_BICYCLE);
        }

        return transitions;
    }

    private void registerTrackedActivity(@NonNull List<ActivityTransition> transitions, final int activity) {
        if (shouldDebugSensor()) {Log.i(TAG, "registerTrackedActivity");}

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(activity)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(activity)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());
    }

    @Override
    public void startSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "startSensing [" + mSensorType.getName() + "]");}

        super.startSensing();

        // Register Receiver
        IntentFilter filter = new IntentFilter(SKMotionActivityIntentService.ACTIVITY_TRANSITION_ACTION);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, filter);

        assert(mRegisteredTransitions != null);
        ActivityTransitionRequest request = new ActivityTransitionRequest(mRegisteredTransitions);

        @SuppressLint("MissingPermission")
        Task<Void> task = mActivityRecognitionClient.requestActivityTransitionUpdates(request, mActivityRecognitionPendingIntent);

        task.addOnSuccessListener(
                result -> {
                    // Handle success
                }
        );

        task.addOnFailureListener(
                e -> Log.e(TAG, e.getMessage())
        );
    }

    @Override
    public void stopSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "stopSensing [" + mSensorType.getName() + "]");}

        @SuppressLint("MissingPermission")
        Task<Void> task = mActivityRecognitionClient.removeActivityTransitionUpdates(mActivityRecognitionPendingIntent);

        task.addOnSuccessListener(
                result -> mActivityRecognitionPendingIntent.cancel());

        task.addOnFailureListener(
                e -> Log.e(TAG, e.getMessage()));

        // Unregister receiver
        this.mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);

        super.stopSensing();
    }

    @Override
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKMotionActivityConfiguration((SKMotionActivityConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(final @NonNull SKAbstractData data) {

        // Always post sensor data
        return true;
    }
}
