package leetcode.sorting;

import java.util.Arrays;

/*
Q.1846. Maximum Element After Decreasing and Rearranging
You are given an array of positive integers arr. Perform some operations (possibly none) on arr so that it satisfies these conditions:

The value of the first element in arr must be 1.
The absolute difference between any 2 adjacent elements must be less than or equal to 1. In other words, abs(arr[i] - arr[i - 1]) <= 1 for each i where 1 <= i < arr.length (0-indexed). abs(x) is the absolute value of x.
There are 2 types of operations that you can perform any number of times:

Decrease the value of any element of arr to a smaller positive integer.
Rearrange the elements of arr to be in any order.
Return the maximum possible value of an element in arr after performing the operations to satisfy the conditions.
 */
public class MaximumElementAfterDecreasingAndRearranging {
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        Arrays.sort(arr);
        int ans = 0;
        for (int i : arr) {
            ans = Math.min(ans + 1, i);
        }

        return ans;
    }
}

/*
public int maximumElementAfterDecrementingAndRearranging(int[] A) {
    Arrays.sort(A);
    int pre = 0;
    for (int a: A)
        pre = Math.min(pre + 1, a);
    return pre;
}
 */