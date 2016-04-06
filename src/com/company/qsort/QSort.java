package com.company.qsort;

import com.company.Utils;

public class QSort {

    ILambdaProvider provider;

    public QSort(int[] array, ILambdaProvider provider) {
        this.provider = provider;
        sort(array, 0, array.length - 1);
    }

    public void sort(int[] array, int start, int finish) {
        if (start >= finish) {
            return;
        }
        int lambda = getLambda(array, start, finish);
        int centerIndex = partition(array, lambda, start, finish);
        //Left part is more then right
        if (centerIndex - start > (finish - start) / 2) {
            sort(array, centerIndex, finish);
            sort(array, start, centerIndex - 1);
        } else {
            sort(array, start, centerIndex - 1);
            sort(array, centerIndex, finish);
        }
    }

    private int getLambda(int[] array, int start, int finish) {
        return provider.getLambda(array, start, finish);
    }

    private int partition(int[] array, int lambda, int start, int finish) {
        int i = start;
        int j = finish;
        while (i < j) {
            if (array[i] >= lambda && array[j] <= lambda) {
                Utils.swap(array, i, j);
                i++;
                j--;
                continue;
            }
            if (array[i] < lambda) {
                i++;
            }
            if (array[j] > lambda) {
                j--;
            }
        }
        return array[i] <= lambda && i < finish ? i + 1 : i;
    }


}
