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

import android.annotation.TargetApi;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;
import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 * An instance of SKBatteryStatusData encapsulates Battery properties
 */
@SuppressWarnings("WeakerAccess")
public class SKBatteryStatusData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKBatteryStatusData.class.getSimpleName();

    public enum SKBatteryPlugged {

        /**
         * TODO
         */
        UNPLUGGED("Unplugged", 0),

        /**
         * TODO
         */
        USB("USB", BatteryManager.BATTERY_PLUGGED_USB),

        /**
         * TODO
         */
        AC("AC", BatteryManager.BATTERY_PLUGGED_AC),

        /**
         * TODO
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        WIRELESS("Wireless", BatteryManager.BATTERY_PLUGGED_WIRELESS);

        private final @NonNull String batteryPlugged;
        private final int batteryPluggedCode;

        SKBatteryPlugged(final @NonNull String batteryPlugged, final int batteryPluggedCode) {
            this.batteryPlugged = batteryPlugged;
            this.batteryPluggedCode = batteryPluggedCode;
        }

        @SuppressWarnings("WeakerAccess")
        public static SKBatteryPlugged valueOf(final int batteryPluggedCode) {

            switch (batteryPluggedCode) {
                case 0:
                    return UNPLUGGED;

                case BatteryManager.BATTERY_PLUGGED_USB:
                    return USB;

                case BatteryManager.BATTERY_PLUGGED_AC:
                    return AC;

                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    return WIRELESS;

                default:
                    throw new RuntimeException("Unsupported SKBatteryPlugged with code: " + batteryPluggedCode);
            }
        }

        @SuppressWarnings("unused")
        public @NonNull String getBatteryPlugged() {
            return this.batteryPlugged;
        }

        @SuppressWarnings("unused")
        public int getBatteryPluggedCode() {
            return this.batteryPluggedCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getBatteryPlugged();
        }
    }

    public enum SKBatteryStatus {

        /**
         * TODO
         */
        CHARGING("Charging", BatteryManager.BATTERY_STATUS_CHARGING),

        /**
         * TODO
         */
        DISCHARGING("Discharging", BatteryManager.BATTERY_STATUS_DISCHARGING),

        /**
         * TODO
         */
        FULL("Full", BatteryManager.BATTERY_STATUS_FULL),

        /**
         * TODO
         */
        NOT_CHARGING("Not Charging", BatteryManager.BATTERY_STATUS_NOT_CHARGING),

        /**
         * TODO
         */
        UNKNOWN("Unknown", BatteryManager.BATTERY_STATUS_UNKNOWN);

        private final @NonNull String batteryStatus;
        private final int batteryStatusCode;

        SKBatteryStatus(final @NonNull String batteryStatus, final int batteryStatusCode) {
            this.batteryStatus = batteryStatus;
            this.batteryStatusCode = batteryStatusCode;
        }

        @SuppressWarnings("WeakerAccess")
        public static SKBatteryStatus valueOf(final int batteryStatusCode) {

            switch (batteryStatusCode) {

                case BatteryManager.BATTERY_STATUS_CHARGING:
                    return CHARGING;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    return DISCHARGING;

                case BatteryManager.BATTERY_STATUS_FULL:
                    return FULL;

                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    return NOT_CHARGING;

                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    return UNKNOWN;

                default:
                    throw new RuntimeException("Unsupported SKBatteryStatus with code: " + batteryStatusCode);
            }
        }

        @SuppressWarnings("unused")
        public @NonNull String getBatteryStatus() {
            return this.batteryStatus;
        }

        @SuppressWarnings("unused")
        public int getBatteryStatusCode() {
            return this.batteryStatusCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getBatteryStatus();
        }
    }

    public enum SKBatteryHealth {

        /**
         * TODO
         */
        COLD("Cold", BatteryManager.BATTERY_HEALTH_COLD),

        /**
         * TODO
         */
        DEAD("Dead", BatteryManager.BATTERY_HEALTH_DEAD),

        /**
         * TODO
         */
        GOOD("Good", BatteryManager.BATTERY_HEALTH_GOOD),

        /**
         * TODO
         */
        OVERHEAT("Overheat", BatteryManager.BATTERY_HEALTH_OVERHEAT),

        /**
         * TODO
         */
        OVER_VOLTAGE("Over Voltage", BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE),

        /**
         * TODO
         */
        UNKNOWN("Unknown", BatteryManager.BATTERY_HEALTH_UNKNOWN),

        /**
         * TODO
         */
        UNSPECIFIED_FAILURE("Unspecified Failure", BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE);

        private final @NonNull String batteryHealth;
        private final int batteryHealthCode;

        SKBatteryHealth(final @NonNull String batteryHealth, final int batteryHealthCode) {
            this.batteryHealth = batteryHealth;
            this.batteryHealthCode = batteryHealthCode;
        }

        @SuppressWarnings("WeakerAccess")
        public static SKBatteryHealth valueOf(final int batteryHealthCode) {

            switch (batteryHealthCode) {

                case BatteryManager.BATTERY_HEALTH_COLD:
                    return COLD;

                case BatteryManager.BATTERY_HEALTH_DEAD:
                    return DEAD;

                case BatteryManager.BATTERY_HEALTH_GOOD:
                    return GOOD;

                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    return OVERHEAT;

                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    return OVER_VOLTAGE;

                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    return UNKNOWN;

                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    return UNSPECIFIED_FAILURE;

                default:
                    throw new RuntimeException("Unsupported SKBatteryHealth with code: " + batteryHealthCode);
            }
        }

        @SuppressWarnings("unused")
        public @NonNull String getBatteryHealth() {
            return this.batteryHealth;
        }

        @SuppressWarnings("unused")
        public int getBatteryHealthCode() {
            return this.batteryHealthCode;
        }

        @NonNull
        @Override
        public String toString() {
            return this.getBatteryHealth();
        }
    }

    private final int level;
    private final int scale;
    private final int temperature;
    private final int voltage;
    private final SKBatteryPlugged plugged;
    private final SKBatteryStatus status;
    private final SKBatteryHealth health;

    /**
     * Initialize the instance
     *
     * @param timestamp   Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     * @param level       Indicates the current battery charge level. Value ranges from 0 to maximum battery level
     * @param scale       Maximum battery level
     * @param temperature Current battery temperature
     * @param voltage     Current battery voltage
     * @param pluggedCode Values are: 0 - on battery, BATTERY_PLUGGED_AC, BATTERY_PLUGGED_USB, BATTERY_PLUGGED_WIRELESS
     * @param statusCode  Values are: BATTERY_STATUS_CHARGING, BATTERY_STATUS_DISCHARGING, BATTERY_STATUS_FULL,
     *                    BATTERY_STATUS_NOT_CHARGING, BATTERY_STATUS_UNKNOWN
     * @param healthCode  Values are: BATTERY_HEALTH_COLD, BATTERY_HEALTH_DEAD, BATTERY_HEALTH_GOOD, BATTERY_HEALTH_OVERHEAT,
     *                    BATTERY_HEALTH_OVER_VOLTAGE, BATTERY_HEALTH_UNKNOWN
     */
    public SKBatteryStatusData(final long timestamp, final int level, final int scale, final int temperature, final int voltage, final int pluggedCode, final int statusCode, final int healthCode) {
        super(SKSensorType.BATTERY_STATUS, timestamp);

        this.level = level;
        this.scale = scale;
        this.temperature = temperature;
        this.voltage = voltage;
        this.plugged = SKBatteryPlugged.valueOf(pluggedCode);
        this.status = SKBatteryStatus.valueOf(statusCode);
        this.health = SKBatteryHealth.valueOf(healthCode);
    }

    /**
     * Get the csv header of the Battery sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Battery sensor.
     */
    @SuppressWarnings({"unused", "SameReturnValue"})
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
    @NonNull
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%f,%d,%d,%s,%s,%s", this.timestamp, this.getLevelRatio(), this.temperature, this.voltage,
                this.plugged.getBatteryPlugged(), this.status.getBatteryStatus(), this.health.getBatteryHealth());
    }

    /**
     * Get the battery properties in JSONObject format
     *
     * @return JSONObject containing the battery properties in JSONObject format:
     * sensor type, sensor type in string, timeIntervalSince1970,charge,temperature,voltage,
     * plugged string ("usb", "ac", "wireless" or "unknown"),
     * status string ("charging", "discharging", "full", "not charging", "unknown" or "unsupported"),
     * health string ("cold", "dead", "good", "over heat", "over voltage", "unknown", "failure" or "unsupported")
     */
    @Override
    @NonNull
    public JSONObject getDataInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sensorType", this.getSensorType());
            jsonObject.put("sensorTypeString", this.getSensorType().toString());
            jsonObject.put("timestamp", this.timestamp);

            JSONObject subJsonObject = new JSONObject();
            subJsonObject.put("charge", this.getLevelRatio());
            subJsonObject.put("temperature", this.temperature);
            subJsonObject.put("voltage", this.voltage);
            subJsonObject.put("plugged", this.plugged.getBatteryPlugged());
            subJsonObject.put("status", this.status.getBatteryStatus());
            subJsonObject.put("health", this.health.getBatteryHealth());

            jsonObject.put("battery", subJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
            return level / (float) scale;
        } else {
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
    public SKBatteryPlugged getBatteryPlugged() {
        return this.plugged;
    }

    /**
     * Get the battery status
     *
     * @return status
     */
    @SuppressWarnings("unused")
    public SKBatteryStatus getBatteryStatus() {
        return this.status;
    }

    /**
     * Get the battery health
     *
     * @return health
     */
    @SuppressWarnings("unused")
    public SKBatteryHealth getBatteryHealth() {
        return this.health;
    }

}
