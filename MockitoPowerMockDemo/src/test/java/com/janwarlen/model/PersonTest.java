package com.janwarlen.model;

import com.janwarlen.BasedTest;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

/**
 * @ClassName: PersonTest
 * @author: janwarlen
 * @Date: 2019/2/1 11:18
 * @Description:
 */
public class PersonTest extends BasedTest {

    @Test
    public void testMockObject() {
        System.out.println("----------------------------------------");
        Person mockPersonObject = PowerMockito.mock(Person.class);
        mockPersonObject.setName("li lei");
        mockPersonObject.setAge(12);
        System.out.println(mockPersonObject.getName() + "|" + mockPersonObject.getAge());
        System.out.println("-");
        String han_meimei = mockPersonObject.sayHi("han meimei");
        System.out.println(han_meimei);
        PowerMockito.when(mockPersonObject.sayHi("test")).thenReturn("hi mockito!");
        System.out.println(mockPersonObject.sayHi("test"));
        System.out.println("----------------------------------------");
    }

    @Test
    public void testSpyObject() {
        System.out.println("----------------------------------------");
        Person spyPersonObject = PowerMockito.spy(new Person());
        spyPersonObject.setName("li lei");
        spyPersonObject.setAge(12);
        String han_meimei = spyPersonObject.sayHi("han meimei");
        System.out.println(han_meimei);
        System.out.println("-");
        PowerMockito.when(spyPersonObject.sayHi("test")).thenReturn("hi mockito!");
        System.out.println(spyPersonObject.sayHi("test"));
        System.out.println("-");
        PowerMockito.doReturn("do hi mockito!").when(spyPersonObject).sayHi("do test");
        System.out.println(spyPersonObject.sayHi("do test"));
        System.out.println("----------------------------------------");
    }
}
