package com.janwarlen.btree;

import java.util.TreeMap;

public class RBTree<T extends  Comparable<? super T>> {

    private static boolean RED = true;
    private static boolean BLACK = false;

    private Node<T> root;

    private static class Node<T> {
        T element;
        boolean color;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        public Node(T element) {
            this(element, true, null, null, null);
        }

        public Node(T element, boolean color, Node<T> l, Node<T> r, Node<T> p) {
            this.element = element;
            this.color = color;
            this.left = l;
            this.right = r;
            this.parent = p;
        }
    }

    public void insert(T element) {
        if (null == this.root) {
            this.root = new Node<T>(element);
            this.root.color = BLACK;
            return;
        }

        Comparable<? super T> comparableEle = (Comparable<? super T>) element;
        int compareRes;
        Node<T> t = this.root;
        Node<T> parent = t;
        do {
            parent = t;
            compareRes = comparableEle.compareTo(t.element);
            if (compareRes > 0) {
                t = t.right;
            } else if (compareRes < 0) {
                t = t.left;
            } else {
                // duplicate
                return;
            }

        } while (null != t);

        compareRes = comparableEle.compareTo(parent.element);
        Node<T> e = new Node<T>(element);
        if (compareRes > 0) {
            parent.right = e;
        } else if (compareRes < 0) {
            parent.left = e;
        }
        e.color = RED;
        e.parent = parent;

        // 插入完成，进行修复
        fixAfterInsert(e);
    }

    public void remove(T element) {
        Node<T> removeNode = getNode(element);
        remove(removeNode);
    }

    // 删除节点
    private void remove(Node<T> d) {
        if (d.left != null && d.right != null) {
            // 左右节点皆不为空，将待删除节点元素赋值为后继节点元素
            // 注意，此时未带来节点颜色
            Node<T> s = successor(d);
            d.element = s.element;
            // 待删除节点指针指向后继节点
            d = s;
        }

        Node<T> replacement = (d.left != null ? d.left : d.right);

        if (replacement != null) {
            // 左右节点至少有一个节点不为空
            replacement.parent = d.parent;
            if (d.parent == null) {
                // 树仅有两个节点,d为根结点
                root = replacement;
            } else if (d == d.parent.left) {
                // 待删除节点为父节点的左节点
                d.parent.left = replacement;
            } else {
                // 待删除节点为父节点的右节点
                d.parent.right = replacement;
            }

            // 断开待删除节点的所有链接
            // 当待删除节点左右节点皆不为空时，此时指向后继节点
            d.left = d.right = d.parent = null;

            // Fix replacement
            // 当待删除节点为红色时，无需调整节点颜色
            if (d.color == BLACK) {
                // 此时原节点已被删除，replacement是替换后节点
                fixAfterDeletion(replacement);
            }
        } else if (null == d.parent) {
            // 当前树仅有根结点
            root = null;
        } else {
            if (d.color == BLACK)
                // 待删除节点为叶子节点
                fixAfterDeletion(d);

            if (d.parent != null) {
                // 针对删除节点为叶子节点情况
                if (d == d.parent.left)
                    d.parent.left = null;
                else if (d == d.parent.right)
                    d.parent.right = null;
                d.parent = null;
            }
        }

    }

