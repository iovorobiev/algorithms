package org.iovorobiev.tree;

import java.util.LinkedList;

public class BinaryTreeNode {
    private int key;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    private BinaryTreeNode(BinaryTreeNode node) {
        this.key = node.key;
        this.left = node.left;
        this.right = node.right;
    }

    public BinaryTreeNode(int key) {
        this.key = key;
    }

    public void insert(int key) {
        if (key > this.key) {
            if (right == null) {
                right = new BinaryTreeNode(key);
                return;
            }
            right.insert(key);
            return;
        }
        if (key < this.key) {
            if (left == null) {
                left = new BinaryTreeNode(key);
                return;
            }
            left.insert(key);
        }
    }

    public void delete(int key) {
        if (key == this.key) {
            rebuildTree();
            return;
        }
        if (key > this.key) {
            if (right == null) {
                return;
            }
            right.delete(key);
            return;
        }
        if (key < this.key) {
            if (left == null) {
                return;
            }
            left.delete(key);
        }
    }

    public boolean contains(int key) {
        if (key == this.key) {
            return true;
        }
        if (key > this.key && right != null) {
            return right.contains(key);
        } else if (key < this.key && left != null) {
            return left.contains(key);
        }
        return false;
    }

    public void inOrderTraversal(LinkedList<Integer> toFill) {
        if (left != null) {
            left.inOrderTraversal(toFill);
        }
        toFill.add(key);
        if (right != null) {
            right.inOrderTraversal(toFill);
        }
    }

    private void rebuildTree() {
        BinaryTreeNode leftChild = new BinaryTreeNode(left);
        key = right.key;
        left = right.left;
        right = right.right;
        BinaryTreeNode lastLeft = this;
        while (lastLeft.left != null) {
            lastLeft = lastLeft.left;
        }
        lastLeft.left = leftChild;
    }
}
