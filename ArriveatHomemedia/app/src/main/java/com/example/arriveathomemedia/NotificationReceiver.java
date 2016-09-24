package com.example.arriveathomemedia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.example.arriveathomemedia.Speaker.SpeakerConfigurator;
import com.example.arriveathomemedia.easybulb.EasyBulbConfig;

import timber.log.Timber;

/**
 * Created by ana on 24/09/16.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Timber.d("Action is:" + action);

        onMoodChange(action, context.getApplicationContext());

    }

    private void onMoodChange(String mood, Context context) {
        EasyBulbConfig easyBulbConfig = ((HomeMediaApplication) context.getApplicationContext()).getEasyBulbConfig();
        SpeakerConfigurator speakerConfigurator = ((HomeMediaApplication) context).getSpeakerConfigurator();

        String externalStorage = Environment.getExternalStorageDirectory() + "/";
        switch (mood) {
            case Mood.COOL:
                easyBulbConfig.cool();
                speakerConfigurator.startPlaying(externalStorage + "cool.mp3", "Cool music");
                //      layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.mood_cool_color));
                break;
            case Mood.POOP:
                easyBulbConfig.poop();
                speakerConfigurator.startPlaying(externalStorage + "poop.mp3", "Poop music");
//                layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.mood_poop_color));
                break;
            case Mood.BOOM:
                easyBulbConfig.boom();
                speakerConfigurator.startPlaying(externalStorage + "boom.mp3", "Boom!");
//                layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.mood_boom_color));
                break;
        }
    }
}
