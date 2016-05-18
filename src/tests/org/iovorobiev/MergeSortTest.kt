package tests.org.iovorobiev

import junit.framework.TestCase
import org.iovorobiev.Utils
import org.iovorobiev.merge.MergeSort

class MergeSortTest : TestCase() {
    fun testMergeSort() {
        val array = IntArray(8182)
        Utils.initRandomArray(array, false)
        MergeSort(array, false)
        for (i in 1..array.size - 1) {
            assertTrue(array[i] >= array[i - 1]);
        }
    }

    fun testMergeSortLowMemory() {
        val array = IntArray(8182)
        Utils.initRandomArray(array, false)
        MergeSort(array, false)
        for (i in 1..array.size - 1) {
            assertTrue(array[i] >= array[i - 1]);
        }
    }
}