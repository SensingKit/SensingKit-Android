# Changelog

### 0.5.0 (??, 2018)
- Added Documentation using javadoc generator (thanks to Susan Crayne @crayne)
- Added support for Air Pressure sensor
- Added support for Humidity sensor
- Added support for Beacon Proximity (iBeacon™, Eddystone™ and AltBeacon) with the help of the Android Beacon Library (thanks Radius Networks)
- Added support for Notification sensor
- Added csvHeaderForSensor method to get the headers of the csv format
- Added missing SKMicrophoneData object
- Added support for Android Studio 3.2
- Added support for Android 8 SDK (Pie)
- Added support for maven distribution (jCenter and Maven Central)
- Renamed Activity sensor to Motion Activity
- Renamed Audio sensor to Microphone
- Renamed Air Pressure sensor to Barometer
- Renamed Battery sensor to BatteryStatus
- Renamed all sensor modules into sensors
- Updated SensingKit-Android API (thanks to Ming-Jiun Huang @U3D3)
- Updated Google Play Services to 16.0.0

### 0.2.0 (July 30, 2015)
- Added support for Bluetooth sensor
- DataInterface method getDataInString() has been renamed to getDataInCSV()
- SKDataInterface has been renamed to SKSensorData
- Added getSensorModuleType() to SKSensorData
- Added SK prefix to all source files
- Added support for Android Studio 1.3
- Updated Build Tools version to 22.0.1

### 0.1.0 (July 13, 2015)
- Initial Release