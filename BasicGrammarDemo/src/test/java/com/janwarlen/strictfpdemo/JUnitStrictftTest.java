package com.janwarlen.strictfpdemo;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 浮点运算更加精确，而且不会因为不同的硬件平台所执行的结果不一致的话，可以用关键字strictfp.
 * ----------------------------------------------------------------------------------
 * 通常处理器都各自实现浮点运算，各自专业浮点处理器为实现最高速，计算结果会和IEEE标准有细小差别。
 * 比如intel主流芯片的浮点运算，内部是80bit高精运算，只输出64bit的结果。IEEE只要求64bit精度的计算，你更精确反而导致结果不一样。
 * 所以设立‘严格浮点计算strictfp’，保证在各平台间结果一致，IEEE标准优先，性能其次；
 * 而非严格的浮点计算是“性能优先”，标准其次。
 * ----------------------------------------------------------------------------------
 * https://baike.baidu.com/item/IEEE%20754/3869922?fr=aladdin
 */
public class JUnitStrictftTest {

    private static StrictfpClassDemo strictfpClassDemo;

    @BeforeClass
    public static void initArgs() {
        strictfpClassDemo = new StrictfpClassDemo();
    }

    @Test
    public void testAdd() {
        float a = 1.0236f;
        float b = 2.0764f;
        strictfpClassDemo.add(a,b);
        StrictfpFuncDemo.add(a,b);
        System.out.println(a + " + " + b + " =" + (a + b));
    }

    @Test
    public void testMinus() {
        float a = 2.123456f;
        float b = 0.98765f;
        strictfpClassDemo.minus(a,b);
        StrictfpFuncDemo.minus(a,b);
        System.out.println(a + " - " + b + " =" + (a - b));
    }

    @Test
    public void testMultiply() {
        float a = 1.234f;
        float b = 2.567f;
        strictfpClassDemo.multiply(a,b);
        StrictfpFuncDemo.multiply(a,b);
        System.out.println(a + " * " + b + " =" + (a * b));
    }

    @Test
    public void testDivide() {
        float a = 9.845612356f;
        float b = 3.2435f;
        strictfpClassDemo.divide(a,b);
        StrictfpFuncDemo.divide(a,b);
        System.out.println(a + " / " + b + " =" + (a / b));
    }

}
