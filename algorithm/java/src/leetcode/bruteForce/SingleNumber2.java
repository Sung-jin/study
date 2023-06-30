package leetcode.bruteForce;

import java.util.Arrays;

/*
Q.137
Given an integer array nums where every element appears three times except for one, which appears exactly once. Find the single element and return it.

You must implement a solution with a linear runtime complexity and use only constant extra space.
 */
public class SingleNumber2 {
    public int singleNumber(int[] nums) {
        Arrays.sort(nums);
        int beforeNum = nums[0];

        for (int i = 1; i < nums.length; i = i + 3) {
            if (beforeNum != nums[i]) return beforeNum;
            beforeNum = nums[i + 2];
        }

        return nums[nums.length - 1];
    }
}

/*
public int singleNumber(int[] A) {
    int ones = 0, twos = 0;
    for(int i = 0; i < A.length; i++){
        ones = (ones ^ A[i]) & ~twos;
        twos = (twos ^ A[i]) & ~ones;
    }
    return ones;
}
와 비트 연산자 이용하는거 신기하네
 */
