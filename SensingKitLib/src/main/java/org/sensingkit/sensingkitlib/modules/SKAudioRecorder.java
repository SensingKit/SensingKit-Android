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
import android.media.MediaRecorder;
import android.os.Environment;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorModuleType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;

import java.io.IOException;

public class SKAudioRecorder extends SKAbstractSensorModule {

    @SuppressWarnings("unused")
    private static final String TAG = "SKAudioRecorder";

    private static final String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.aac";

    private final MediaRecorder recorder;

    public SKAudioRecorder(final Context context) throws SKException {
        super(context, SKSensorModuleType.AUDIO_RECORDER);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(outputFile);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // This sensor does not post data
        return false;
    }

    @Override
    public void startSensing() throws SKException {

        this.isSensing = true;

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            throw new SKException(TAG, "AudioRecorder sensor could not be prepared.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        recorder.start();
    }

    @Override
    public void stopSensing() {

        this.isSensing = false;

        recorder.stop();
        recorder.reset();
        recorder.release();
    }
}
