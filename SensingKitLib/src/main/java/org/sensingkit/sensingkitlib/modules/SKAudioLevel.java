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

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorModuleType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKAudioLevelData;

public class SKAudioLevel extends SKAbstractSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "SKAudioLevel";

    private static final int sampleRate = 8000;
    private static final int bufferSizeFactor = 1;

    private final int bufferSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT) * bufferSizeFactor;

    private final AudioRecord audioRecord;

    public SKAudioLevel(final Context context) throws SKException {
        super(context, SKSensorModuleType.AUDIO_LEVEL);

        // Configure the AudioRecord
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public void startSensing() {

        this.isSensing = true;

        // monitor audio
        audioRecord.startRecording();

        // init the thread that read the audio buffer
        Thread thread = new Thread(new Runnable() {
            public void run() {
                readAudioBuffer();
            }
        });

        // Set priority to max and start the thread that reads the audio level
        thread.setPriority(Thread.currentThread().getThreadGroup().getMaxPriority());
        thread.start();
    }

    @Override
    public void stopSensing() {

        audioRecord.stop();

        this.isSensing = false;
    }

    private void readAudioBuffer() {

        short[] buffer = new short[bufferSize];

        int bufferReadResult;

        do {
            // read buffer
            bufferReadResult = audioRecord.read(buffer, 0, bufferSize);

            // get max audio level
            int level = getMaxAbs(buffer);

            // Build the data object
            SKAbstractData data = new SKAudioLevelData(System.currentTimeMillis(), level);

            // Submit sensor data object
            submitSensorData(data);
        }
        while (bufferReadResult > 0 && audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING);
    }

    // Get the Max Abs of the raw data
    private int getMaxAbs(short[] raw) {

        int max = Math.abs(raw[0]);

        for (int i = 1 ; i < raw.length; i++)
        {
            max = Math.max(max, Math.abs(raw[i]));
        }

        return max;
    }
}
