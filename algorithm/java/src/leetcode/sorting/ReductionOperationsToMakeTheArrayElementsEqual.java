package leetcode.sorting;

import java.util.Arrays;

/*
Q.1887. Reduction Operations to Make the Array Elements Equal
Given an integer array nums, your goal is to make all elements in nums equal. To complete one operation, follow these steps:

Find the largest value in nums. Let its index be i (0-indexed) and its value be largest. If there are multiple elements with the largest value, pick the smallest i.
Find the next largest value in nums strictly smaller than largest. Let its value be nextLargest.
Reduce nums[i] to nextLargest.
Return the number of operations to make all elements in nums equal.
 */
public class ReductionOperationsToMakeTheArrayElementsEqual {
    public int reductionOperations(int[] nums) {
        Arrays.sort(nums);
        if (nums[0] == nums[nums.length - 1]) return 0;

        int index = nums.length - 2;
        int target = nums[nums.length - 1];
        int answer = 0;

        while(index >= 0) {
            while(index >= 0 && target == nums[index]) index--;

            if (index < 0) return answer;

            answer += nums.length - index - 1;
            target = nums[index];
            index--;
        }

        return answer;
    }
}

/*
public int reductionOperations(int[] n) {
    int res = 0, sz = n.length;
    Arrays.sort(n);
    for (int j = sz - 1; j > 0; --j)
        if (n[j - 1] != n[j])
            res += sz - j;
    return res;
}
 */