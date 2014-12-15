# SensingKit-Android Library

An Android library that provides Continuous Sensing functionality to your applications. For more information, please refer to the [project website](http://www.sensingkit.org).


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
}
```

## How to Use this Library

- import and init SensingKit into your class as shown bellow:



```
import org.sensingkit.sensingkitlib.SensingKitLib;

SensingKitLibInterface mSensingKitLib = SensingKitLib.getSensingKitLib(this);
```


- Register a sensor data listener as shown bellow:



```
mSensingKitLib.subscribeToSensor(SensorModuleType, new SKSensorDataListener() {
                @Override
                public void onDataReceived(final SensorModuleType moduleType, final DataInterface data) {
                    System.out.println(data);
                }

            });
```



- You can Start and Stop the Continuous Sensing using the following commands:

```
mSensingKitLib.startContinuousSensingWithSensor(SensorModuleType);
mSensingKitLib.stopContinuousSensingWithSensor(SensorModuleType);
```



- The following sensor modules are currently supported in SensingKit-Android, (located in SensorModuleType enum):

    — ACCELEROMETER
    — GRAVITY
    — LINEAR_ACCELERATION
    — GYROSCOPE
    — ROTATION
    — MAGNETOMETER
    — AMBIENT_TEMPERATURE
    — STEP_DETECTOR
    — STEP_COUNTER
    — LIGHT
    — LOCATION
    — ACTIVITY
    — BATTERY


For a compete description of our API, please refer to the [project website](http://www.sensingkit.org).

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