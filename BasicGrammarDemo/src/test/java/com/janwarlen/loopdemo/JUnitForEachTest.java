package com.janwarlen.loopdemo;

import org.junit.Test;

public class JUnitForEachTest {

    @Test
    public void testAssignmentString() {
        ForEachDemo.assignmentString();
    }

    @Test
    public void testAssignmentInteger() {
        ForEachDemo.assignmentInteger();
    }

    /**
     * 当使用foreach循环基本类型时变量时不能修改集合中的元素的值，遍历对象时可以修改对象的属性的值，但是不能修改对象的引用
     */
    @Test
    public void testAssignmentPerson() {
        ForEachDemo.assignmentPerson();
    }
}
