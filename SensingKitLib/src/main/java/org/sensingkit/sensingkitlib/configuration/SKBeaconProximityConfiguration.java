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

import org.altbeacon.beacon.Identifier;

public class SKBeaconProximityConfiguration extends SKAbstractConfiguration {

    public final class BeaconType {

        public static final int ALTBEACON = 0;
        public static final int IBEACON = 1;
        public static final int EDDYSTONE_UID = 2;

        BeaconType() {
            throw new RuntimeException();
        }
    }

    private int beaconType;

    // Filters
    private Identifier filterId1;
    private Identifier filterId2;
    private Identifier filterId3;

    @SuppressWarnings("unused")
    public SKBeaconProximityConfiguration() {
        this(null, null, null);
    }

    @SuppressWarnings("unused")
    public SKBeaconProximityConfiguration(Identifier filterId1) {
        this(filterId1, null, null);
    }

    @SuppressWarnings("unused")
    public SKBeaconProximityConfiguration(Identifier filterId1, Identifier filterId2) {
        this(filterId1, filterId2, null);
    }

    @SuppressWarnings("unused")
    public SKBeaconProximityConfiguration(Identifier filterId1, Identifier filterId2, Identifier filterId3) {
        super();

        // Set default values
        beaconType = BeaconType.ALTBEACON;
        this.filterId1 = filterId1;
        this.filterId2 = filterId2;
        this.filterId3 = filterId3;
    }

    public SKBeaconProximityConfiguration(SKBeaconProximityConfiguration configuration) {
        super();

        // Save local configuration
        beaconType = configuration.beaconType;
        filterId1 = configuration.filterId1;
        filterId2 = configuration.filterId2;
        filterId3 = configuration.filterId3;
    }

    @SuppressWarnings("unused")
    public int getBeaconType() {
        return beaconType;
    }

    @SuppressWarnings("unused")
    public void setBeaconType(final int beaconType) {
        this.beaconType = beaconType;
    }

    @SuppressWarnings("unused")
    public final Identifier getFilterId1() {
        return filterId1;
    }

    @SuppressWarnings("unused")
    public void setFilterId1(final Identifier filterId1) {
        this.filterId1 = filterId1;
    }

    @SuppressWarnings("unused")
    public final Identifier getFilterId2() {
        return filterId2;
    }

    @SuppressWarnings("unused")
    public void setFilterId2(final Identifier filterId2) {
        this.filterId2 = filterId2;
    }

    @SuppressWarnings("unused")
    public final Identifier getFilterId3() {
        return filterId3;
    }

    @SuppressWarnings("unused")
    public void setFilterId3(final Identifier filterId3) {
        this.filterId3 = filterId3;
    }

}
