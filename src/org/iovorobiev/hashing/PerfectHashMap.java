package org.iovorobiev.hashing;

import javafx.util.Pair;
import org.iovorobiev.Utils;
import sun.misc.Perf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class PerfectHashMap {
    public static final int COLLISIONS_BARRIER = 3;
    private int p; // first prime number bigger than maxCapacity
    private int a, b; // coefficients for hash function
    private int maxCapacity;
    private int inputLength;
    private SimpleMap[] map;

    public PerfectHashMap(Pair<Short, String>[] values) {
        inputLength = values.length;
        maxCapacity = inputLength * inputLength;
        p = (int) Utils.findNextPrime(maxCapacity);
        map = new SimpleMap[maxCapacity];
        insertPerfect(values);
    }

    public String get(short key) {
        int hash = getHash(key);
        if (map[hash] == null) {
            return null;
        }
        return map[hash].get(key);
    }

    @SuppressWarnings("unchecked")
    private void insertPerfect(Pair<Short, String>[] values) {
        boolean collisionsAcceptable = false;
        int inited = 0;
        do {
            map = new SimpleMap[maxCapacity];
            initRandomHash();
            for (Pair<Short, String> value : values) {
                int hash = getHash(value.getKey());
                if (map[hash] == null) {
                    map[hash] = new SimpleMap();
                    inited++;
                }
                map[hash].put(value);
            }
            collisionsAcceptable = collisionsAcceptable(map);
        } while (!collisionsAcceptable);
        for (SimpleMap simpleMap : map) {
            if (simpleMap != null) {
                simpleMap.rearrange();
            }
        }
    }

    private boolean collisionsAcceptable(SimpleMap[] map) {
        long squaredSum = 0;
        for (SimpleMap simpleMap : map) {
            if (simpleMap != null) {
                squaredSum += simpleMap.size() * simpleMap.size();
            }
        }
        return squaredSum < COLLISIONS_BARRIER * inputLength;
    }

    private void initRandomHash() {
        a = (int) (Math.random() * (p - 2)  + 1);
        b = (int) (Math.random() * (p - 1));
    }

    private int getHash(short key) {
        int reflectedKey = key + Short.MAX_VALUE;
        return ((a * reflectedKey + b) % p) % maxCapacity;
    }

    private static class SimpleMap {
        private ArrayList<Pair<Short, String>> content = new ArrayList<>();
        private int maxCapacity;
        private int p;
        private int a, b;

        void put(Pair<Short, String> pair) {
            content.add(pair);
        }

        String get(short key) {
            Pair<Short, String> result = content.get(getHash(key));
            if (result != null && result.getKey() == key) {
                return result.getValue();
            }
            return null;
        }

        int size() {
            return content.size();
        }

        void rearrange() {
            maxCapacity = 3 * content.size() * content.size();
            p = (int) Utils.findNextPrime(maxCapacity);
            Pair<Short, String>[] arrangedContent;
            boolean hasCollisions;
            do {
                hasCollisions = false;
                initRandomHash();
                arrangedContent = new Pair[maxCapacity];
                for (Pair<Short, String> pair : content) {
                    int hash = getHash(pair.getKey());
                    if (arrangedContent[hash] != null) {
                        hasCollisions = true;
                        break;
                    }
                    arrangedContent[hash] = pair;
                }
            } while (hasCollisions);
            content = new ArrayList<>(Arrays.asList(arrangedContent));
        }

        private void initRandomHash() {
            a = (int) Math.round (Math.random() * (p - 2)  + 1);
            b = (int) Math.round (Math.random() * (p - 1));
        }

        private int getHash(short key) {
            int reflectedKey = key + Short.MAX_VALUE;
            return ((a * reflectedKey + b) % p) % maxCapacity;
        }
    }
}
