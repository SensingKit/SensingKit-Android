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

import org.sensingkit.sensingkitlib.modules.SensorType;
import org.sensingkit.sensingkitlib.modules.SensorUtilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataWriter {

    public DataWriter() {

        // Create current folder
        String fullFolder = "sdcard" + "/Crowd/" + getFolder();
        File folder = new File(fullFolder);

        if (!folder.exists()) {
            folder.mkdir();
        }

    }

    private String getFolder() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        return dateFormat.format(new Date());
    }

    public void write(String dataPacket, final SensorType sensorType)
    {
        String filename = SensorUtilities.getSensorFilename(sensorType);

        /// TODO: Change this
        System.out.println("OK");
    }
}
