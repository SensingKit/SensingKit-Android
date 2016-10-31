/*
 * Copyright (c) 2016. Queen Mary University of London
 * Kleomenis Katevas, k.katevas@qmul.ac.uk
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

import com.google.android.gms.location.LocationRequest;

public class SKLocationConfiguration extends SKAbstractConfiguration {

    public final class Priority {

        public static final int HIGH_ACCURACY           = LocationRequest.PRIORITY_HIGH_ACCURACY;
        public static final int BALANCED_POWER_ACCURACY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        public static final int LOW_POWER               = LocationRequest.PRIORITY_LOW_POWER;
        public static final int NO_POWER                = LocationRequest.PRIORITY_NO_POWER;

        Priority() {
            throw new RuntimeException();
        }
    }

    private int priority;
    private int interval;
    private int fastestInterval;

    public SKLocationConfiguration() {
        super();

        // Set default values
        this.priority = Priority.HIGH_ACCURACY;
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

    @SuppressWarnings("unused")
    public int getPriority() {
        return priority;
    }

    @SuppressWarnings("unused")
    public void setPriority(final int priority) {
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
