package tests.org.iovorobiev

import junit.framework.TestCase
import org.iovorobiev.tree.RBTree

class RedBlackTreeTest : TestCase() {
    var blackDepth : Int = -1;

    fun testInsert() {
        val toInsert = intArrayOf(10, 15, 5, 7, 6, 8, 3, 1, 4, 13, 17, 11, 14, 16, 18)
        val root = RBTree();
        for (i in toInsert) {
            root.insert(i)
        }
        traverseTree(root.root, 0, 0)
    }

    fun testDelete() {
        val toInsert = intArrayOf(10, 15, 5, 7, 6, 8, 3, 1, 4, 13, 17, 11, 14, 16, 18)
        val root = RBTree();
        for (i in toInsert) {
            root.insert(i)
        }
        root.delete(3);
        root.delete(13)
        root.delete(10)
        traverseTree(root.root, 0, 0);
    }

    // Count black-path
    private fun traverseTree(root: RBTree.RBNode, level: Int, black: Int) {
        if (root.isColorRed) {
            if (root.left != null) {
                assertFalse(root.left.isColorRed)
            }
            if (root.right != null) {
                assertFalse(root.right.isColorRed)
            }

        }
        val currentBlack = black + root.colorInt;
        if (root.left == null && root.right == null) {
            if (blackDepth == -1) {
                blackDepth = currentBlack;
            } else {
                assertEquals(blackDepth, currentBlack)
            }
        }
        for (i in 0..level - 1) {
            print("  ");
        }
        println("Color ${root.colorString} Key ${root.key} Parent ${root.parent?.key}");
        for (i in 0..level - 1) {
            print("  ");
        }
        println("{")
        if (root.left != null) {
            traverseTree(root.left, level + 1, currentBlack)
        }
        if (root.right != null) {
            traverseTree(root.right, level + 1, currentBlack)
        }
        for (i in 0..level - 1) {
            print("  ");
        }
        println("}")
    }
}