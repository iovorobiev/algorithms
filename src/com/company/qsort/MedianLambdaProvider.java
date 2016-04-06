package com.company.qsort;

import com.company.Utils;

import javax.rmi.CORBA.Util;

/**
 * Created by vorobievilya on 02/04/16.
 */
public class MedianLambdaProvider implements ILambdaProvider {
    @Override
    public int getLambda(int[] array, int start, int finish) {
        int medIndex = (finish - start) / 2;
        int[] medianArray = new int[] {array[start], array[medIndex], array[finish]};
        for (int i = 0; i < medianArray.length - 1; i++) {
            for (int j = i + 1; j < medianArray.length; j++) {
                if (medianArray[j] < medianArray[i]) {
                    Utils.swap(medianArray, i, j);
                }
            }
        }
        return medianArray[1];
    }
}
