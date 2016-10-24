/*
 * Copyright (c) 2014. Queen Mary University of London
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
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorType;


public abstract class SKAbstractGoogleServicesSensor extends SKAbstractSensor implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @SuppressWarnings("unused")
    private static final String TAG = SKAbstractGoogleServicesSensor.class.getName();

    protected GoogleApiClient mClient;

    protected SKAbstractGoogleServicesSensor(final Context context, final SKSensorType sensorType) throws SKException {
        super(context, sensorType);


    }

    protected abstract void serviceConnected();

    @Override
    public void onConnected(Bundle connectionHint) {
        serviceConnected();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // Ignore, will try to reconnect automatically
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        // At least one of the API client connect attempts failed
        // No client is connected
    }

    public static boolean isGooglePlayServicesAvailable(final Context context) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(context);
        return code == ConnectionResult.SUCCESS;
    }
}
