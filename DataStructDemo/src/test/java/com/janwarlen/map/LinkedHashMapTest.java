package com.janwarlen.map;

import org.junit.Test;

import java.util.LinkedHashMap;

public class LinkedHashMapTest {

    @Test
    public void testCreate() {
        LinkedHashMap<Integer, Integer> test = new LinkedHashMap();
        test.put(new Integer(1), new Integer(1));
    }
}
