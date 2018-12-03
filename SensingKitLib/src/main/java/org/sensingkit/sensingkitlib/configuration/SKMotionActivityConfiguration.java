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

import org.sensingkit.sensingkitlib.SKSensorType;

public class SKMotionActivityConfiguration extends SKAbstractConfiguration {

    // Tracked Activities
    private boolean trackStationary;
    private boolean trackWalking;
    private boolean trackRunning;
    private boolean trackAutomotive;
    private boolean trackCycling;

    public SKMotionActivityConfiguration() {
        super();

        // Set default values
        this.trackStationary = true;
        this.trackWalking = true;
        this.trackRunning = true;
        this.trackAutomotive = true;
        this.trackCycling = true;
    }

    @SuppressWarnings("unused")
    public SKMotionActivityConfiguration(boolean trackStationary, boolean trackWalking, boolean trackRunning, boolean trackAutomotive, boolean trackCycling) {
        super();

        // Set default values
        this.trackStationary = trackStationary;
        this.trackWalking = trackWalking;
        this.trackRunning = trackRunning;
        this.trackAutomotive = trackAutomotive;
        this.trackCycling = trackCycling;
    }

    @SuppressWarnings("unused")
    public SKMotionActivityConfiguration(SKMotionActivityConfiguration configuration) {
        super();

        // Save local configuration
        this.trackStationary = configuration.trackStationary;
        this.trackWalking = configuration.trackWalking;
        this.trackRunning = configuration.trackRunning;
        this.trackAutomotive = configuration.trackAutomotive;
        this.trackCycling = configuration.trackCycling;
    }

    public boolean isValidForSensor(final SKSensorType sensorType) {
        return (sensorType == SKSensorType.MOTION_ACTIVITY);
    }

    @SuppressWarnings("unused")
    public boolean getTrackStationary() {
        return trackStationary;
    }

    @SuppressWarnings("unused")
    public void setTrackStationary(final boolean trackStationary) {
        this.trackStationary = trackStationary;
    }

    @SuppressWarnings("unused")
    public boolean getTrackWalking() {
        return trackWalking;
    }

    @SuppressWarnings("unused")
    public void setTrackWalking(final boolean trackWalking) {
        this.trackWalking = trackWalking;
    }

    @SuppressWarnings("unused")
    public boolean getTrackRunning() {
        return trackRunning;
    }

    @SuppressWarnings("unused")
    public void setTrackRunning(final boolean trackRunning) {
        this.trackRunning = trackRunning;
    }

    @SuppressWarnings("unused")
    public boolean getTrackAutomotive() {
        return trackAutomotive;
    }

    @SuppressWarnings("unused")
    public void setTrackAutomotive(final boolean trackAutomotive) {
        this.trackAutomotive = trackAutomotive;
    }

    @SuppressWarnings("unused")
    public boolean getTrackCycling() {
        return trackCycling;
    }

    @SuppressWarnings("unused")
    public void setTrackCycling(final boolean trackCycling) {
        this.trackCycling = trackCycling;
    }
}
