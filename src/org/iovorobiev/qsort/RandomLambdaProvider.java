package org.iovorobiev.qsort;

public class RandomLambdaProvider implements ILambdaProvider {
    @Override
    public int getLambda(int[] array, int start, int finish) {
        int randomIndex = (int) (Math.random() * (finish - start) + start);
        return array[randomIndex];
    }
}
