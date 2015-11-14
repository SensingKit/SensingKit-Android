package org.sensingkit.sensingkitlib.sensors;

import android.content.Context;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.data.SKAbstractData;

/**
 * Created by U3D3 on 11/14/15.
 */
public class SKEddystoneProximity extends SKAbstractSensor {



    public SKEddystoneProximity(Context context) throws SKException{
        super(context, SKSensorType.EDDYSTONE_PROXIMITY);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {
        return false;
    }

    @Override
    public void startSensing() throws SKException {

    }

    @Override
    public void stopSensing() {

    }
}
