package com.plotprojects.eddystoneexample;

import android.util.Log;

import com.google.android.gms.nearby.messages.EddystoneUid;
import com.google.android.gms.nearby.messages.Message;
import com.plotprojects.retail.android.Plot;
import com.plotprojects.retail.android.TriggerType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EddystoneHelper {
    public static void beaconEntered(String TAG, Message message){
        beaconChanged(TAG,message,false);
    }

    public static void beaconExited(String TAG, Message message){
        beaconChanged(TAG,message,true);
    }

    private static void beaconChanged(String TAG, Message message, boolean isExit){
        EddystoneUid eddystoneUid = EddystoneUid.from(message);
        String eddystoneInstance = eddystoneUid.getInstance();
        Log.i(TAG, isExit ? "exited" : "entered" + " Eddystone instance " + eddystoneInstance + " of namespace " + eddystoneUid.getNamespace());
        Plot.externalRegionTrigger(setupProperties(eddystoneInstance), isExit ? TriggerType.EXIT : TriggerType.ENTER);
    }

    private static Collection<Map<String,String>> setupProperties(String eddystoneInstance){
        Map<String,String> uidProperty = new HashMap<>();
        uidProperty.put("id", eddystoneInstance);
        Collection<Map<String, String>> allProperties = new ArrayList<>();
        allProperties.add(uidProperty);
        return allProperties;
    }

}
