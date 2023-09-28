package leetcode.sorting;

import java.util.Arrays;

/*
905. Sort Array By Parity
Given an integer array nums, move all the even integers at the beginning of the array followed by all the odd integers.

Return any array that satisfies this condition.
 */
public class SortArrayByParity {
    public int[] sortArrayByParity(int[] nums) {
        return Arrays.stream(nums)
                .boxed()
                .sorted((a, b) -> a % 2 == 0 ? -1 : b % 2 == 0 ? 1 : 0)
                .mapToInt(v -> v)
                .toArray();
    }
}

/*
public int[] sortArrayByParity(int[] A) {
    for (int i = 0, j = 0; j < A.length; j++)
        if (A[j] % 2 == 0) {
            int tmp = A[i];
            A[i++] = A[j];
            A[j] = tmp;;
        }
    return A;
}
 */
