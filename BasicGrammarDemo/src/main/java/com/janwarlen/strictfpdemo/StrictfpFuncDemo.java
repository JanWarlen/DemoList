package com.janwarlen.strictfpdemo;

public class StrictfpFuncDemo {

    public static strictfp void add(float a, float b) {
        System.out.println(a + " + " + b + " =" + (a + b));
    }

    public static strictfp void minus(float a, float b) {
        System.out.println(a + " - " + b + " =" + (a - b));
    }

    public static strictfp void multiply(float a, float b) {
        System.out.println(a + " * " + b + " =" + (a * b));
    }

    public static strictfp void divide(float a, float b) {
        System.out.println(a + " / " + b + " =" + (a / b));
    }



}
