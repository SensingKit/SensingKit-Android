/*
 * Copyright (c) 2016. Queen Mary University of London
 * Kleomenis Katevas, k.katevas@qmul.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit https://www.sensingkit.org
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

package test.sensingkittestapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.sensingkit.sensingkitlib.*;
import org.sensingkit.sensingkitlib.configuration.SKBeaconProximityConfiguration;
import org.sensingkit.sensingkitlib.data.SKSensorData;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    SensingKitLibInterface mSensingKitLib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SKSensorType sensor = SKSensorType.BEACON_PROXIMITY;
        SKBeaconProximityConfiguration configuration = new SKBeaconProximityConfiguration();
        configuration.setBeaconType(SKBeaconProximityConfiguration.BeaconType.IBEACON);

        try {
            mSensingKitLib = SensingKitLib.getSensingKitLib(this);
            mSensingKitLib.registerSensor(sensor, configuration);

            mSensingKitLib.subscribeSensorDataListener(sensor, new SKSensorDataListener() {
                @Override
                public void onDataReceived(final SKSensorType moduleType, final SKSensorData sensorData) {
                    Log.i("MainActivity", sensorData.getDataInCSV());  // Print data in CSV format
                }
            });
        }
        catch (SKException ex) {
            ex.printStackTrace();
            Log.i("MainActivity", ex.getMessage());
        }

        Button startSensingButton = (Button)findViewById(R.id.buttonStart);
        startSensingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("MainActivity", "Start");
                try {
                    mSensingKitLib.startContinuousSensingWithAllRegisteredSensors();
                }
                catch (SKException ex) {
                    ex.printStackTrace();
                    Log.i("App", ex.getMessage());
                }
            }
        });

        Button stopSensingButton = (Button)findViewById(R.id.buttonStop);
        stopSensingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("MainActivity", "Stop");

                try {
                    mSensingKitLib.stopContinuousSensingWithAllRegisteredSensors();
                }
                catch (SKException ex) {
                    ex.printStackTrace();
                    Log.i("App", ex.getMessage());
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }

                });
                builder.show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MA", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
            }
        }
    }
}
