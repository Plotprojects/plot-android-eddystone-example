package com.plotprojects.eddystoneexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.NearbyPermissions;

public class BeaconMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MessagesOptions messagesOptions = new MessagesOptions.Builder().setPermissions(NearbyPermissions.BLE).build();
        Nearby.getMessagesClient(context,messagesOptions).handleIntent(intent, new MessageListener() {
            @Override
            public void onFound(Message message) {
                EddystoneHelper.beaconEntered(message);
            }

            @Override
            public void onLost(Message message) {
                EddystoneHelper.beaconExited(message);
            }
        });
    }
}
