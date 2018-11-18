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

package org.sensingkit.sensingkitlib.data;

import android.os.BatteryManager;
import android.os.Build;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

import static android.os.BatteryManager.*;

/**
 *  An instance of SKBatteryStatusData encapsulates Battery properties
 */
public class SKBatteryStatusData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKBatteryStatusData.class.getSimpleName();

    protected final int level;
    protected final int scale;
    protected final int temperature;
    protected final int voltage;
    protected final int plugged;
    protected final int status;
    protected final int health;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param level Indicates the current battery charge level. Value ranges from 0 to maximum battery level
     *
     * @param scale Maximum battery level
     *
     * @param temperature Current battery temperature
     *
     * @param voltage Current battery voltage
     *
     * @param plugged Values are: 0 - on battery, BATTERY_PLUGGED_AC, BATTERY_PLUGGED_USB, BATTERY_PLUGGED_WIRELESS
     *
     * @param status Values are: BATTERY_STATUS_CHARGING, BATTERY_STATUS_DISCHARGING, BATTERY_STATUS_FULL,
     *               BATTERY_STATUS_NOT_CHARGING, BATTERY_STATUS_UNKNOWN
     *
     * @param health Values are: BATTERY_HEALTH_COLD, BATTERY_HEALTH_DEAD, BATTERY_HEALTH_GOOD, BATTERY_HEALTH_OVERHEAT,
     *               BATTERY_HEALTH_OVER_VOLTAGE, BATTERY_HEALTH_UNKNOWN
     */
    public SKBatteryStatusData(long timestamp, int level, int scale, int temperature, int voltage, int plugged, int status, int health) {

        super(SKSensorType.BATTERY_STATUS, timestamp);

        this.level = level;
        this.scale = scale;
        this.temperature = temperature;
        this.voltage = voltage;
        this.plugged = plugged;
        this.status = status;
        this.health = health;
    }

    /**
     * Get the csv header of the Battery sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Battery sensor.
     */
    @SuppressWarnings("unused")
    public static String csvHeader() {
        return "timeIntervalSince1970,charge,temperature,voltage,plugged,status,health";
    }

    /**
     * Get the battery properties in csv format
     *
     * @return String containing the battery properties in csv format: timeIntervalSince1970,charge,temperature,
     * voltage,
     * plugged string ("usb", "ac", "wireless" or "unknown"),
     * status string ("charging", "discharging", "full", "not charging", "unknown" or "unsupported"),
     * health string ("cold", "dead", "good", "over heat", "over voltage", "unknown", "failure" or "unsupported")
     */

     @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%d,%d,%s,%s,%s", this.timestamp, this.getLevelRatio(), this.temperature, this.voltage, getPluggedString(), getBatteryStatusString(), getBatteryHealthString());
    }

    /**
     * Get the battery charge
     *
     * @return charge (level/scale)
     */
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

    /**
     * Get the battery level
     *
     * @return level
     */
    @SuppressWarnings("unused")
    public int getLevel() {
        return this.level;
    }

    /**
     * Get the battery scale
     *
     * @return scale
     */
    @SuppressWarnings("unused")
    public int getScale() {
        return this.scale;
    }

    /**
     * Get the battery temperature
     *
     * @return temperature
     */
    @SuppressWarnings("unused")
    public int getTemperature() {
        return this.temperature;
    }

    /**
     * Get the battery voltage
     *
     * @return voltage
     */
    @SuppressWarnings("unused")
    public int getVoltage() {
        return this.voltage;
    }

    /**
     * Get the battery plugged state
     *
     * @return plugged
     */
    @SuppressWarnings("unused")
    public int getPlugged() {
        return this.plugged;
    }

    /**
     * Get the battery status
     *
     * @return status
     */
    @SuppressWarnings("unused")
    public int getBatteryStatus() {
        return this.status;
    }

    /**
     * Get the battery health
     *
     * @return health
     */
    @SuppressWarnings("unused")
    public int getBatteryHealth() {
        return this.health;
    }

    /**
     * Get the battery plugged state in string format
     *
     * @return plugged in string format
     */
    @SuppressWarnings("unused")
    public String getPluggedString() {
        return getPluggedString(this.plugged);
    }

    /**
     * Get the battery status in string format
     *
     * @return status in string format
     */
    @SuppressWarnings("unused")
    public String getBatteryStatusString() {
        return getBatteryStatusString(this.status);
    }

    /**
     * Get the battery health in string format
     *
     * @return health in string format
     */
    @SuppressWarnings("unused")
    public String getBatteryHealthString() {
        return getBatteryHealthString(this.health);
    }

    private static String getPluggedString(int pluggedType) {

        if (pluggedType == BATTERY_PLUGGED_USB) {
            return "usb";
        }
        else if (pluggedType == BATTERY_PLUGGED_AC) {
            return "ac";
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && pluggedType == BATTERY_PLUGGED_WIRELESS) {
            return "wireless";
        }
        else {
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
