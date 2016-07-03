package tests.org.iovorobiev

import junit.framework.Assert
import junit.framework.TestCase
import org.iovorobiev.tree.BTree
import org.iovorobiev.tree.BTreeNode
import java.util.*

class BTreeTest : TestCase() {
    fun testInserBTree() {
        val tree: BTree = BTree(sizeOfBlock = 4)
        tree.insert(5)
        tree.insert(6)
        tree.insert(7)
        tree.insert(12)
        tree.insert(4)
        tree.insert(14)
        tree.insert(8)
        tree.insert(9)
        tree.delete(8)
        tree.delete(9)
        tree.delete(14)
        tree.delete(12)
        val result = LinkedList<Int>()
        checkTree(tree.root!!, result)
        val rightResult = intArrayOf(4,5,6,7)
        assertEquals(rightResult.size, result.size)
        for (i in 0..rightResult.lastIndex) {
            assertEquals(rightResult[i], result[i])
        }
    }

    fun checkTree(node: BTreeNode, result: LinkedList<Int>) {
        for(key in node.keys) {
            result.add(key)
        }
        node.children?.let {
            for (child in it) {
                checkTree(child, result)
            }
        }
    }
}