# SensingKit-Android Library

An Android library that provides Continuous Sensing functionality to your applications. For more information, please refer to the [project website](http://www.sensingkit.org).


## Supported Sensors

The following sensor modules are currently supported in SensingKit-Android, (listed in [SensorModuleType](SensingKitLib/src/main/java/org/sensingkit/sensingkitlib/modules/SensorModuleType.java) enum):

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
- Audio Level

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
    compile 'org.sensingkit:SensingKitLib-release:0.1@aar'
    compile 'com.android.support:appcompat-v7:21.0.3’
    compile 'com.google.android.gms:play-services-location:6.5.87'
}
```


## How to Use this Library

- import and init SensingKit into your class as shown bellow:

```java
import org.sensingkit.sensingkitlib.SensingKitLib;

SensingKitLibInterface mSensingKitLib = SensingKitLib.getSensingKitLib(this);
```


- Register a sensor module as shown bellow:

```java
mSensingKitLib.registerSensorModule(SensorModuleType.LIGHT);
```


- Subscribe a sensor data listener as shown bellow:

```java
mSensingKitLib.subscribeSensorDataListener(SensorModuleType.LIGHT, new SKSensorDataListener() {
    @Override
    public void onDataReceived(final SensorModuleType moduleType, final DataInterface moduleData) {
        System.out.println(data);  // Print data
    }
});
```


- You can Start and Stop the Continuous Sensing using the following commands:

```java
mSensingKitLib.startContinuousSensingWithSensor(SensorModuleType.LIGHT);
mSensingKitLib.stopContinuousSensingWithSensor(SensorModuleType.LIGHT);
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
