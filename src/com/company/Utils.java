package com.company;

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

    static void initRandomArray(int[] array) {
        initRandomArray(array, true);
    }

    static void initRandomArray(int[] array, boolean withOutput) {
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
}