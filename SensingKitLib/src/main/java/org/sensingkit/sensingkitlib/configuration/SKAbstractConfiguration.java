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

@SuppressWarnings("unused")
public abstract class SKAbstractConfiguration implements SKConfiguration {

    private boolean requestWakeLock;
    private boolean debugStatus;

    // TODO: Add Documentation
    public SKAbstractConfiguration() {

        // Set default
        this.requestWakeLock = true;
        this.debugStatus = false;
    }

    public void setRequestWakeLock(final boolean requestWakeLock) {
        this.requestWakeLock = requestWakeLock;
    }

    public boolean getRequestWakeLock() {
        return this.requestWakeLock;
    }

    public void setDebugStatus(final boolean debugSensor) {
        this.debugStatus = debugSensor;
    }

    public boolean getDebugStatus() {
        return this.debugStatus;
    }

}
