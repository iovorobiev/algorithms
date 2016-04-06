package com.company.heap;

import com.company.Utils;

import java.util.ArrayList;
import java.util.Collections;

//Min heap based on binary tree
public class Heap {
    private ArrayList<Integer> content = new ArrayList<>();

    public void makeHeap(int[] array) {
        int start = (int) (Math.floor(array.length / 2f) - 1);
        for (int anArray : array) {
            content.add(anArray);
        }
        while (start >= 0) {
            siftDown(start);
            start--;
        }
    }

    public void insert(int value) {
        int insertedIndex = content.size();
        content.add(value);
        int index = insertedIndex;

        while (index > 0 && content.get(getParentIndex(index)) > content.get(index)) {
            int parentIndex = getParentIndex(index);
            Utils.swap(content, parentIndex, index);
            index = parentIndex;
        }
    }

    public void remove(int index) {
        content.set(index, content.get(content.size() - 1));
        content.remove(content.size() - 1);
        siftDown(index);
    }

    private void siftDown(int currentIndex) {
        while (currentIndex < content.size() - 1 ) {
            int leftChildIndex = getLeftChildIndex(currentIndex);
            int rightChildIndex = getRightChildIndex(currentIndex);

            int minIndex;

            if (leftChildIndex >= content.size()) {
                break;
            }
            if (rightChildIndex >= content.size()) {
                minIndex = leftChildIndex;
            } else {
                minIndex = getLeftChild(currentIndex) > getRightChild(currentIndex) ? rightChildIndex : leftChildIndex;
            }

            if (content.get(currentIndex) > content.get(minIndex)) {
                Utils.swap(content, currentIndex, minIndex);
                currentIndex = minIndex;
                continue;
            }
            break;
        }
    }

    public int getMin() {
        return content.get(0);
    }

    public int extractMin() {
        int currentMin = content.get(0);
        remove(0);
        return currentMin;
    }

    public int decreaseKey(int index, int newValue) {
        return 0;
    }


    private int getParentIndex(int childIndex) {
        return (childIndex - (childIndex % 2 == 0? 2 : 1)) / 2;
    }

    private int getLeftChild(int index) {
        return content.get(getLeftChildIndex(index));
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChild(int index) {
        return content.get(getRightChildIndex(index));
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }
}
