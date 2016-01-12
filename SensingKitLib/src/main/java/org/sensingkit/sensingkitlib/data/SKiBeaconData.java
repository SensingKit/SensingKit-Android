package org.sensingkit.sensingkitlib.data;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 * Created by U3D3 on 1/12/16.
 */
public class SKiBeaconData extends SKAbstractData{

    @SuppressWarnings("unused")
    private static final String TAG = "SKiBeaconData";

    protected final String namespaceId;
    protected final String instanceId;
    protected final int rssi;
    protected final int txPower;

    public SKiBeaconData(long timestamp, String namespaceId, String instanceId, int rssi, int txPower) {
        super(SKSensorType.IBEACON, timestamp);
        this.namespaceId = namespaceId; //**
        this.instanceId = instanceId; //**
        this.rssi = rssi;
        this.txPower = txPower;
    }

    @Override
    public String getDataInCSV() {
        //TODO Check format
        return String.format(Locale.US, "%d,%s,%s,%d,%d", this.timestamp, this.namespaceId, this.instanceId, this.rssi, this.txPower );
    }

    @SuppressWarnings("unused")
    public String getNamespaceId(){
        return this.namespaceId;
    }

    @SuppressWarnings("unused")
    public String getInstanceId(){
        return this.instanceId;
    }

    @SuppressWarnings("unused")
    public int getRssi(){
        return this.rssi;
    }

    @SuppressWarnings("unused")
    public int getTxPower(){
        return this.txPower;
    }
}
