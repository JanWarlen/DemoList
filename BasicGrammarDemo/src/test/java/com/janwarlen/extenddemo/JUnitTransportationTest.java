package com.janwarlen.extenddemo;

import com.janwarlen.extenddemo.Transportation.Car;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @ClassName: JUnitTransportationTest
 * @author: janwarlen
 * @Date: 2019/1/30 17:50
 * @Description: 测试交通工具
 */
public class JUnitTransportationTest {

    @Test
    public void testExtendAttributes() {
        Car car = new Car();
        car.setColor("red");
        car.setWeight(new BigDecimal(1));
        Bus bus = new Bus();
        bus.setColor("blue");
        bus.setWeight(new BigDecimal(10));
    }
}
