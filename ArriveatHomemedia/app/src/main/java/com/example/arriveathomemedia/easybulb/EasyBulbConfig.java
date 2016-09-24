package com.example.arriveathomemedia.easybulb;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ricardobelchior on 24/09/2016.
 */
public class EasyBulbConfig {

    public static final int COLOR_COOL = 66;
    public static final int COLOR_POOP = 143;

    private final ExecutorService executorService;

    private final WiFiBox wifiBox;


    public EasyBulbConfig() {

        wifiBox = getWifiBox();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void cool() {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    wifiBox.color(COLOR_COOL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void poop() {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    wifiBox.color(COLOR_POOP);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void boom() {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    wifiBox.discoMode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void on() {


        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    wifiBox.on();
                    wifiBox.white();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void off() {


        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    wifiBox.off();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void color(final int color) {


        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    wifiBox.color(color);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private WiFiBox getWifiBox() {
        String host = "192.168.0.137";
        int port = WiFiBox.DEFAULT_PORT;

        WiFiBox wiFiBox = null;
        try {
            wiFiBox = new WiFiBox(host, port);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return wiFiBox;
    }


}
