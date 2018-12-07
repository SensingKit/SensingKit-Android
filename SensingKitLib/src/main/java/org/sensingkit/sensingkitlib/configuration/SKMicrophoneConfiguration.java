/*
 * Copyright (c) 2016. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit https://www.sensingkit.org
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

package org.sensingkit.sensingkitlib.configuration;

import android.media.MediaRecorder;
import android.os.Environment;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.io.File;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SKMicrophoneConfiguration extends SKAbstractConfiguration {

    public final class AudioSource {

        public static final int CAMCORDER = MediaRecorder.AudioSource.CAMCORDER;
        public static final int MIC = MediaRecorder.AudioSource.MIC;
        public static final int VOICE_CALL = MediaRecorder.AudioSource.VOICE_CALL;
        public static final int VOICE_COMMUNICATION = MediaRecorder.AudioSource.VOICE_COMMUNICATION;
        public static final int VOICE_DOWNLINK = MediaRecorder.AudioSource.VOICE_DOWNLINK;
        public static final int VOICE_RECOGNITION = MediaRecorder.AudioSource.VOICE_RECOGNITION;
        public static final int VOICE_UPLINK = MediaRecorder.AudioSource.VOICE_UPLINK;

        AudioSource() {
            throw new RuntimeException();
        }
    }

    public final class OutputFormat {

        public static final int AAC_ADTS = MediaRecorder.OutputFormat.AAC_ADTS;
        public static final int AMR_NB = MediaRecorder.OutputFormat.AMR_NB;
        public static final int AMR_WB = MediaRecorder.OutputFormat.AMR_WB;
        public static final int MPEG_4 = MediaRecorder.OutputFormat.MPEG_4;

        OutputFormat() {
            throw new RuntimeException();
        }
    }

    public final class AudioEncoder {

        public static final int AAC = MediaRecorder.AudioEncoder.AAC;
        public static final int AAC_ELD = MediaRecorder.AudioEncoder.AAC_ELD;
        public static final int AMR_NB = MediaRecorder.AudioEncoder.AMR_NB;
        public static final int AMR_WB = MediaRecorder.AudioEncoder.AMR_WB;
        public static final int HE_AAC = MediaRecorder.AudioEncoder.HE_AAC;

        AudioEncoder() {
            throw new RuntimeException();
        }
    }

    private String filename;
    private File outputDirectory;
    private int audioSource;
    private int outputFormat;
    private int audioEncoder;
    private int bitrate;
    private int samplingRate;
    private int audioChannels;

    public SKMicrophoneConfiguration() {
        super();

        // Set default values
        this.filename = "recording";
        this.outputDirectory = Environment.getExternalStorageDirectory().getAbsoluteFile();
        this.audioSource = AudioSource.MIC;
        this.outputFormat = OutputFormat.MPEG_4;
        this.audioEncoder = AudioEncoder.AAC;
        this.bitrate = 96000;
        this.samplingRate = 44100;
        this.audioChannels = 1;
    }

    public SKMicrophoneConfiguration(SKMicrophoneConfiguration configuration) {
        super();

        // Save local configuration
        this.filename = configuration.filename;
        this.outputDirectory = configuration.outputDirectory;
        this.audioSource = configuration.audioSource;
        this.outputFormat = configuration.outputFormat;
        this.audioEncoder = configuration.audioEncoder;
        this.bitrate = configuration.bitrate;
        this.samplingRate = configuration.samplingRate;
        this.audioChannels = configuration.audioChannels;
    }

    public boolean isValidForSensor(final SKSensorType sensorType) {
        return (sensorType == SKSensorType.MICROPHONE);
    }

    @SuppressWarnings("unused")
    public String getFilename() {
        return filename;
    }

    @SuppressWarnings("unused")
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    @SuppressWarnings("unused")
    public File getOutputDirectory() {
        return outputDirectory;
    }

    @SuppressWarnings("unused")
    public void setOutputDirectory(final File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @SuppressWarnings("unused")
    public File getRecordingFile() {

        // filename.extension
        String filename = this.filename + "." + SKMicrophoneConfiguration.getRecordingExtension(this.outputFormat);

        // return the full path
        return new File(this.outputDirectory, filename);
    }

    @SuppressWarnings("unused")
    public String getRecordingPath() {

        return getRecordingFile().getPath();
    }

    @SuppressWarnings("unused")
    public int getAudioSource() {
        return audioSource;
    }

    @SuppressWarnings("unused")
    public void setAudioSource(final int audioSource) {
        this.audioSource = audioSource;
    }

    @SuppressWarnings("unused")
    public int getOutputFormat() {
        return outputFormat;
    }

    @SuppressWarnings("unused")
    public void setOutputFormat(final int outputFormat) {
        this.outputFormat = outputFormat;
    }

    @SuppressWarnings("unused")
    public int getAudioEncoder() {
        return audioEncoder;
    }

    @SuppressWarnings("unused")
    public void setAudioEncoder(final int audioEncoder) {
        this.audioEncoder = audioEncoder;
    }

    private static String getRecordingExtension(final int outputFormat) {

        switch (outputFormat) {
            case OutputFormat.AAC_ADTS:
                return "m4a";

            case OutputFormat.AMR_NB:
                return "amr";

            case OutputFormat.AMR_WB:
                return "awb";

            case OutputFormat.MPEG_4:
                return "m4a";

            default:
                throw new RuntimeException();
        }
    }

    @SuppressWarnings("unused")
    public void setBitrate(final int bitrate) {
        this.bitrate = bitrate;
    }

    @SuppressWarnings("unused")
    public int getBitrate() {
        return bitrate;
    }

    @SuppressWarnings("unused")
    public void setSamplingRate(final int samplingRate) {
        this.samplingRate = samplingRate;
    }

    @SuppressWarnings("unused")
    public int getSamplingRate() {
        return samplingRate;
    }

    @SuppressWarnings("unused")
    public void setAudioChannels(final int audioChannels) {
        this.audioChannels = audioChannels;
    }

    @SuppressWarnings("unused")
    public int getAudioChannels() {
        return audioChannels;
    }

}
