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

import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationRequest;

import org.sensingkit.sensingkitlib.SKSensorType;

public class SKLocationConfiguration extends SKAbstractConfiguration {

    @SuppressWarnings({"WeakerAccess", "unused"})
    public enum SKPriority {

        /**
         * TODO
         */
        HIGH_ACCURACY("High Accuracy", LocationRequest.PRIORITY_HIGH_ACCURACY),

        /**
         * TODO
         */
        BALANCED_POWER_ACCURACY("Balanced Power Accuracy", LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY),

        /**
         * TODO
         */
        LOW_POWER("Low Power", LocationRequest.PRIORITY_LOW_POWER),

        /**
         * TODO
         */
        NO_POWER("No Power", LocationRequest.PRIORITY_NO_POWER);

        private final int priorityCode;
        private final @NonNull String name;

        SKPriority(final @NonNull String name, final int priorityCode) {
            this.name = name;
            this.priorityCode = priorityCode;
        }

        @SuppressWarnings("unused")
        public @NonNull String getName() {
            return this.name;
        }

        @SuppressWarnings("unused")
        public int getPriorityCode() {
            return this.priorityCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getName();
        }
    }

    private SKPriority priority;
    private int interval;
    private int fastestInterval;

    public SKLocationConfiguration() {
        super();

        // Set default values
        this.priority = SKPriority.HIGH_ACCURACY;
        this.interval = 1000;
        this.fastestInterval = 500;
    }

    public SKLocationConfiguration(SKLocationConfiguration configuration) {
        super();

        // Save local configuration
        this.priority = configuration.priority;
        this.interval = configuration.interval;
        this.fastestInterval = configuration.fastestInterval;
    }

    public boolean isValidForSensor(final SKSensorType sensorType) {
        return (sensorType == SKSensorType.LOCATION);
    }

    @SuppressWarnings("unused")
    public SKPriority getPriority() {
        return priority;
    }

    @SuppressWarnings("unused")
    public void setPriority(final SKPriority priority) {
        this.priority = priority;
    }

    @SuppressWarnings("unused")
    public int getInterval() {
        return interval;
    }

    @SuppressWarnings("unused")
    public void setInterval(final int interval) {
        this.interval = interval;
    }

    @SuppressWarnings("unused")
    public int getFastestInterval() {
        return fastestInterval;
    }

    @SuppressWarnings("unused")
    public void setFastestInterval(final int fastestInterval) {
        this.fastestInterval = fastestInterval;
    }

}
