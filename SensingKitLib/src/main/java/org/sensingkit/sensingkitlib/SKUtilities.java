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

package org.sensingkit.sensingkitlib;

import android.content.Context;
import android.content.pm.PackageManager;

public final class SKUtilities {

    @SuppressWarnings("unused")
    private static final String TAG = SKUtilities.class.getName();

    /**
     * Check whether a given permission has been granted
     *
     * @param context Android Context
     *
     * @param permission Android permission
     *
     * @return 1 if permission is granted; 0 if it is not
     *
     * @throws SKException
     */
    public static boolean checkPermission(Context context, String permission) throws SKException {

        if (context == null) {
            throw new SKException(TAG, "Context cannot be null.", SKExceptionErrorCode.UNKNOWN_ERROR);
        }

        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     *  Get the current time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     *  @return the current time in milliseconds
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     *  Get the current time in nanoseconds (the current value of the running Java Virtual Machine's high-resolution time source)
     *
     *  @return the current time in nanoseconds

     */
    public static long getNanoTime() {
        return System.nanoTime();
    }
}
