package com.plotprojects.eddystoneexample;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.plotprojects.retail.android.Plot;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EddystoneExample";
    private static final String EDDYSTONE_NAMESPACE = "edd1ebeac04e5defa017";
    MessageListener mMessageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Plot.init(this);

        subscribeBackground();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribeForeground();
                Snackbar.make(view, "Searching for Eddystone", Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        mMessageListener = getMessageListener();
    }

    private MessageListener getMessageListener(){
        return new MessageListener() {
            @Override
            public void onFound(Message message) {
                EddystoneHelper.beaconEntered(TAG,message);
            }

            @Override
            public void onLost(Message message) {
                EddystoneHelper.beaconExited(TAG, message);
            }
        };
    }

    private void subscribeForeground() {
        Log.i(TAG, "Subscribing.");
        MessageFilter.Builder messageFilterBuilder = new MessageFilter.Builder();
        MessageFilter messageFilter = messageFilterBuilder.
                includeEddystoneUids(EDDYSTONE_NAMESPACE,null) // subscribeForeground to namespace
                .build();
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .setFilter(messageFilter)
                .build();
        Nearby.getMessagesClient(this).subscribe(mMessageListener, options);
    }

    // Subscribe to messages in the background.
    private void subscribeBackground() {
        Log.i(TAG, "Subscribing for background updates.");
        MessageFilter.Builder messageFilterBuilder = new MessageFilter.Builder();
        MessageFilter messageFilter = messageFilterBuilder.
                includeEddystoneUids(EDDYSTONE_NAMESPACE,null) // subscribeForeground to namespace
                .build();
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .setFilter(messageFilter)
                .build();
        Nearby.getMessagesClient(this).subscribe(getPendingIntent(), options);
    }

    private PendingIntent getPendingIntent() {
        return PendingIntent.getBroadcast(this, 0, new Intent(this, BeaconMessageReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
