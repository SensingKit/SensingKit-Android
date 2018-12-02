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
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
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

    public SKMotionActivity(final @NonNull Context context, final @NonNull SKMotionActivityConfiguration configuration) throws SKException {
        super(context, SKSensorType.MOTION_ACTIVITY, configuration);

        mActivityRecognitionClient = ActivityRecognition.getClient(mApplicationContext);
        mRegisteredTransitions = buildTransitions(configuration);

        registerLocalBroadcastManager();

        Intent intent = new Intent(mApplicationContext, SKMotionActivityIntentService.class);
        mActivityRecognitionPendingIntent = PendingIntent.getService(mApplicationContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (ActivityTransitionResult.hasResult(intent)) {

                ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

                if (result == null) { return; }

                for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                    // chronological sequence of events.

                    int activityType = event.getActivityType();
                    int transitionType = event.getTransitionType();
                    long timestamp = event.getElapsedRealTimeNanos(); // TODO: fix


                    // Build the data object
                    SKAbstractData data = new SKMotionActivityData(timestamp, activityType, transitionType);

                    // Submit sensor data object
                    submitSensorData(data);
                }
            }
        }
    };

    private @NonNull List<ActivityTransition> buildTransitions(final @NonNull SKMotionActivityConfiguration configuration) {

        // reset list
        List<ActivityTransition> transitions = new ArrayList<>();

        if (configuration.getTrackStationary()) {
            registerTrackedActivity(DetectedActivity.STILL);
        }

        if (configuration.getTrackWalking()) {
            registerTrackedActivity(DetectedActivity.WALKING);
        }

        if (configuration.getTrackRunning()) {
            registerTrackedActivity(DetectedActivity.RUNNING);
        }

        if (configuration.getTrackAutomotive()) {
            registerTrackedActivity(DetectedActivity.IN_VEHICLE);
        }

        if (configuration.getTrackCycling()) {
            registerTrackedActivity(DetectedActivity.ON_BICYCLE);
        }

        return transitions;
    }

    private void registerTrackedActivity(final int activity) {

        mRegisteredTransitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(activity)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        mRegisteredTransitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(activity)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());
    }

    @Override
    public void startSensing() throws SKException {

        super.startSensing();

        ActivityTransitionRequest request = new ActivityTransitionRequest(mRegisteredTransitions);

        @SuppressLint("MissingPermission")
        Task<Void> task = mActivityRecognitionClient.requestActivityTransitionUpdates(request, mActivityRecognitionPendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        // Handle success
                    }
                }
        );

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
        );
    }

    @Override
    public void stopSensing() throws SKException {

        @SuppressLint("MissingPermission")
        Task<Void> task = mActivityRecognitionClient.removeActivityTransitionUpdates(mActivityRecognitionPendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        mActivityRecognitionPendingIntent.cancel();
                    }
                });

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                });

        unregisterLocalBroadcastManager();

        super.stopSensing();
    }

    private void registerLocalBroadcastManager() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mApplicationContext);
        manager.registerReceiver(mBroadcastReceiver, new IntentFilter(SKMotionActivityIntentService.BROADCAST_UPDATE));
    }

    private void unregisterLocalBroadcastManager() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(mApplicationContext);
        manager.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void setConfiguration(final @NonNull SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKMotionActivityConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKMotionActivity.",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        // Set the configuration
        super.setConfiguration(configuration);

        // Cast the configuration instance
        SKMotionActivityConfiguration motionActivityConfiguration = (SKMotionActivityConfiguration)mConfiguration;

        // update transitions
        mRegisteredTransitions = buildTransitions(motionActivityConfiguration);
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
