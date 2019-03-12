package com.plotprojects.eddystoneexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class BeaconMessageReceiver extends BroadcastReceiver {

    private static final String TAG = "BeaconMessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Nearby.getMessagesClient(context).handleIntent(intent, new MessageListener() {
            @Override
            public void onFound(Message message) {
                EddystoneHelper.beaconEntered(TAG,message);
            }

            @Override
            public void onLost(Message message) {
                EddystoneHelper.beaconExited(TAG,message);
            }
        });
    }
}
