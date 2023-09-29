package leetcode.slidingWindow;

/*
Q.896. Monotonic Array
An array is monotonic if it is either monotone increasing or monotone decreasing.

An array nums is monotone increasing if for all i <= j, nums[i] <= nums[j]. An array nums is monotone decreasing if for all i <= j, nums[i] >= nums[j].

Given an integer array nums, return true if the given array is monotonic, or false otherwise.
 */
public class MonotonicArray {
    public boolean isMonotonic(int[] nums) {
        if (nums.length == 1) return true;

        int index = 0, diff = nums[0] - nums[1];

        while (index < nums.length - 2 && diff == 0) {
            index++;
            diff = nums[index] - nums[index + 1];
        }

        boolean isIncrease = diff > 0;

        for (int i = index + 1; i < nums.length - 1; i++) {
            int nowDiff = nums[i] - nums[i + 1];

            if (nowDiff == 0) continue;

            boolean nowIncrease = nowDiff > 0;

            if (isIncrease != nowIncrease) return false;
        }

        return true;
    }
}

/*
public boolean isMonotonic(int[] A) {
    boolean inc = true, dec = true;
    for (int i = 1; i < A.length; ++i) {
        inc &= A[i - 1] <= A[i];
        dec &= A[i - 1] >= A[i];
    }
    return inc || dec;
}
 */
