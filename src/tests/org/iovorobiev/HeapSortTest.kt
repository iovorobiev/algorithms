package tests.org.iovorobiev

import junit.framework.TestCase
import org.iovorobiev.Utils
import org.iovorobiev.heap.Heap

class HeapSortTest : TestCase() {

    fun testHeapSort() {
        val heap = Heap()
        val array = IntArray(8182)
        Utils.initRandomArray(array, false)
        heap.makeHeap(array)
        for (i in 0..array.size - 1) {
            array[i] = heap.extractMin()
        }
        for (i in 1..array.size - 1) {
            assertTrue(array[i] >= array[i-1])
        }
    }
}