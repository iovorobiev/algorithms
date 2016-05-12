package org.iovorobiev.hashing;

import javafx.util.Pair;

import java.util.LinkedList;

public class UniversalHashMap {
    private static final int p = 65537; //first prime number that is bigger than maxContain

    private int maxContain = 2 * Short.MAX_VALUE;
    private int a, b; //coeffs for hash functions family

    private LinkedList<Pair<Short, String>>[] storage;
    private int size;

    @SuppressWarnings("unchecked")
    public UniversalHashMap() {
        a = (int) (Math.random() * (p - 2)  + 1);
        b = (int) (Math.random() * (p - 1));
        storage = new LinkedList[maxContain];
    }

    public void put(short key, String value) {
        int hash = getHash(key);
        if (storage[hash] == null) {
            storage[hash] = new LinkedList<>();
        }
        for (int i = 0; i < storage[hash].size(); i++) {
            if (storage[hash].get(i).getKey() == key) {
                storage[hash].set(i, new Pair<>(key, value));
                return;
            }
        }
        size++;
        storage[hash].add(new Pair<>(key, value));
    }

    public String get(short key) {
        int hash = getHash(key);
        if (storage[hash] == null) {
            return null;
        }
        for (int i = 0; i < storage[hash].size(); i++) {
            if (storage[hash].get(i).getKey() == key) {
                return storage[hash].get(i).getValue();
            }
        }
        return null;
    }

    public int size() {
        return size();
    }

    public void printContent() {
        for (int i = 0; i < storage.length; i++) {

            if (storage[i] == null) {
                continue;
            }
            System.out.print(i + " -> ");
            for (int j = 0; j < storage[i].size(); j++) {
                System.out.print(storage[i].get(j) + ", ");
            }
            System.out.println();
        }
    }

    private int getHash(short key) {
        if (key < 0) {
            key += maxContain / 2;
        }
        return ((a * key + b) % p) % maxContain;
    }

}
