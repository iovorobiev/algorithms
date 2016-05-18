package org.iovorobiev;

import java.util.List;

public class Utils {
    public static void swap(int[] array, int i, int j) {
        int buf = array[i];
        array[i] = array[j];
        array[j] = buf;
    }

    public static void swap(List<Integer> array, int i, int j) {
        Integer buf = array.get(i);
        array.set(i, array.get(j));
        array.set(j, buf);
    }

    public static void initRandomArray(int[] array) {
        initRandomArray(array, true);
    }

    public static void initRandomArray(int[] array, boolean withOutput) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
            if (withOutput) {
                System.out.print(array[i] + (i < array.length - 1? ", " : ""));
            }
        }
        if (withOutput) {
            System.out.println();
        }
    }

    public static long findNextPrime(long n) {
        long result = n + 1;
        while(!isPrime(result)) {
            result++;
        }
        return result;
    }

    private static boolean isPrime(long n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
