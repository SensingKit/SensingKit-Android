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
import androidx.annotation.NonNull;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.io.File;

@SuppressWarnings({"unused"})
public class SKMicrophoneConfiguration extends SKAbstractConfiguration {

    public enum SKAudioSource {

        /**
         * TODO
         */
        CAMCORDER("Camcorder", MediaRecorder.AudioSource.CAMCORDER),

        /**
         * TODO
         */
        MIC("Mic", MediaRecorder.AudioSource.MIC),

        /**
         * TODO
         */
        VOICE_CALL("Voice Call", MediaRecorder.AudioSource.VOICE_CALL),

        /**
         * TODO
         */
        VOICE_COMMUNICATION("Voice Communication", MediaRecorder.AudioSource.VOICE_COMMUNICATION),

        /**
         * TODO
         */
        VOICE_DOWNLINK("Voice Downlink", MediaRecorder.AudioSource.VOICE_DOWNLINK),

        /**
         * TODO
         */
        VOICE_RECOGNITION("Voice Recognition", MediaRecorder.AudioSource.VOICE_RECOGNITION),

        /**
         * TODO
         */
        VOICE_UPLINK("Voice Uplink", MediaRecorder.AudioSource.VOICE_UPLINK);

        private final String name;
        private final int audioSourceCode;

        SKAudioSource(final @NonNull String name, final int audioSourceCode) {
            this.name = name;
            this.audioSourceCode = audioSourceCode;
        }

        @SuppressWarnings("unused")
        public @NonNull String getName() {
            return this.name;
        }

        public int getAudioSourceCode() {
            return audioSourceCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getName();
        }
    }

    public enum SKOutputFormat {

        /**
         * TODO
         */
        AAC_ADTS("AAC ADTS", MediaRecorder.OutputFormat.AAC_ADTS, "m4a"),

        /**
         * TODO
         */
        AMR_NB("AMR NB", MediaRecorder.OutputFormat.AMR_NB, "amr"),

        /**
         * TODO
         */
        AMR_WB("AMR WB", MediaRecorder.OutputFormat.AMR_WB, "awb"),

        /**
         * TODO
         */
        MPEG_4("MPEG 4", MediaRecorder.OutputFormat.MPEG_4, "m4a");

        private final String name;
        private final int outputFormatCode;
        private final @NonNull String extension;

        SKOutputFormat(final @NonNull String name, final int outputFormatCode, final @NonNull String extension) {
            this.name = name;
            this.outputFormatCode = outputFormatCode;
            this.extension = extension;
        }

        @SuppressWarnings("unused")
        public @NonNull String getName() {
            return this.name;
        }

        public int getOutputFormatCode() {
            return outputFormatCode;
        }

        @NonNull
        public String getExtension() {
            return extension;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getName();
        }
    }

    public enum SKAudioEncoder {

        /**
         * TODO
         */
        AAC("AAC", MediaRecorder.AudioEncoder.AAC),

        /**
         * TODO
         */
        AAC_ELD("AAC ELD", MediaRecorder.AudioEncoder.AAC_ELD),

        /**
         * TODO
         */
        AMR_NB("AMR NB", MediaRecorder.AudioEncoder.AMR_NB),

        /**
         * TODO
         */
        AMR_WB("AMW WB", MediaRecorder.AudioEncoder.AMR_WB),

        /**
         * TODO
         */
        HE_AAC("HE AAC", MediaRecorder.AudioEncoder.HE_AAC);

        private final String name;
        private final int audioEncoderCode;

        SKAudioEncoder(final @NonNull String name, final int audioEncoderCode) {
            this.name = name;
            this.audioEncoderCode = audioEncoderCode;
        }

        @SuppressWarnings("unused")
        public @NonNull String getName() {
            return this.name;
        }

        public int getAudioEncoderCode() {
            return audioEncoderCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getName();
        }
    }

    private String filename;
    private File outputDirectory;
    private SKAudioSource audioSource;
    private SKOutputFormat outputFormat;
    private SKAudioEncoder audioEncoder;
    private int bitrate;
    private int samplingRate;
    private int audioChannels;

    public SKMicrophoneConfiguration() {
        super();

        // Set default values
        this.filename = "recording";
        this.outputDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        this.audioSource = SKAudioSource.MIC;
        this.outputFormat = SKOutputFormat.MPEG_4;
        this.audioEncoder = SKAudioEncoder.AAC;
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
        String filename = this.filename + "." + outputFormat.getExtension();

        // return the full path
        return new File(this.outputDirectory, filename);
    }

    @SuppressWarnings("unused")
    public String getRecordingPath() {

        return getRecordingFile().getPath();
    }

    @SuppressWarnings("unused")
    public SKAudioSource getAudioSource() {
        return audioSource;
    }

    @SuppressWarnings("unused")
    public void setAudioSource(final SKAudioSource audioSource) {
        this.audioSource = audioSource;
    }

    @SuppressWarnings("unused")
    public SKOutputFormat getOutputFormat() {
        return outputFormat;
    }

    @SuppressWarnings("unused")
    public void setOutputFormat(final SKOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    @SuppressWarnings("unused")
    public SKAudioEncoder getAudioEncoder() {
        return audioEncoder;
    }

    @SuppressWarnings("unused")
    public void setAudioEncoder(final SKAudioEncoder audioEncoder) {
        this.audioEncoder = audioEncoder;
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
