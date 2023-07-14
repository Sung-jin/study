package leetcode.dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/*
Q.1218

Given an integer array arr and an integer difference, return the length of the longest subsequence in arr which is an arithmetic sequence such that the difference between adjacent elements in the subsequence equals difference.

A subsequence is a sequence that can be derived from arr by deleting some or no elements without changing the order of the remaining elements.
 */
public class LongestArithmeticSubsequenceOfGivenDifference {
    public int longestSubsequence(int[] arr, int difference) {
        if (arr.length == 0) return 0;
        Map<Integer, Integer> subsequenceCount = new HashMap<>();
        int maxCount = 0;

        for (int value : arr) {
            subsequenceCount.put(value, subsequenceCount.getOrDefault(value - difference, 0) + 1);
        }
        for (Integer value : subsequenceCount.values()) {
            maxCount = Math.max(maxCount, value);
        }

        return maxCount;
    }
    // 맵을 통해 이전값의 연속된 수를 계산하므로, dp 맞네
}

/*
class Solution {
	public int longestSubsequence(int[] arr, int difference) {
		HashMap<Integer, Integer> dp = new HashMap<>();
		int longest = 0;
		for(int i=0; i<arr.length; i++) {
			dp.put(arr[i], dp.getOrDefault(arr[i] - difference, 0) + 1);
			longest = Math.max(longest, dp.get(arr[i]));
		}
		return longest;
	}
}
 */