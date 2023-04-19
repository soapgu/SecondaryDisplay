package com.demo.secondarydisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayManager mDisplayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = mDisplayManager.getDisplays();
        for (Display display:displays
             ) {
            Logger.i( "Display ID:%s Name:%s",display.getDisplayId(),display.getName() );
        }

        Display display = mDisplayManager.getDisplay(1);
        boolean flag = (display.getFlags() & Display.FLAG_PRESENTATION) != 0;
        Logger.i( "-----Target Display ID:%s Name:%s Flag:%s-----",display.getDisplayId(),display.getName(),flag );

        this.findViewById(R.id.button_start).setOnClickListener( v->launchPresentationSecScreen( display ) );
        this.findViewById(R.id.button_count).setOnClickListener( v-> sendBroadcast());
    }

    private void launchActivitySecScreen(){
        Intent intent = new Intent(this, SecondaryActivity.class);
        ActivityOptions options = ActivityOptions.makeBasic();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            options.setLaunchDisplayId(1);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent,options.toBundle());
    }

    private void launchPresentationSecScreen(Display display){
        SecondaryPresentation presentation = new SecondaryPresentation(this,display);
        //presentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        //presentation.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //presentation.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        presentation.show();
    }

    private void sendBroadcast(){
        count ++;
        Intent intent = new Intent();
        intent.setAction(Settings.broadcast);
        intent.putExtra(Settings.count, count);
        Logger.i("Sent Broadcasts");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}