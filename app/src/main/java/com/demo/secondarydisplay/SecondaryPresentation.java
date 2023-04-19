package com.demo.secondarydisplay;

import android.app.Presentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SecondaryPresentation extends Presentation {
    private BroadcastReceiver broadcastReceiver;
    private TextView textView;
    public SecondaryPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation);
        this.textView = findViewById(R.id.tv_count);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Settings.broadcast);
        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int count = intent.getIntExtra(Settings.count,0);
                textView.setText( String.valueOf(count) );
            }
        }, filter);
        setOnDismissListener(dialog -> LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver));
    }
}
