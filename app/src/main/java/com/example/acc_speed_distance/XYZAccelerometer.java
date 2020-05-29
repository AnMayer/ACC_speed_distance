package com.example.acc_speed_distance;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class XYZAccelerometer extends Accelerometer{
    private static final int BUFFER_SIZE = 500;
    // calibration
    private  float dX = 0.04f;
    private  float dY = -0.1f;
    private  float dZ = -9.88f;
    // buffer variables
    private float X;
    private float Y;
    private float Z;

    //private float lastX;
   // private float lastY;
   // private float lastZ;
    private int cnt = 0;

    // returns last SenorEvent parameters
    public Point getLastPoint(){
        return new Point(lastX, lastY, lastZ, 1);
    }

    // returns parameters, using buffer: average acceleration
    // since last call of getPoint().
    public Point getPoint(){

        if (cnt == 0){
            return new Point(lastX, lastY, lastZ, 1);
        }

        Point p =  new Point(X, Y, Z, cnt);

        reset();
        return p;
    }

    // resets buffer
    public void reset(){
        cnt = 0;
        X = 0;
        Y = 0;
        Z = 0;
    }


    public void onSensorChanged(SensorEvent se) {
        float x = se.values[0] + dX;
        float y = se.values[1] + dY;
        float z = se.values[2] + dZ;

        lastX = x;
        lastY = y;
        lastZ = z;

        X+= x;
        Y+= y;
        Z+= z;

        if (cnt < BUFFER_SIZE-1) {
            cnt++;
        } else
        {
            reset();
        }
    }

    public  void setdX(float dX) {
        this.dX = dX;
    }

    public  void setdY(float dY) {
        this.dY = dY;
    }

    public  void setdZ(float dZ) {
        this.dZ = dZ;
    }
}
