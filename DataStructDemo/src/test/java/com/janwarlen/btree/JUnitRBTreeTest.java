package com.janwarlen.btree;

import org.junit.Test;

import java.util.Collections;

public class JUnitRBTreeTest {

    @Test
    public void testInsert() {
        RBTree<Integer> test = new RBTree<Integer>();
        int[] arr = {1,5,2,7,4,6,9};//,3
//        int[] arr = {23,44,75,14,66,99,37,52,84};
        for (int i = 0; i < arr.length; i++) {
            test.insert(arr[i]);
        }
        System.out.println(test.toString());
    }

    @Test
    public void testRemove() {
        int[] arr = {23,44,75,14,66,99,37,84};
        RBTree<Integer> test = new RBTree<Integer>();
        for (int i = 0; i < arr.length; i++) {
            test.insert(arr[i]);
        }
        test.remove(44);
        System.out.println(test.toString());
    }

}
