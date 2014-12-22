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

import org.sensingkit.sensingkitlib.model.data.DataInterface;
import org.sensingkit.sensingkitlib.modules.SensorModuleType;

import java.util.ArrayList;

public class SensorDataBuffer {
    protected SensorModuleType mSKSensorModuleType;
    protected ArrayList<DataInterface> dataBuffer;

    private final static String sNewLine = "\n";

    public SensorDataBuffer(final SensorModuleType SensorModuleType) {
        mSKSensorModuleType = SensorModuleType;
        initBuffer();
    }

    private void initBuffer() {
        dataBuffer = new ArrayList<>(100);  // TODO: Make this '100' const dynamic
    }

    public void addData(DataInterface data) {
        dataBuffer.add(data);
    }

    public SensorModuleType getMotionSensorType() {
        return mSKSensorModuleType;
    }

    public String flush() {

        // keep a ref to the current dataBuffer
        ArrayList<DataInterface> dataBuffer = this.dataBuffer;

        // create new buffer
        initBuffer();

        // return the old buffer
        return bufferInString(dataBuffer);
    }

    protected String bufferInString(ArrayList<DataInterface> dataBuffer) {

        // Calculate capacity
        int capacity = dataBuffer.size() * 60;  // TODO: Make this '60' const dynamic

        // Create StringBuilder
        StringBuilder sb = new StringBuilder(capacity);

        // Add SensorDara
        for (DataInterface sensorData : dataBuffer) {
            sb.append(sensorData.getDataInString());
            sb.append(sNewLine);
        }

        // Return
        return sb.toString();
    }
}
