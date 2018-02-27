package com.janwarlen.staticdemo;

import org.junit.Test;

public class JUnitStaticTest {

    @Test
    public void testStatic() {
        StaticMemberDemo test = new StaticMemberDemo();
        test.staticImport();
        StaticMemberDemo.staticImportFunc();
    }



}
