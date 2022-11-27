package org.sgx.sc.game.math;

public class TimeUtil {
    public static double smooth(double a, double b, double smoothness) {
        return (b - a) / smoothness;
    }
}