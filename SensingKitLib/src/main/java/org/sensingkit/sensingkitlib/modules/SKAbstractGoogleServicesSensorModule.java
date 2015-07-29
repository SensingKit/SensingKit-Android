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

package org.sensingkit.sensingkitlib.modules;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorModuleType;


public abstract class SKAbstractGoogleServicesSensorModule extends SKAbstractSensorModule implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @SuppressWarnings("unused")
    private static final String TAG = "SKAbstractGoogleServicesSensorModule";

    protected GoogleApiClient mClient;

    protected SKAbstractGoogleServicesSensorModule(final Context context, final SKSensorModuleType sensorModuleType) throws SKException {
        super(context, sensorModuleType);


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
    public void onConnectionFailed(ConnectionResult result) {
        // At least one of the API client connect attempts failed
        // No client is connected
    }

}
