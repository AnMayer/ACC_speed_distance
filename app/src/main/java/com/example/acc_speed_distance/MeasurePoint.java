package com.example.acc_speed_distance;

public class MeasurePoint {
    private float x;
    private float y;
    private float z;
    private float speedBefore;
    private float speedAfter;
    private long interval;


    public MeasurePoint(float x, float y, float z, float speedBefore, long interval) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.speedBefore = speedBefore;
        this.interval = interval;
        speedAfter = 0;
        calc();
    }
public float getSpeedAfter()
{
    return speedAfter;
}

    private void calc(){
        //Acceleration as projection of current vector on average
        float acceleration = (float) Math.sqrt(this.x * this.x + this.y * this.y * +this.z * this.z);
        float t = ((float)interval / 1000f);
        speedAfter = speedBefore + acceleration * t;
        float distance = speedBefore * t + acceleration * t * t / 2;

    }
}
