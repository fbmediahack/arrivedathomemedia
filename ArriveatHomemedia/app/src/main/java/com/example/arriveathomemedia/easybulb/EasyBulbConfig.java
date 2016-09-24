package com.example.arriveathomemedia.easybulb;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ricardobelchior on 24/09/2016.
 */
public class EasyBulbConfig {

    public static final int COLOR_COOL = 66;
    public static final int COLOR_POOP = 143;


    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final WiFiBox wifiBox = getWifiBox();

    private final Context ctx;


    public EasyBulbConfig(Context ctx) {
        this.ctx = ctx;
    }

    public void on() {
        stopSensorListener();

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
        stopSensorListener();

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

    public void cool() {
        stopSensorListener();

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
        stopSensorListener();

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

//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    wifiBox.discoMode();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        startSensorListener();
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




    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    private SensorManager mSensorManager;

    private void startSensorListener() {

        mSensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(sensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            final float alpha = 0.8f;

            double[] gravity = new double[3];
            double[] linear_acceleration = new double[3];

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];


            //Log.d("TAG", linear_acceleration[0] + ", " + linear_acceleration[1] + ", " + linear_acceleration[2]);
            double sum = Math.abs(linear_acceleration[0] + linear_acceleration[1] + linear_acceleration[2]);
            double color = WiFiBox.MAX_COLOR / sum;
            Log.d("TAG", "color: " + color);

            color((int) color);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    private void stopSensorListener() {
        try {
            if (mSensorManager != null) {
                mSensorManager.unregisterListener(sensorListener);
            }
        } catch (Exception ignored) {}
    }

}
