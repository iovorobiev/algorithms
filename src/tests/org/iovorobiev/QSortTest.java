package tests.org.iovorobiev;

import junit.framework.TestCase;
import org.iovorobiev.Utils;
import org.iovorobiev.qsort.MedianLambdaProvider;
import org.iovorobiev.qsort.QSort;
import org.iovorobiev.qsort.RandomLambdaProvider;

public class QSortTest extends TestCase {
    private int[] array = new int[4096];

    public void testMedianSort() {
        Utils.initRandomArray(array, false);
        new QSort(array, new MedianLambdaProvider());
        for (int i = 1; i < array.length; i++) {
            assertTrue(array[i] >= array[i - 1]);
        }
    }

    public void testRandomSort() {
        Utils.initRandomArray(array, false);
        new QSort(array, new RandomLambdaProvider());
        for (int i = 1; i < array.length; i++) {
            assertTrue(array[i] >= array[i - 1]);
        }
    }

}
