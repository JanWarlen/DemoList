package com.janwarlen.staticdemo;
import static com.janwarlen.staticdemo.StaticImportDemo.*;


class InitDemo{
    protected InitDemo (String msg) {
        System.out.println(msg);
    }
}

public class StaticMemberDemo {

    static {
        InitDemo demo1 = new InitDemo("static block: demo 1");
    }

    /**
     * static member variable
     */
    public static final String version = "1.0";

    private InitDemo demo2 = new InitDemo("member variable:demo 2");

    private static InitDemo demo3 = new InitDemo("static member variable:demo 3");

    static {
        InitDemo demo4 = new InitDemo("static block: demo 4");
    }

    public static InitDemo demo5 = new InitDemo("static member variable:demo 5");

    public InitDemo getDemo2() {
        return demo2;
    }

    public void setDemo2(InitDemo demo2) {
        this.demo2 = demo2;
    }

    public static InitDemo getDemo3() {
        return demo3;
    }

    public static void setDemo3(InitDemo demo3) {
        StaticMemberDemo.demo3 = demo3;
    }

    public void staticImport() {
        sayHello();
    }

    public static void staticImportFunc() {
        sayHello();
    }
}
