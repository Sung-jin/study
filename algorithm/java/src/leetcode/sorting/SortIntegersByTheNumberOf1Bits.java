package leetcode.sorting;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
Q.1356. Sort Integers by The Number of 1 Bits
You are given an integer array arr. Sort the integers in the array in ascending order by the number of 1's in their binary representation and in case of two or more integers have the same number of 1's you have to sort them in ascending order.

Return the array after sorting it.
 */
public class SortIntegersByTheNumberOf1Bits {
    public int[] sortByBits(int[] arr) {
        Map<Integer, List<Integer>> values = new HashMap<>();
        int[] answer = new int[arr.length];
        AtomicInteger index = new AtomicInteger();

        for (int value : arr) {
            int bitCount = Integer.bitCount(value);
            List<Integer> bitValues = values.getOrDefault(bitCount, new ArrayList<>());
            bitValues.add(value);
            values.put(bitCount, bitValues);
        }

        values.keySet().stream().sorted().forEach(v -> values.get(v).stream().sorted().forEach(value -> answer[index.getAndIncrement()] = value));

        return answer;
    }
}

/*
class Solution {
    public int[] sortByBits(int[] arr) {
        //ok so for this enginner rule is applied where each number is updated by a the number itself + number of bits in number*(10001) and then we will sort it and then we will take the modulo.

        for(int i=0;i<arr.length;i++)
        {
            arr[i]+=Integer.bitCount(arr[i])*10001;
        }

        Arrays.sort(arr);

        for(int i=0;i<arr.length;i++)
        {
            arr[i]=arr[i]%10001;
        }
        return arr;
    }
}
 */
