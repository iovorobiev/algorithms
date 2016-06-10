package tests.org.iovorobiev

import junit.framework.TestCase
import org.iovorobiev.tree.RBTree
import org.iovorobiev.tree.SplayTree
import java.util.*

class SplayTreeTest : TestCase() {
    fun testTree() {
        val tree = SplayTree()
        val toInsert = intArrayOf(10, 15, 5, 7, 6, 8, 3, 1, 4, 13, 17, 11, 14, 16, 18)
        val result = intArrayOf(1, 3, 4, 5, 6, 7, 8, 10, 11, 13, 14, 15, 16, 17, 18)
        val resultDeleteFirst = intArrayOf(1, 3, 4, 5, 6, 7, 8, 10, 11, 13, 14, 15, 17, 18)
        val resultDeleteSecond = intArrayOf(1, 3, 5, 6, 7, 8, 10, 11, 13, 14, 15, 17, 18)
        for (i in toInsert) {
            tree.insert(i)
        }
        assertNotNull(tree.root)
        val listToFill = LinkedList<Int>()
        traverseTree(tree.root!!, listToFill)

        for (i in 0..result.size - 1) {
            assertEquals(result[i], listToFill[i])
        }

        tree.delete(16)
        listToFill.clear()
        traverseTree(tree.root!!, listToFill)
        assertEquals(resultDeleteFirst.size, listToFill.size)
        for (i in 0..resultDeleteFirst.size - 1) {
            assertEquals(resultDeleteFirst[i], listToFill[i])
        }

        tree.delete(4)
        listToFill.clear()
        traverseTree(tree.root!!, listToFill)
        assertEquals(resultDeleteSecond.size, listToFill.size)
        for (i in 0..resultDeleteSecond.size - 1) {
            assertEquals(resultDeleteSecond[i], listToFill[i])
        }
    }



    private fun traverseTree(root: SplayTree.SplayTreeNode, listToFill: MutableList<Int>) {
        root.left?.let{
            traverseTree(it, listToFill)
        }
        listToFill.add(root.key)
        root.right?.let {
            traverseTree(it, listToFill)
        }
    }
}