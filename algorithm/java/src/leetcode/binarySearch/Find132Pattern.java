package leetcode.binarySearch;

import java.util.Arrays;

/*
Q.456. 132 Pattern
Given an array of n integers nums, a 132 pattern is a subsequence of three integers nums[i], nums[j] and nums[k] such that i < j < k and nums[i] < nums[k] < nums[j].

Return true if there is a 132 pattern in nums, otherwise, return false.
 */
public class Find132Pattern {
    public boolean find132pattern(int[] nums) {
        if (nums.length < 3) return false;

        for (int i = 1; i < nums.length - 1; i++) {
            if (is123Pattern(nums, i)) return true;
        }

        return false;
    }

    private boolean is123Pattern(int[] nums, int index) {
        int middle = nums[index];

        return hasUpDownValue(Arrays.copyOfRange(nums, 0, index), middle, false) &&
                hasUpDownValue(Arrays.copyOfRange(nums, index + 1, nums.length), middle, true);
    }

    private boolean hasUpDownValue(int[] nums, int target, boolean checkUp) {
        int left = 0, right = nums.length - 1;
        int value = nums[(left + right) / 2];

        while (!(checkUp ? value > target : value < target)) {
            int mid = (left + right) / 2;
            if (checkUp) left = mid;
            else right = mid;

            if (left > right) return false;

            value = nums[mid];
        }

        return true;
    }
}

/*
public boolean find132pattern(int[] nums) {
    int n = nums.length, top = n, third = Integer.MIN_VALUE;

    for (int i = n - 1; i >= 0; i--) {
        if (nums[i] < third) return true;
        while (top < n && nums[i] > nums[top]) third = nums[top++];
        nums[--top] = nums[i];
    }

    return false;
}
 */