package com.janwarlen.strictfpdemo;

public strictfp class StrictfpClassDemo {

    public void add(float a, float b) {
        System.out.println(a + " + " + b + " =" + (a + b));
    }

    public void minus(float a, float b) {
        System.out.println(a + " - " + b + " =" + (a - b));
    }

    public void multiply(float a, float b) {
        System.out.println(a + " * " + b + " =" + (a * b));
    }

    public void divide(float a, float b) {
        System.out.println(a + " / " + b + " =" + (a / b));
    }
}
