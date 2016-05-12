package org.iovorobiev;

import javafx.util.Pair;
import org.iovorobiev.hashing.PerfectHashMap;
import org.iovorobiev.hashing.UniversalHashMap;
import org.iovorobiev.heap.Heap;
import org.iovorobiev.qsort.MedianLambdaProvider;
import org.iovorobiev.merge.MergeSort;
import org.iovorobiev.qsort.QSort;

public class Main {

    final static int TEST_NUM = 1000;

    public static void main(String[] args) {
        universalHashMap();
    }

    private static void universalHashMap() {
        UniversalHashMap hashMap = new UniversalHashMap();
        short[] keys = new short[322];

        for (int i = 0; i < 322; i++) {
            keys[i] = (short) (Math.random() * Short.MAX_VALUE * 2 - Short.MAX_VALUE);
            String value = Short.toString(keys[i]);
            hashMap.put(keys[i], value);
        }
        hashMap.printContent();
    }

    private static void makeTests() {
        for (int i = 0; i < TEST_NUM; i++) {
            if (!heapSort()) {
                return;
            }
        }
        System.out.println("Tests passed");
    }

    private static boolean heapSort() {
        Heap heap = new Heap();
        int[] array = new int[3200];
        Utils.initRandomArray(array, false);
        heap.makeHeap(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.extractMin();
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                System.out.println("FAILED!");
                return false;
            }
        }
        return true;
    }

    private static boolean mergeSort() {
        int[] array = new int[8182];
        Utils.initRandomArray(array, false);
        new MergeSort(array);
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                System.out.println("FAILED!");
                return false;
            }
        }
        return true;
    }

    private static boolean qsort() {
        int[] array = new int[4096];//{809, 273, 273, 88};
        Utils.initRandomArray(array);

        System.out.println();
        new QSort(array, new MedianLambdaProvider());
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                System.out.println("FAILED!");
                return false;
            }
        }
        System.out.println("All is correct");
        return true;
    }


}
