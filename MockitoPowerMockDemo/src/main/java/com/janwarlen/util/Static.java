package com.janwarlen.util;

/**
 * @Auther: janwarlen
 * @Date: 2018/12/25 15:07
 * @Description: 静态方法类
 */
public class Static {
    public static String staticMethod() {
        return "static";
    }

    public static String staticWithParam(String param) {
        return param + "_";
    }

    public static void staticVoid() {
        System.out.println("staticVoid");
    }

    public static void staticVoidWithParam(String param) {
        System.out.println(staticWithParam(param));
    }
}
