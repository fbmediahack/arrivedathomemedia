package com.example.arriveathomemedia.Speaker;


import com.harman.hkwirelessapi.AudioCodecHandler;
import com.harman.hkwirelessapi.HKWirelessHandler;
import com.harman.hkwirelessapi.HKWirelessListener;

import timber.log.Timber;

public class SpeakerConfigurator {

    private static final String KEY = "2FA8-2FD6-C27D-47E8-A256-D011-3751-2BD6";

    HKWirelessHandler hControlHandler = new HKWirelessHandler();

    AudioCodecHandler hAudioControl = new AudioCodecHandler();

    private static long deviceId = 211059561019568l;


    public void startSpeaker() {

        // Initialize the HKWControlHandler and start wireless audio
        hControlHandler.initializeHKWirelessController(KEY);

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

        hControlHandler.addDeviceToSession(deviceId);
    }

    public void findSpeakers() {
        hControlHandler.refreshDeviceInfoOnce();
    }

    public void startPlaying(String path, String title) {
        if (hAudioControl.isPlaying()) {
            hAudioControl.stop();
        }
        hAudioControl.playCAF(path, title, false);
    }

    public void stopPlaying() {
        hAudioControl.stop();
    }

    public void volumeUp() {
        int volume = hAudioControl.getDeviceVolume(deviceId);
        Timber.d("Current volume at %d", volume);
        volume += 5;
        hAudioControl.setVolumeDevice(deviceId, volume);
    }

    public void volumeDown() {
        int volume = hAudioControl.getDeviceVolume(deviceId);
        Timber.d("Current volume at %d", volume);
        volume -= 5;
        hAudioControl.setVolumeDevice(deviceId, volume);
    }


}
