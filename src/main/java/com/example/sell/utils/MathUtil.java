package com.example.sell.utils;

public class MathUtil {

    private final static  double VALUE = 0.01;
    public static Boolean comepare(Double a,Double b){

        double v = Math.abs(a - b);
        if (v < VALUE){
            return true;
        }
        return false;
    }
}
