/*
 * Copyright (c) 2014. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
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

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

@SuppressWarnings("WeakerAccess")
final class SKUtilities {

    @SuppressWarnings("unused")
    private static final String TAG = SKUtilities.class.getSimpleName();

    /**
     * Check whether a given permission has been granted
     *
     * @param context Android Context
     *
     * @param permission Android permission
     *
     * @return 1 if permission is granted; 0 if it is not
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean isPermissionGranted(final @NonNull String permission, final @NonNull Context context) {

        int res = ContextCompat.checkSelfPermission(context, permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    static void requestPermissions(final @NonNull Activity activity, final @NonNull String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, 0);
    }

    /**
     *  Get the current time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     *  @return the current time in milliseconds
     */
    static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     *  Get the current time in nanoseconds (the current value of the running Java Virtual Machine's high-resolution time source)
     *
     *  @return the current time in nanoseconds

     */
    static long getNanoTime() {
        return System.nanoTime();
    }

}
