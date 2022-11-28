package org.sgx.sc.engine.time;

public class Time {
    private double lastTime;
    private final double startTime;
    private double time;
    private double delta;

    public Time() {
        lastTime = System.nanoTime();
        startTime = System.nanoTime();
        delta = 0;
    }

    public void update() {
        double currentTime = System.nanoTime();
        delta = (currentTime - lastTime) / 1000000000.0;
        if(delta >= 0.15) delta = 0.0;
        lastTime = currentTime;
        time = (System.nanoTime() - startTime) / 1000000000.0;
    }

    public double getDelta() { return delta; }
    public double getTime() { return time; }
}