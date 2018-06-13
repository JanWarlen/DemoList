package com.janwarlen.btree;

import org.junit.Test;

public class JUnitRBTreeTest {

    @Test
    public void testInsert() {
        RBTree<Integer> test = new RBTree<Integer>();
        for (int i = 1; i < 9; i++) {
            test.insert(i);
        }
    }

    @Test
    public void testRemove() {
        RBTree<Integer> test = new RBTree<Integer>();
        for (int i = 0; i < 9; i++) {
            test.insert(i);
        }
        test.remove(6);
    }

}
