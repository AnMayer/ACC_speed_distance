package com.example.acc_speed_distance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.util.Timer;
import java.util.TimerTask;




public class MainActivity extends Activity {
    static final int TIMER_DONE = 2;
    static final int START = 3;
    private XYZAccelerometer xyzAcc;
    private SensorManager mSensorManager;
    private static final long UPDATE_INTERVAL = 500;
    private static final long MEASURE_TIMES = 20;
    private Timer timer;
    private TextView tv;
    private Button testBtn;
    int counter;
    public MeasureData mdXYZ;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        testBtn = findViewById(R.id.btn);
       // testBtn.setOnClickListener((View.OnClickListener) this);
    }

    /** handler for async events*/
    Handler hRefresh = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIMER_DONE:

                    onMeasureDone();
                    @SuppressLint("HandlerLeak") String es1 = Float.toString(Math.round(mdXYZ.getLastSpeedKm()*100)/100f);
                    tv.append(" END SPEED " + es1  + " \n");
                    enableButtons();
                    break;
                case START:
                    tv.append(" START" + "\n");
                    timer = new Timer();
                    timer.scheduleAtFixedRate(
                            new TimerTask() {

                                public void run() {
                                    dumpSensor();
                                }
                            },
                            0,
                            UPDATE_INTERVAL);

                    break;
            }
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        tv.append("\n ..");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setAccelerometer();
        mSensorManager.registerListener(xyzAcc,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(xyzAcc);
        super.onPause();
    }


    public void onButtonTest(View v) {
        disableButtons();
        mdXYZ = new MeasureData(UPDATE_INTERVAL);
        counter = 0;
        tv.setText("");
        hRefresh.sendEmptyMessage(START);
    }

    void dumpSensor() {
        ++counter;
        mdXYZ.addPoint(xyzAcc.getPoint());

        if (counter > MEASURE_TIMES) {
            timer.cancel();
            hRefresh.sendEmptyMessage(TIMER_DONE);
        }

    }

    private void enableButtons() {
        testBtn.setEnabled(true);

    }



    private void setAccelerometer() {
        xyzAcc = new XYZAccelerometer();
        mSensorManager.registerListener(xyzAcc,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }


    private void disableButtons() {
        testBtn.setEnabled(false);
    }

    private void onMeasureDone() {
        mdXYZ.process();
    }

}