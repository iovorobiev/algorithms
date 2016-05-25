package tests.org.iovorobiev

import junit.framework.TestCase
import org.iovorobiev.tree.BinaryTreeNode
import java.util.*

class BinaryTreeTest : TestCase() {

    private val array: IntArray = intArrayOf(10, 15, 5, 7, 6, 8, 3, 1, 4, 13, 17, 11, 14, 16, 18)

    fun testInsertBinaryTree() {
        val result = intArrayOf(1, 3, 4, 5, 6, 7, 8, 10, 11, 13, 14, 15, 16, 17, 18)
        val node = BinaryTreeNode(array[0])
        fillTree(node)
        val randomIndex = (Math.random() * (array.size - 1)).toInt()
        assertTrue(node.contains(array[randomIndex]))
        assertFalse(node.contains(32))
        val inOrder = LinkedList<Int>()
        node.inOrderTraversal(inOrder)
        for (i in inOrder.indices) {
            assertEquals(result[i], inOrder[i])
        }
    }

    private fun fillTree(node : BinaryTreeNode) {
        for (i in 1..array.size - 1) {
            node.insert(array[i])
        }
    }

    fun testDeleteBinaryTree() {
        val result = intArrayOf(1, 3, 4, 6, 7, 8, 11, 14, 15, 16, 17, 18)
        val node = BinaryTreeNode(array[0])
        fillTree(node)
        node.delete(5)
        node.delete(13)
        node.delete(10)
        val inOrder = LinkedList<Int>()
        node.inOrderTraversal(inOrder)
        for (i in inOrder.indices) {
            assertEquals(result[i], inOrder[i])
        }
    }
}