package org.sensingkit.sensingkitlib.data;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.Locale;

/**
 * Created by U3D3 on 11/14/15.
 */
public class SKEddystoneProximityData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = "SKEddystoneProximityData";

    protected final String namespaceId;
    protected final int instanceId;
    protected final int rssi;
    protected final int txPower;

    public SKEddystoneProximityData(long timestamp, String namespaceId, int instanceId, int rssi, int txPower) {
        super(SKSensorType.EDDYSTONE_PROXIMITY, timestamp);
        this.namespaceId = namespaceId;
        this.instanceId = instanceId;
        this.rssi = rssi;
        this.txPower = txPower;
    }

    @Override
    public String getDataInCSV() {

        return String.format(Locale.US, "%d,%s,%s,%d,%d", this.timestamp, this.namespaceId, this.instanceId, this.rssi, this.txPower );
    }

    @SuppressWarnings("unused")
    public String getNamespaceId(){
        return this.namespaceId;
    }

    @SuppressWarnings("unused")
    public int getInstanceId(){
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
