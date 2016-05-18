package org.iovorobiev.merge;

public class MergeSort {

    public MergeSort(int[] array, boolean lowMemory) {
        int size = 1;
        while (size < array.length) {
            for (int i = 0; i < array.length; i += 2 * size) {
                if (lowMemory) {
                    mergeLowMemory(array, i, i + size - 1, i + size, i + 2 * size - 1);
                } else {
                    merge(array, i, i + size - 1, i + size, i + 2 * size - 1);
                }
            }
            size *= 2;
        }
    }

    private void merge(int[] array, int iStart, int iEnd, int jStart, int jEnd) {
        if (jStart > array.length - 1) {
            return;
        }
        if (jEnd > array.length - 1) {
            jEnd = array.length - 1;
        }
        int firstLength = iEnd - iStart;
        int secondLength = jEnd - jStart;
        int[] merged = new int[firstLength + secondLength + 2];
        int firstIter = iStart;
        int secondIter = jStart;
        for (int i = 0; i < merged.length; i++) {
            if (firstIter > (iStart + firstLength)) {
                merged[i] = array[secondIter];
                secondIter++;
            } else if (secondIter > (jStart + secondLength)) {
                merged[i] = array[firstIter];
                firstIter++;
            } else if (array[firstIter] > array[secondIter]) {
                merged[i] = array[secondIter];
                secondIter++;
            } else if (array[firstIter] <= array[secondIter]){
                merged[i] = array[firstIter];
                firstIter++;
            }
        }
        System.arraycopy(merged, 0, array, iStart, merged.length);
    }

    private void mergeLowMemory(int[] array, int iStart, int iEnd, int jStart, int jEnd) {
        if (jStart > array.length - 1) {
            return;
        }
        if (jEnd > array.length - 1) {
            jEnd = array.length - 1;
        }

        int firstLength = iEnd - iStart;
        int secondLength = jEnd - jStart;
        int fullLength = firstLength + secondLength + 2;
        int[] merged = new int[firstLength + 1];
        System.arraycopy(array, iStart, merged, 0, firstLength + 1);
        int firstIter = 0;
        int secondIter = jStart;
        for (int i = 0; i < fullLength; i++) {
            if (firstIter > firstLength) {
                array[iStart + i] = array[secondIter];
                secondIter++;
            } else if (secondIter > jStart + secondLength) {
                array[iStart + i] = merged[firstIter];
                firstIter++;
            } else if (merged[firstIter] > array[secondIter]) {
                array[iStart + i] = array[secondIter];
                secondIter++;
            } else if (merged[firstIter] <= array[secondIter]) {
                array[iStart + i] = merged[firstIter];
                firstIter++;
            }
        }
    }
}
