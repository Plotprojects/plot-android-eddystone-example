package com.plotprojects.eddystoneexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.EddystoneUid;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageFilter;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.plotprojects.retail.android.Plot;
import com.plotprojects.retail.android.TriggerType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EddystoneExample";
    private static final String EDDYSTONE_NAMESPACE = "edd1ebeac04e5defa017";
    MessageListener mMessageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Plot.init(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribe();
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
                EddystoneUid eddystoneUid = EddystoneUid.from(message);
                String eddystoneInstance = eddystoneUid.getInstance();
                Log.i(TAG, "entered Eddystone instance " + eddystoneInstance + " of namespace " + eddystoneUid.getNamespace());

                Map<String,String> uidProperty = new HashMap<>();
                uidProperty.put("uid", eddystoneInstance);
                Collection<Map<String, String>> allProperties = new ArrayList<>();
                allProperties.add(uidProperty);
                Plot.externalRegionTrigger(allProperties, TriggerType.ENTER);
            }

            @Override
            public void onLost(Message message) {
                EddystoneUid eddystoneUid = EddystoneUid.from(message);
                String eddystoneInstance = eddystoneUid.getInstance();
                Log.i(TAG, "exited Eddystone instance " + eddystoneInstance + " of namespace " + eddystoneUid.getNamespace());

                Map<String,String> uidProperty = new HashMap<>();
                uidProperty.put("uid", eddystoneInstance);
                Collection<Map<String, String>> allProperties = new ArrayList<>();
                allProperties.add(uidProperty);
                Plot.externalRegionTrigger(allProperties, TriggerType.EXIT);
            }
        };
    }

    private void subscribe() {
        Log.i(TAG, "Subscribing.");
        MessageFilter.Builder messageFilterBuilder = new MessageFilter.Builder();
        MessageFilter messageFilter = messageFilterBuilder.
                includeEddystoneUids(EDDYSTONE_NAMESPACE,null) // subscribe to namespace
                .build();
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .setFilter(messageFilter)
                .build();
        Nearby.getMessagesClient(this).subscribe(mMessageListener, options);
    }
}
