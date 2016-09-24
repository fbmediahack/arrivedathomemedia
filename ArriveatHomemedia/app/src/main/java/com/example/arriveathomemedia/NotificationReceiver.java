package com.example.arriveathomemedia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

/**
 * Created by ana on 24/09/16.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Timber.d("Action is:" + action);

        switch (action) {
            case Mood.COOL:
                break;
            case Mood.POOP:
                break;
            case Mood.BOOM:
                break;
        }

    }
}
