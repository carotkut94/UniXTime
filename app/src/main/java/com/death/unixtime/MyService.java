package com.death.unixtime;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rajora_sd on 12/26/2016.
 */

public class MyService extends Service
{

    Timer myTimer;

    private boolean timerRunning = false;

    private long RETRY_TIME = 1;
    private long START_TIME = 1000;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new Task(), START_TIME, RETRY_TIME);
        timerRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!timerRunning) {
            myTimer = new Timer();
            myTimer.scheduleAtFixedRate(new Task(), START_TIME, RETRY_TIME);
            timerRunning = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public class Task extends TimerTask {

        @Override
        public void run() {
            buildUpdate();
        }

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        if (myTimer != null) {
            myTimer.cancel();

        }

        timerRunning = false;
    }


    private void buildUpdate()
    {
        String lastUpdated = DateFormat.format("hh:mm:ss", new Date()).toString();

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.collection_widget);
        view.setTextViewText(R.id.timeWidget,Long.toString(System.currentTimeMillis()));
        view.setTextViewText(R.id.dateSlot,lastUpdated );
        ComponentName thisWidget = new ComponentName(this, TimeWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, view);
    }
}


