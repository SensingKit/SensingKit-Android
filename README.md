# SensingKit-Android Library

An Android library that provides Continuous Sensing functionality to your applications. For more information, please refer to the [project website](http://www.sensingkit.org).


## Supported Sensors

The following sensor modules are currently supported in SensingKit-Android, (listed in [SKSensorModuleType](SensingKitLib/src/main/java/org/sensingkit/sensingkitlib/SKSensorModuleType.java) enum):

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
- Activity
- Battery
- Screen Status
- Audio Recorder
- Audio Level
- Bluetooth

## Configuring the Library

- Build the library using the command:

```
./gradlew build
```

- Create an app/libs directory inside your project and copy the generated SensingKitLib/build/outputs/aar/SensingKitLib-release.aar (or the equivalent debug) file there.

- Edit your app/build.gradle file and add a flatDir entry as shown bellow:

```
repositories {
    mavenCentral()
    flatDir {
        dirs 'libs'
    }
}
```


- In the same app/build.gradle file, add SensingKitLib as a dependency as shown below:

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'org.sensingkit:SensingKitLib-release@aar'
    compile 'com.android.support:appcompat-v7:22.2.1’
    compile 'com.google.android.gms:play-services-location:7.5.0'
}
```


## How to Use this Library

- Import and init SensingKit into your Activity class as shown bellow:

```java
import org.sensingkit.sensingkitlib.SensingKitLib;

SensingKitLibInterface mSensingKitLib = SensingKitLib.getSensingKitLib(this);
```


- Register a sensor module (e.g. a Light sensor) as shown bellow:

```java
mSensingKitLib.registerSensorModule(SKSensorModuleType.LIGHT);
```


- Subscribe a sensor data listener:

```java
mSensingKitLib.subscribeSensorDataListener(SKSensorModuleType.LIGHT, new SKSensorDataListener() {
    @Override
    public void onDataReceived(final SKSensorModuleType moduleType, final SKSensorData sensorData) {
        System.out.println(sensorData.getDataInCSV());  // Print data in CSV format
    }
});
```


- You can cast the data object into the actual sensor data object in order to access all the sensor data properties:
 
```java
SKLightData lightData = (SKLightData)sensorData;
```



- You can Start and Stop the Continuous Sensing using the following commands:

```java
mSensingKitLib.startContinuousSensingWithSensor(SKSensorModuleType.LIGHT);
mSensingKitLib.stopContinuousSensingWithSensor(SKSensorModuleType.LIGHT);
```


For a complete description of our API, please refer to the [project website](http://www.sensingkit.org).

## License

```
Copyright (c) 2014. Queen Mary University of London
Kleomenis Katevas, k.katevas@qmul.ac.uk.

This file is part of SensingKit-Android library.
For more information, please visit http://www.sensingkit.org.

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
