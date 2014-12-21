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

package org.sensingkit.sensingkitlib.model;

import android.content.Context;
import android.os.Handler;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKUtilities;
import org.sensingkit.sensingkitlib.modules.SensorModuleType;

import java.util.ArrayList;

public class ModelManager {

    @SuppressWarnings("unused")
    private static final String TAG = "ModelManager";

    private static ModelManager sModelManager;
    private final Context mApplicationContext;

    protected DataWriter mDataWriter;
    protected ArrayList<SensorDataBuffer> mDataBufferList;

    Handler mHandler = new Handler();

    public static ModelManager getModelManager(final Context context) throws SKException {

        if (context == null) {
            throw new SKException(TAG, "Context cannot be null.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        if (sModelManager == null) {
            sModelManager = new ModelManager(context);
        }

        return sModelManager;
    }

    private ModelManager(final Context context) throws SKException {

        mApplicationContext = context;
        mDataWriter = new DataWriter();
        mDataBufferList = new ArrayList<>(10);  // TODO: Make this '10' const dynamic

        if (!checkExternalStoragePermission()) {
            throw new SKException(TAG, "External Storage permission does not exist.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }
    }

    private boolean checkExternalStoragePermission() throws SKException {

        return SKUtilities.checkPermission(
                mApplicationContext,
                "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    private Runnable mRepeatedTask = new Runnable() {
        @Override
        public void run() {
            flush();
            mHandler.postDelayed(mRepeatedTask, 5000);//1000 * 60 * 2);  // 2 min
        }
    };

    public SensorDataBuffer createSensorDataBuffer(final SensorModuleType SensorModuleType) {

        // Create the data buffer
        SensorDataBuffer dataBuffer = new SensorDataBuffer(SensorModuleType);

        // Add it to the list
        mDataBufferList.add(dataBuffer);

        // Return it
        return dataBuffer;
    }

    public void flush() {
        for (SensorDataBuffer dataBuffer : mDataBufferList) {

            // Prepare data
            String dataPacket = dataBuffer.flush();
            SensorModuleType SensorModuleType = dataBuffer.mSKSensorModuleType;

            // Write data
            mDataWriter.write(dataPacket, SensorModuleType);
        }
    }

    public void startAutoFlushing()
    {
        mHandler.postDelayed(mRepeatedTask, 5000);//1000 * 60 * 2);  // 2 min
    }

    public void stopAutoFlushing()
    {
        mHandler.removeCallbacks(mRepeatedTask);

        // Flush remaining data
        flush();
    }

}
