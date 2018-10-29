package com.janwarlen.btree;

import java.util.ArrayList;
import java.util.List;

public class AVLTree<T> {

    Node<T> root;

    private static class Node<T> {

        public Node(T element) {
            this(element, null, null);
        }

        public Node(T element, Node<T> l, Node<T> r) {
            this.element = element;
            this.left = l;
            this.right = r;
            this.height = 0;
        }

        T element;
        Node<T> left;
        Node<T> right;
        int height;
    }

    public Node<T> insert(T element, Node<T> ele) {
        if (null == ele) {
            return new Node<T>(element);
        }
        Comparable<? super T> comparableEle = (Comparable<? super T>) element;
        int compareRes = comparableEle.compareTo(ele.element);
        if (compareRes < 0) {
            //left
            ele.left = insert(element, ele.left);
        } else if (compareRes > 0) {
            //right
            ele.right = insert(element, ele.right);
        } else {
            //duplicate
        }

        return balance(ele);
    }

    public Node<T> remove(T element, Node<T> ele) {
        if (null == ele) {
            return ele;
        }

        Comparable<? super T> comparableEle = (Comparable<? super T>) element;
        int compareRes = comparableEle.compareTo(ele.element);
        if (compareRes < 0) {
            ele.left = remove(element, ele.left);
        } else if (compareRes > 0) {
            ele.right = remove(element, ele.right);
        } else if (null != ele.left && null != ele.right) {
                ele.element = findMax(ele.left).element;
                ele.left = remove(ele.element, ele.left);
            //此处理论从左子树找最大和右子树找最小并无明显区别
//            ele.element = findMin(ele.right).element;
//            ele.right = remove(ele.element, ele.right);
        } else {
            ele = (null != ele.left) ? ele.left : ele.right;
        }

        return balance(ele);
    }

    private Node<T> findMin(Node<T> ele) {
        if (null == ele) {
            return null;
        } else if (null == ele.left) {
            return ele;
        }

        return findMin(ele.left);
    }

    private Node<T> findMax(Node<T> ele) {
        if (null == ele) {
            return null;
        } else if (null == ele.right) {
            return ele;
        }

        return findMax(ele.right);
    }

    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * @param ele 待平衡节点
     * @return 该节点位置指针
     */
    private Node<T> balance(Node<T> ele) {
        if (null == ele) {
            return ele;
        }

        //左子树高于右子树
        if (height(ele.left) - height(ele.right) > ALLOWED_IMBALANCE) {
            //判断需要单旋转还是双旋转
            if (height(ele.left.left) >= height(ele.left.right)) {
                //左单旋转
                ele = rotateWithLeftChild(ele);
            } else {
                //左双旋转
                ele = doubleWithLeftChild(ele);
            }
        }

        //右子树高于左子树
        if (height(ele.right) - height(ele.left) > ALLOWED_IMBALANCE) {
            if (height(ele.right.right) >= height(ele.right.left)) {
                //右单旋转
                ele = rotateWithRightChild(ele);
            } else {
                //右双旋转
                ele = doubleWithRightChild(ele);
            }
        }


        //重新计算节点高度
        ele.height = Math.max(height(ele.left), height(ele.right)) + 1;

        return ele;
    }

    //右双旋转
    private Node<T> doubleWithRightChild(Node<T> ele) {
        ele.right = rotateWithLeftChild(ele.right);
        return rotateWithRightChild(ele);
    }

    //左双旋转
    private Node<T> doubleWithLeftChild(Node<T> ele) {
        ele.left = rotateWithRightChild(ele.left);
        return rotateWithLeftChild(ele);
    }

    //右单旋转
    private Node<T> rotateWithRightChild(Node<T> ele) {
        Node<T> right = ele.right;
        ele.right = right.left;
        right.left = ele;
        ele.height = Math.max(height(ele.left), height(ele.right)) + 1;
        right.height = Math.max(height(right.left), height(right.right)) + 1;
        return right;
    }

    //左单旋转
    private Node<T> rotateWithLeftChild(Node<T> ele) {
        Node<T> left = ele.left;
        ele.left = left.right;
        left.right = ele;
        ele.height = Math.max(height(ele.left), height(ele.right)) + 1;
        left.height = Math.max(height(left.left), height(left.right)) + 1;
        return left;
    }

    public int height(Node<T> ele) {
        return ele == null ? -1 : ele.height;
    }

    public void printTree(Node<T> ele) {
        if (null == ele) {
            return;
        }

        List<Node<T>> preLevel = new ArrayList<Node<T>>();
        List<Node<T>> curLevel = new ArrayList<Node<T>>();
        curLevel.add(ele);
        while (!curLevel.isEmpty()) {
            for (Node<T> item : curLevel) {
                System.out.print(item.element + "\t");
            }
            System.out.println("");
            preLevel.clear();
            preLevel.addAll(curLevel);
            curLevel.clear();

            for (Node<T> item : preLevel) {
                if (null != item.left)
                    curLevel.add(item.left);
                if (null != item.right)
                    curLevel.add(item.right);
            }
        }

    }
}
