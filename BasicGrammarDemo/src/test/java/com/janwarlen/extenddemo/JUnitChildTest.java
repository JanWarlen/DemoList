package com.janwarlen.extenddemo;

import org.junit.Test;

/**
 * @Auther: janwarlen
 * @Date: 2019/1/10 17:27
 * @Description:
 */
public class JUnitChildTest {

    /**
     * 测试最终子类向上转型后，能否被识别为同级别的其他类型
     */
    @Test
    public void testChildConvertToGrandPaInstanceOfMaternalGrandPa() {
        Grandpa test = new Child();
        System.out.println("test mother：" + (test instanceof Mother));
        System.out.println("test grandpa：" + (test instanceof Grandpa));
        System.out.println("test maternal grandpa：" + (test instanceof MaternalGrandPa));
    }
}
