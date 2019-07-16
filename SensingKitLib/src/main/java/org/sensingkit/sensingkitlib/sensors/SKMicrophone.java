/*
 * Copyright (c) 2015. Kleomenis Katevas
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

import android.Manifest;
import android.content.Context;
import android.media.MediaRecorder;
import androidx.annotation.NonNull;
import android.util.Log;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKMicrophoneConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKMicrophoneData;

import java.io.IOException;

public class SKMicrophone extends SKAbstractSensor {

    @SuppressWarnings("unused")
    private static final String TAG = SKMicrophone.class.getSimpleName();

    private MediaRecorder mMediaRecorder;

    public SKMicrophone(final @NonNull Context context, final @NonNull SKMicrophoneConfiguration configuration) throws SKException {
        super(context, SKSensorType.MICROPHONE, configuration);
    }

    @Override
    protected void initSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "initSensing [" + mSensorType.getName() + "]");}

        mMediaRecorder = new MediaRecorder();

        // configure sensor
        updateSensor(context, sensorType, configuration);
    }

    @Override
    protected void updateSensor(@NonNull Context context, SKSensorType sensorType, @NonNull SKConfiguration configuration) throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "updateSensing [" + mSensorType.getName() + "]");}

        // Cast the configuration instance
        SKMicrophoneConfiguration microphoneConfiguration = (SKMicrophoneConfiguration)configuration;

        // Check if permission to file writing is granted
        if (!microphoneConfiguration.getOutputDirectory().canWrite()) {
            throw new SKException(TAG, "Microphone sensor does not have the permission to record in the following outputDirectory: " +
                    microphoneConfiguration.getRecordingPath() + ".", SKExceptionErrorCode.FILE_WRITER_PERMISSION_DENIED);
        }

        // Check if file already exists
        if (microphoneConfiguration.getRecordingFile().exists()) {
            throw new SKException(TAG, "Filename already exists (" +
                    microphoneConfiguration.getRecordingFile() + ").", SKExceptionErrorCode.FILE_ALREADY_EXISTS);
        }

        mMediaRecorder.reset();

        // Set configuration
        mMediaRecorder.setAudioSource(microphoneConfiguration.getAudioSource().getAudioSourceCode());
        mMediaRecorder.setOutputFormat(microphoneConfiguration.getOutputFormat().getOutputFormatCode());
        mMediaRecorder.setAudioEncoder(microphoneConfiguration.getAudioEncoder().getAudioEncoderCode());
        mMediaRecorder.setOutputFile(microphoneConfiguration.getRecordingPath());
        mMediaRecorder.setAudioEncodingBitRate(microphoneConfiguration.getBitrate());
        mMediaRecorder.setAudioSamplingRate(microphoneConfiguration.getSamplingRate());
        mMediaRecorder.setAudioChannels(microphoneConfiguration.getAudioChannels());

        // Prepare for recording
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
            throw new SKException(TAG, "Microphone sensor could not be prepared.", SKExceptionErrorCode.SENSOR_ERROR);
        }
    }

    @Override
    @NonNull
    public SKConfiguration getConfiguration() {
        return new SKMicrophoneConfiguration((SKMicrophoneConfiguration)mConfiguration);
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

        mMediaRecorder.start();

        // Build the data object
        SKAbstractData data = new SKMicrophoneData(System.currentTimeMillis(), "Started");

        // Submit sensor data object
        submitSensorData(data);
    }

    @Override
    public void stopSensing() throws SKException {
        if (shouldDebugSensor()) {Log.i(TAG, "stopSensing [" + mSensorType.getName() + "]");}

        // Stop recording
        mMediaRecorder.stop();

        // Build the data object
        SKAbstractData data = new SKMicrophoneData(System.currentTimeMillis(), "Stopped");

        // Submit sensor data object
        submitSensorData(data);

        super.stopSensing();
    }

    @Override
    public void sensorDeregistered() {
        super.sensorDeregistered();

        // Release sensor
        mMediaRecorder.reset();
        mMediaRecorder.release();
    }

    @Override
    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }
}
