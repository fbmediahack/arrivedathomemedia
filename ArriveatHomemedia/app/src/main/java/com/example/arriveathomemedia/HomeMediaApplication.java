package com.example.arriveathomemedia;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by ana on 24/09/16.
 */

public class HomeMediaApplication extends Application {

    private BeaconManager beaconManager;

    @Override public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "home_entrance",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        12840, 14876));
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification(
                        "How are you feeling today?",
                        "SOME OTHER TEXT HERE");
            }

            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });

    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent coolIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent poopIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent boomIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);


        RemoteInput coolRemoteInput = new RemoteInput.Builder("reply_cool")
                .setLabel("Cool")
                .build();

        RemoteInput poopRemoteInput = new RemoteInput.Builder("reply_poop")
                .setLabel("Poop")
                .build();

        RemoteInput boomRemoteInput = new RemoteInput.Builder("reply_boom")
                .setLabel("Boom")
                .build();


        Notification.Action actionCool =
                new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.emoticon_cool),
                        "", coolIntent)
                        .addRemoteInput(coolRemoteInput)
                        .build();

        Notification.Action actionPoop =
                new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.emoticon_poop),
                        "", poopIntent)
                        .addRemoteInput(poopRemoteInput)
                        .build();

        Notification.Action actionBoom =
                new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.boombox),
                        "", boomIntent)
                        .addRemoteInput(boomRemoteInput)
                        .build();

        Notification notification =
                new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.readability)
                        .setContentTitle(title)
                        .setContentText(message)
                        .addAction(actionCool)
                        .addAction(actionPoop)
                        .addAction(actionBoom)
                        .build();


        notification.defaults |= Notification.DEFAULT_SOUND;


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
