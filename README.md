# SensingKit-Android Library

An Android library that provides Continuous Sensing functionality to your applications. For more information, please refer to the [project website](https://www.sensingkit.org).


## Supported Sensors

The following sensor modules are currently supported in SensingKit-Android, (listed in [SKSensorType](SensingKitLib/src/main/java/org/sensingkit/sensingkitlib/SKSensorType.java) enum):

- Accelerometer
- Gravity
- Linear Acceleration
- Gyroscope
- Rotation
- Magnetometer
- Ambient Temperature
- Step Detector
- Step Counter
- Light
- Location
- Motion Activity
- Battery Status
- Screen Status
- Microphone
- Audio Level
- Bluetooth
- Beacon Proximity
- Humidity
- Barometer
- Notification

## Configuring the Library

- Build the library using the command:

```
./gradlew build
```

- Create an `app/libs` directory inside your project and copy the generated `SensingKitLib/build/outputs/aar/SensingKitLib-release.aar` (or the equivalent debug) file there.

- Edit your app/build.gradle file and add a flatDir entry as shown below:

```
repositories {
    mavenCentral()
    flatDir {
        dirs 'libs'
    }
}
```


- In the same `app/build.gradle` file, add SensingKit as a dependency as shown below:

```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'org.sensingkit:SensingKitLib-release@aar'
    implementation 'com.google.android.gms:play-services-location:16.0.0'
}
```


## How to Use this Library

Import and init SensingKit as shown below:

```java
import org.sensingkit.sensingkitlib.*;
import org.sensingkit.sensingkitlib.data.*;

public class MainActivity extends AppCompatActivity {

    SensingKitLibInterface mSensingKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // init SensingKit
        mSensingKit = SensingKitLib.sharedSensingKitLib(this);
    }
}
```


Check if a sensor is available in the device:

```java
if (mSensingKit.isSensorAvailable(SKSensorType.LIGHT)) {
    // You can access the sensor
}
```


Register a sensor (e.g. a Light sensor) as shown below:

```java
mSensingKit.registerSensor(SKSensorType.LIGHT);
```


Subscribe a sensor data listener:

```java
mSensingKit.subscribeSensorDataHandler(SKSensorType.LIGHT, new SKSensorDataHandler() {
    @Override
    public void onDataReceived(final SKSensorType moduleType, final SKSensorData sensorData) {
        System.out.println(sensorData.getDataInCSV());  // Print data in CSV format
    }
});
```


You can cast the data object into the actual sensor data object in order to access all the sensor data properties:

```java
SKLightData lightData = (SKLightData)sensorData;
```


You can Start and Stop the Continuous Sensing using the following commands:

```java
// Start
mSensingKit.startContinuousSensingWithSensor(SKSensorType.LIGHT);

// Stop
mSensingKit.stopContinuousSensingWithSensor(SKSensorType.LIGHT);
```

For a complete description of our API, please refer to the [project website](https://www.sensingkit.org).


## Required Permissions

Depending on the used sensor and its configuration, some additional permissions are required to be granted by the user. SensingKit automates this process by providing the following APIs:

```java
boolean isPermissionGrantedForSensor(final SKSensorType sensorType) throws SKException;

void requestPermissionForSensor(final SKSensorType sensorType, final @NonNull Activity activity) throws SKException;

void requestPermissionForAllRegisteredSensors(final @NonNull Activity activity);
```

For example, in order to request permission to access the Location sensor:

```java
if (!isPermissionGrantedForSensor(SKSensorType.LOCATION) {
    requestPermissionForSensor(SKSensorType.LOCATION, this);
}
```

You will also need to add a `<uses-permission>` element in your app manifest, as a child of the top-level `<manifest>` element:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="org.sensingkit.crowdsensing_android">

    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <!-- other permissions go here -->

    <application ...>
        ...
    </application>
</manifest>
```

The permissions required by the following SensingKit sensors are:

### Location
- `android.permission.ACCESS_FINE_LOCATION`


### Motion Activity

- `com.google.android.gms.permission.ACTIVITY_RECOGNITION`


### Microphone

- `android.permission.RECORD_AUDIO`


### Audio Level

- `android.permission.RECORD_AUDIO`


### Bluetooth

- `android.permission.BLUETOOTH`
- `android.permission.BLUETOOTH_ADMIN`


### Beacon Proximity

- `android.permission.ACCESS_FINE_LOCATION`


For more information about Android's App Permissions, please visit: https://developer.android.com/training/permissions/requesting.


## Special Permissions

Some sensors (i.e. only the Notification sensor at this moment) require some special actions from the user to acquire permision to access it. The user needs to visit the phone's `Settings > Advanced > App Permissions > Special app access` and grant the special access to the app (i.e. `Notification access` in the case of the Notification sensor).

This long proccess can be simplified by sending the user directly to that screen, using the following call:

```java
startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
```


## License

```
Copyright (c) 2014. Kleomenis Katevas
Kleomenis Katevas, k.katevas@imperial.ac.uk.

This file is part of SensingKit-Android library.
For more information, please visit https://www.sensingkit.org.

SensingKit-Android is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SensingKit-Android is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with SensingKit-Android.  If not, see <http://www.gnu.org/licenses/>.
```

This library is available under the GNU Lesser General Public License 3.0, allowing to use the library in your applications.

If you want to help with the open source project, contact hello@sensingkit.org.
