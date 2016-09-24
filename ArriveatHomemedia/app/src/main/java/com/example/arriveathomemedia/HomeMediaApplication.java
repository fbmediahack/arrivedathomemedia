package com.example.arriveathomemedia;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.example.arriveathomemedia.Speaker.SpeakerConfigurator;
import com.example.arriveathomemedia.easybulb.EasyBulbConfig;

import java.util.List;
import java.util.UUID;

import timber.log.Timber;

/**
 * Created by ana on 24/09/16.
 */

public class HomeMediaApplication extends Application {

    private BeaconManager beaconManager;
    private SpeakerConfigurator speakerConfigurator;
    private EasyBulbConfig easyBulbConfig;

    @Override public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        easyBulbConfig = new EasyBulbConfig(this);

        speakerConfigurator = new SpeakerConfigurator();
        speakerConfigurator.startSpeaker();
        speakerConfigurator.findSpeakers();

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
                        "Welcome home!",
                        "How do you feel today?");
            }

            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });

    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent();

        PendingIntent coolIntent = PendingIntent.getBroadcast(this, 0,
                notifyIntent.setAction(Mood.COOL), PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent poopIntent = PendingIntent.getBroadcast(this, 1,
                notifyIntent.setAction(Mood.POOP), PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent boomIntent = PendingIntent.getBroadcast(this, 2,
                notifyIntent.setAction(Mood.BOOM), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action actionCool =
                new NotificationCompat.Action.Builder(R.drawable.emoticon_cool, "", coolIntent)
                        .build();

        NotificationCompat.Action actionPoop =
                new NotificationCompat.Action.Builder(R.drawable.emoticon_poop, "", poopIntent)
                        .build();

        NotificationCompat.Action actionBoom =
                new NotificationCompat.Action.Builder(R.drawable.boombox, "", boomIntent)
                        .build();

        Notification notification =
                new NotificationCompat.Builder(this)
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

    public SpeakerConfigurator getSpeakerConfigurator() {
        return speakerConfigurator;
    }

    public EasyBulbConfig getEasyBulbConfig() {
        return easyBulbConfig;
    }
}
