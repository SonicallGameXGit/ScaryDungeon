package org.sgx.sc.game.math;

public class TimeUtil {
    public static double smooth(double a, double b, double smoothness) {
        return smoothness == 0.0 ? a : (b - a) / smoothness;
    }
}