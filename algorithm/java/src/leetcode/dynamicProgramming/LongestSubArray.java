package leetcode.dynamicProgramming;

import java.util.Arrays;

/*
Q.1943
Given a binary array nums, you should delete one element from it.

Return the size of the longest non-empty subarray containing only 1's in the resulting array. Return 0 if there is no such subarray.
 */
public class LongestSubArray {
    public int longestSubarray(int[] nums) {
        if (Arrays.stream(nums).allMatch(n -> n == 1)) return nums.length - 1;
        int maxContinuousCount = 0;
        int beforeRemoveContinuousCount = 0;
        int continuousCount = 0;

        for (int num : Arrays.copyOfRange(nums, 0, nums.length + 1)) {
            if (num == 0) {
                maxContinuousCount = Math.max(maxContinuousCount, beforeRemoveContinuousCount + continuousCount);
                beforeRemoveContinuousCount = continuousCount;
                continuousCount = 0;
            } else {
                continuousCount++;
            }
        }

        return maxContinuousCount;
    }
}

/*
public int longestSubarray(int[] A) {
    int i = 0, j, k = 1, res = 0;
    for (j = 0; j < A.length; ++j) {
        if (A[j] == 0) {
            k--;
        }
        while (k < 0) {
            if (A[i] == 0) {
                k++;
            }
            i++;
        }
        res = Math.max(res, j - i);
    }
    return res;
}
 */