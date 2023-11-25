package leetcode.greedy;

/*
Q.1685. Sum of Absolute Differences in a Sorted Array
You are given an integer array nums sorted in non-decreasing order.

Build and return an integer array result with the same length as nums such that result[i] is equal to the summation of absolute differences between nums[i] and all the other elements in the array.

In other words, result[i] is equal to sum(|nums[i]-nums[j]|) where 0 <= j < nums.length and j != i (0-indexed).
 */
public class SumOfAbsoluteDifferencesInASortedArray {
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int[] answer = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int res = Math.abs(nums[i] - nums[j]);
                answer[i] += res;
                answer[j] += res;
            }
        }

        return answer;
    }
}

/*
public int[] getSumAbsoluteDifferences(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    int[] prefixSum = new int[n + 1];
    for (int i = 0; i < n; ++i) {
        prefixSum[i + 1] = prefixSum[i] + nums[i];
    }
    for (int i = 0; i < n; ++i) {
        res[i] = i * nums[i] - prefixSum[i] + (prefixSum[n] -  prefixSum[i] - (n - i) * nums[i]);
    }
    return res;
}
 */