    // 待删除节点已被删除替换后，进行颜色修复
    private void fixAfterDeletion(Node<T> x) {
        //  只有节点为黑色才需要修复
        while (x != root && colorOf(x) == BLACK) {
            // 左节点
            if (x == leftOf(parentOf(x))) {
                Node<T> sib = rightOf(parentOf(x));
                // 兄弟节点为红色，自己是黑色,设定为情况1
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib))  == BLACK &&
                        colorOf(rightOf(sib)) == BLACK) {
                    // 兄弟节点的子节点都为黑色,设定为情况2
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        // 兄弟节点的子节点只有右节点为黑色，设定为情况3
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    // 兄弟节点的右节点为红色,设定为情况4
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else { // symmetric
                // 镜像对称
                Node<T> sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                        colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }

    // 找到后继节点
    private Node<T> successor(Node<T> e) {
        if (null != e && null != e.right) {
            e = e.right;
            while (null != e.left) {
                e = e.left;
            }
            return e;
        }

        return null;
    }

    // 根据元素找到对应节点
    private Node<T> getNode(T element) {
        Comparable<? super T> comparableEle = (Comparable<? super T>) element;
        int compareRes;
        Node<T> cmp = root;
        while (null != cmp) {
            compareRes = comparableEle.compareTo(cmp.element);
            if (compareRes > 0) {
                cmp = cmp.right;
            } else if (compareRes < 0) {
                cmp = cmp.left;
            } else {
                return cmp;
            }
        }

        return null;
    }

    private void fixAfterInsert(Node<T> e) {
        // 父节点为红色才进行处理
        while (null != e && root != e && RED == e.parent.color) {
            if (parentOf(e) == leftOf(parentOf(e))) {
                // 父节点为祖节点的左节点
                Node<T> u = rightOf(parentOf(parentOf(e)));
                if (RED == colorOf(u)) {
                    // 叔节点为红色 wiki：case 3
                    setColor(parentOf(e), BLACK);
                    setColor(u, BLACK);
                    setColor(parentOf(parentOf(e)), BLACK);
                    e = parentOf(parentOf(e));
                } else {
                    // 叔节点为黑色
                    if (e == rightOf(parentOf(e))) {
                        // wiki：case 4
                        e = parentOf(e);
                        rotateLeft(e);
                    }
                    // wiki : case 5
                    setColor(parentOf(e), BLACK);
                    setColor(parentOf(parentOf(e)), RED);
                    rotateRight(parentOf(parentOf(e)));
                }
            } else {
                // 父节点为祖节点的右节点
                Node<T> u = leftOf(parentOf(parentOf(e)));
                if (RED == colorOf(u)) {
                    // 叔节点为红色 wiki：case 3
                    setColor(parentOf(e), BLACK);
                    setColor(u, BLACK);
                    setColor(parentOf(parentOf(e)), BLACK);
                    e = parentOf(parentOf(e));
                } else {
                    // 叔节点为黑色
                    if (e == leftOf(parentOf(e))) {
                        // wiki：case 4
                        e = parentOf(e);
                        rotateRight(e);
                    }
                    // wiki : case 5
                    setColor(parentOf(e), BLACK);
                    setColor(parentOf(parentOf(e)), RED);
                    rotateLeft(parentOf(parentOf(e)));
                }
            }
        }
    }

    // 右旋转（方向）
    private void rotateRight(Node<T> p) {
        if (p != null) {
            Node<T> l = p.left;
            p.left = l.right;
            if (l.right != null) l.right.parent = p;
            l.parent = p.parent;
            if (p.parent == null)
                root = l;
            else if (p.parent.right == p)
                p.parent.right = l;
            else p.parent.left = l;
            l.right = p;
            p.parent = l;
        }
    }

    // 左旋转（方向）
    private void rotateLeft(Node<T> e) {
        if (null != e) {
            Node<T> p = e.parent;
            Node<T> r = p.right;
            p.right = r.left;
            if (null != r.left) {
                r.left.parent = p;
            }
            r.parent = p.parent;
            if (null == p.parent) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            r.left = p;
            p.parent = r;
        }
    }

    private void setColor(Node<T> e, boolean color) {
        e.color = color;
    }

    // 获取该节点的颜色
    private boolean colorOf(Node<T> e) {
        return (null == e ? BLACK : e.color);
    }

    // 获取该结点的右子节点
    private Node<T> rightOf(Node<T> e) {
        if (null == e || null == e.right) {
            return null;
        }

        return e.right;
    }

    // 获取该结点的左子节点
    private Node<T> leftOf(Node<T> e) {
        if (null == e || null == e.left) {
            return null;
        }

        return e.left;
    }

    // 获取该结点的父节点
    private Node<T> parentOf(Node<T> e) {
        if (null == e || null == e.parent) {
            return null;
        }

        return e.parent;
    }
}
