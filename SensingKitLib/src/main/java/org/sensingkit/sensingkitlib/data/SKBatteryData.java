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

package org.sensingkit.sensingkitlib.data;

import android.os.BatteryManager;

import org.sensingkit.sensingkitlib.SKSensorModuleType;

import java.util.Locale;

import static android.os.BatteryManager.*;

public class SKBatteryData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = "SKBatteryData";

    protected final int level;
    protected final int scale;
    protected final int temperature;
    protected final int voltage;
    protected final int plugged;
    protected final int status;
    protected final int health;

    public SKBatteryData(long timestamp, int level, int scale, int temperature, int voltage, int plugged, int status, int health) {

        super(SKSensorModuleType.BATTERY, timestamp);

        this.level = level;
        this.scale = scale;
        this.temperature = temperature;
        this.voltage = voltage;
        this.plugged = plugged;
        this.status = status;
        this.health = health;
    }

    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%d,%d,%s,%s,%s", this.timestamp, this.getLevelRatio(), this.temperature, this.voltage, getPluggedString(), getBatteryStatusString(), getBatteryHealthString());
    }

    @SuppressWarnings("unused")
    public float getLevelRatio() {

        // Calculate the level: level/scale
        if (level >= 0 && scale > 0) {
            return level / (float)scale;
        }
        else {
            return 0;
        }
    }

    @SuppressWarnings("unused")
    public int getLevel() {
        return this.level;
    }

    @SuppressWarnings("unused")
    public int getScale() {
        return this.scale;
    }

    @SuppressWarnings("unused")
    public int getTemperature() {
        return this.temperature;
    }

    @SuppressWarnings("unused")
    public int getVoltage() {
        return this.voltage;
    }

    @SuppressWarnings("unused")
    public int getPlugged() {
        return this.plugged;
    }

    @SuppressWarnings("unused")
    public int getBatteryStatus() {
        return this.status;
    }

    @SuppressWarnings("unused")
    public int getBatteryHealth() {
        return this.health;
    }

    @SuppressWarnings("unused")
    public String getPluggedString() {
        return getPluggedString(this.plugged);
    }

    @SuppressWarnings("unused")
    public String getBatteryStatusString() {
        return getBatteryStatusString(this.status);
    }

    @SuppressWarnings("unused")
    public String getBatteryHealthString() {
        return getBatteryHealthString(this.health);
    }

    private static String getPluggedString(int pluggedType) {

        switch (pluggedType) {

            case BATTERY_PLUGGED_USB:
                return "usb";

            case BATTERY_PLUGGED_AC:
                return "ac";

            case BATTERY_PLUGGED_WIRELESS:
                return "wireless";

            default:
                return "unknown";
        }
    }

    private static String getBatteryStatusString(int status) {

        switch (status) {

            case BatteryManager.BATTERY_STATUS_CHARGING:
                return "charging";

            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return "discharging";

            case BatteryManager.BATTERY_STATUS_FULL:
                return "full";

            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return "not Charging";

            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                return "unknown";

            default:
                return "unsupported";
        }
    }

    private String getBatteryHealthString(int health) {

        switch (health) {

            case BatteryManager.BATTERY_HEALTH_COLD:
                return "cold";

            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "dead";

            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "good";

            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "over heat";

            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "over voltage";

            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                return "unknown";

            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "failure";

            default:
                return "unsupported";
        }
    }

}
