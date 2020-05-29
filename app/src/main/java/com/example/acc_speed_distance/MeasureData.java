package com.example.acc_speed_distance;

import java.util.LinkedList;

public class MeasureData {
    private LinkedList <Point> accData;
    private LinkedList <MeasurePoint> data;
    // timer interval of generating points
    private long interval;

    public MeasureData(long interval) {
        this.interval = interval;
        accData = new LinkedList ();
        data = new LinkedList ();
    }

    public void addPoint(Point p){
        accData.add(p);
    }

    public void process(){

        for(int i = 0; i < accData.size(); ++i){
            Point p = accData.get(i);
            float speed = 0;

            if(i > 0){
                speed = data.get(i-1).getSpeedAfter();
            }
            data.add(new MeasurePoint(p.getX(), p.getY(), p.getZ(), speed, interval));
        }
    }

    public float getLastSpeed(){
        return data.getLast().getSpeedAfter();
    }

    public float getLastSpeedKm(){
        float ms = getLastSpeed();
        return ms*3.6f;
    }
}
