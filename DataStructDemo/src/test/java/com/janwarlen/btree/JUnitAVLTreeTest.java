package com.janwarlen.btree;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Scanner;

public class JUnitAVLTreeTest {

    private static AVLTree<Integer> avlTree;

    @BeforeClass
    public static void initTree() {
        avlTree = new AVLTree<Integer>();
    }

    @Test
    public void testInsert() {
        for (int i = 1; i < 9; i++) {
            avlTree.root = avlTree.insert(i, avlTree.root);
        }
        avlTree.printTree(avlTree.root);
    }

    @Test
    public void testRemove() {
        for (int i = 0; i < 9; i++) {
            avlTree.root = avlTree.insert(i, avlTree.root);
        }

        //实验验证删除的两种方式的结果皆满足高度要求
        avlTree.remove(3,avlTree.root);
        avlTree.printTree(avlTree.root);
    }

}
