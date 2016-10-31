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

package org.sensingkit.sensingkitlib.sensors;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

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
    private static final String TAG = SKMicrophone.class.getName();

    private MediaRecorder recorder;

    public SKMicrophone(final Context context, final SKMicrophoneConfiguration configuration) throws SKException {
        super(context, SKSensorType.MICROPHONE, configuration);
    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKMicrophoneConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKMicrophone.",
                    SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Set the configuration
        super.setConfiguration(configuration);

        // Cast the configuration instance
        SKMicrophoneConfiguration microphoneConfiguration = (SKMicrophoneConfiguration)configuration;

        // Init MediaRecorder
        if (recorder == null) {
            recorder = new MediaRecorder();
        }
        else {
            // Reset if the sensor is already prepared for recording
            recorder.reset();
        }

        // Set configuration
        recorder.setAudioSource(microphoneConfiguration.getAudioSource());
        recorder.setOutputFormat(microphoneConfiguration.getOutputFormat());
        recorder.setAudioEncoder(microphoneConfiguration.getAudioEncoder());
        recorder.setOutputFile(microphoneConfiguration.getRecordingPath());
        recorder.setAudioEncodingBitRate(microphoneConfiguration.getBitrate());
        recorder.setAudioSamplingRate(microphoneConfiguration.getSamplingRate());
        recorder.setAudioChannels(microphoneConfiguration.getAudioChannels());

        // Prepare for recording
        try {
            recorder.prepare();
        } catch (IOException e) {
            throw new SKException(TAG, "Microphone sensor could not be prepared.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public SKConfiguration getConfiguration() {
        return new SKMicrophoneConfiguration((SKMicrophoneConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public void startSensing() throws SKException {

        this.isSensing = true;

        recorder.start();

        // Build the data object
        SKAbstractData data = new SKMicrophoneData(System.currentTimeMillis(), "Started");

        // Submit sensor data object
        submitSensorData(data);
    }

    @Override
    public void stopSensing() {

        this.isSensing = false;

        recorder.stop();
        recorder.reset();
        recorder.release();

        // Build the data object
        SKAbstractData data = new SKMicrophoneData(System.currentTimeMillis(), "Stopped");

        // Submit sensor data object
        submitSensorData(data);
    }
}
