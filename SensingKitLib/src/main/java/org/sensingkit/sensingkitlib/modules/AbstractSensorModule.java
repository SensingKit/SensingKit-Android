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

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorDataListener;
import org.sensingkit.sensingkitlib.model.data.AbstractData;

import java.util.ArrayList;

public abstract class AbstractSensorModule implements SensorModuleInterface {

    @SuppressWarnings("unused")
    private static final String TAG = "AbstractSensorModule";

    protected final Context mApplicationContext;
    protected final SensorModuleType moduleType;
    protected boolean isSensing = false;
    protected ArrayList<SKSensorDataListener> mSensorDataListeners;

    protected AbstractSensorModule(final Context context, final SensorModuleType moduleType) {

        this.mApplicationContext = context;
        this.moduleType = moduleType;

    }

    public boolean isSensing() {
        return isSensing;
    }

    public SensorModuleType getSensorType() {
        return this.moduleType;
    }

    public String getSensorName() throws SKException {
        return SensorModuleUtilities.getSensorModuleInString(moduleType);
    }

    public void registerCallback(SKSensorDataListener callback) throws SKException {

        // Init the list
        if (this.mSensorDataListeners == null) {
            this.mSensorDataListeners = new ArrayList<>();
        }

        // Register the callback
        if (this.mSensorDataListeners.contains(callback)) {
            throw new SKException(TAG, "SKSensorDataListener already registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        this.mSensorDataListeners.add(callback);
    }

    public void unregisterCallback(SKSensorDataListener callback) throws SKException {

        // Unregister the callback
        if (this.mSensorDataListeners == null || !this.mSensorDataListeners.remove(callback)) {
            throw new SKException(TAG, "SKSensorDataListener is not registered.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        // Delete the callBackList if it is empty
        if (this.mSensorDataListeners.size() == 0) {
            this.mSensorDataListeners = null;
        }
    }

    public void clearCallbacks() throws SKException {

       // Clear all callbacks
       if (this.mSensorDataListeners != null) {
           this.mSensorDataListeners.clear();
           this.mSensorDataListeners = null;
       }
    }

    protected abstract boolean shouldPostSensorData(AbstractData data);

    protected void submitSensorData(AbstractData data) {

        // If there is a significant change
        if (shouldPostSensorData(data)) {

            if (mSensorDataListeners != null) {

                // CallBack with data as parameter
                for (SKSensorDataListener callback : mSensorDataListeners) {
                    callback.onDataReceived(moduleType, data);
                }
            }
        }
    }

}
