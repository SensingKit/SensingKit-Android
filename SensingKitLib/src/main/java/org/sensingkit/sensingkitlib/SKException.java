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

import androidx.annotation.NonNull;

public class SKException extends Exception {

    private final SKExceptionErrorCode errorCode;
    private final String TAG;

    /**
     *
     * @param TAG Class TAG that this exception was invoked from.
     * @param message Error message
     * @param errorCode Error code
     */
    public SKException(final @NonNull String TAG, final @NonNull String message, final SKExceptionErrorCode errorCode) {
        super(message);
        this.TAG = TAG;
        this.errorCode = errorCode;
    }

    /**
     * Get the error code
     *
     * @return The error code
     */
    @SuppressWarnings("unused")
    public SKExceptionErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * Get the Class TAG that this exception was invoked from.
     *
     * @return Class TAG
     */
    @SuppressWarnings("unused")
    public @NonNull String getTAG() {
        return this.TAG;
    }
}
