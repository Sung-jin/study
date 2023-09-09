package leetcode.dynamicProgramming;

import java.util.Arrays;

/*
Q.377 Combination Sum IV
Given an array of distinct integers nums and a target integer target, return the number of possible combinations that add up to target.

The test cases are generated so that the answer can fit in a 32-bit integer.
 */
public class CombinationSumIV {

    private int[] dp;

    public int combinationSum4(int[] nums, int target) {
        dp = new int[target + 1];
        Arrays.sort(nums);
        Arrays.fill(dp, -1);
        dp[0] = 1;

        return combination(nums, target);
    }

    private int combination(int[] nums, int target) {
        if (dp[target] != -1) return dp[target];

        int count = 0;

        for (int num : nums) {
            if (target >= num) {
                count += combination(nums, target - num);
            } else break;
        }

        dp[target] = count;
        return count;
    }
}
