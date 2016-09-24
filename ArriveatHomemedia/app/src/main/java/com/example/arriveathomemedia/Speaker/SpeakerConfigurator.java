package com.example.arriveathomemedia.Speaker;


import android.os.Environment;

import com.harman.hkwirelessapi.AudioCodecHandler;
import com.harman.hkwirelessapi.HKWirelessHandler;
import com.harman.hkwirelessapi.HKWirelessListener;

import timber.log.Timber;

public class SpeakerConfigurator {

    private static final String KEY = "2FA8-2FD6-C27D-47E8-A256-D011-3751-2BD6";

    HKWirelessHandler hControlHandler = new HKWirelessHandler();

    AudioCodecHandler hAudioControl = new AudioCodecHandler();


    public void startSpearker() {

        // Initialize the HKWControlHandler and start wireless audio
        hControlHandler.initializeHKWirelessController(KEY);
    }

    public void findSpeakers() {
        hControlHandler.refreshDeviceInfoOnce();

        hControlHandler.registerHKWirelessControllerListener(new HKWirelessListener() {
            @Override
            public void onPlayEnded() {
                Timber.d("PlayEnded");
            }

            @Override
            public void onPlaybackStateChanged(int playState) {
                Timber.d("PlaybackState :" + playState);
            }

            @Override
            public void onPlaybackTimeChanged(int timeElapsed) {
                Timber.d("TimeElapsed :" + timeElapsed);
            }

            @Override
            public void onVolumeLevelChanged(long deviceId, int deviceVolume, int avgVolume) {
                Timber.d("DeviceId:" + deviceId + "Volume:" + deviceVolume);
            }

            @Override
            public void onDeviceStateUpdated(long deviceId, int reason) {
                Timber.d("DeviceStateUpdated:" + deviceId);
            }

            @Override
            public void onErrorOccurred(int errorCode, String errorMsg) {
                Timber.d("Error:" + errorMsg);
            }
        });

        hControlHandler.addDeviceToSession(211059561019568l);
        String path = Environment.getExternalStorageDirectory() + "/Download/Crazy game .mp3";
        hAudioControl.playCAF(path, "Some title", false);
    }


}
