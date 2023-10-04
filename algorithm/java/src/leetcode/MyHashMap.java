package leetcode;

import java.util.Arrays;

/*
Q.706. Design HashMap
Design a HashMap without using any built-in hash table libraries.

Implement the MyHashMap class:

MyHashMap() initializes the object with an empty map.
void put(int key, int value) inserts a (key, value) pair into the HashMap. If the key already exists in the map, update the corresponding value.
int get(int key) returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
void remove(key) removes the key and its corresponding value if the map contains the mapping for the key.
 */
public class MyHashMap {
    private final int[] values;
    // 0 <= key, value <= 10^6

    public MyHashMap() {
        this.values = new int[1_000_001];
        Arrays.fill(values, -1);
    }

    public void put(int key, int value) {
        this.values[key] = value;
    }

    public int get(int key) {
        return this.values[key];
    }

    public void remove(int key) {
        this.values[key] = -1;
    }
}

/*
class MyHashMap {

    private static final int SIZE = 1000;
    private List<int[]>[] map;

    public MyHashMap() {
        map = new ArrayList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            map[i] = new ArrayList<>();
        }
    }

    public void put(int key, int value) {
        int index = key % SIZE;
        List<int[]> bucket = map[index];
        for (int[] pair : bucket) {
            if (pair[0] == key) {
                pair[1] = value;
                return;
            }
        }
        bucket.add(new int[]{key, value});
    }

    public int get(int key) {
        int index = key % SIZE;
        List<int[]> bucket = map[index];
        for (int[] pair : bucket) {
            if (pair[0] == key) {
                return pair[1];
            }
        }
        return -1;
    }

    public void remove(int key) {
        int index = key % SIZE;
        List<int[]> bucket = map[index];
        for (int i = 0; i < bucket.size(); i++) {
            int[] pair = bucket.get(i);
            if (pair[0] == key) {
                bucket.remove(i);
                return;
            }
        }
    }
}
 */