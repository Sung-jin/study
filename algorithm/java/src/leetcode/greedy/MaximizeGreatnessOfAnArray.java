package leetcode.greedy;

import java.util.Arrays;

/*
Q.2592
You are given a 0-indexed integer array nums. You are allowed to permute nums into a new array perm of your choosing.

We define the greatness of nums be the number of indices 0 <= i < nums.length for which perm[i] > nums[i].

Return the maximum possible greatness you can achieve after permuting nums.
 */
public class MaximizeGreatnessOfAnArray {
    public int maximizeGreatness(int[] nums) {
        if (nums.length == 0 || nums.length == 1) return 0;

        Arrays.sort(nums);
        int nextIndex = 1;
        int ans = 0;

        for (int num : nums) {
            if (nextIndex == nums.length) return ans;

            for (int j = nextIndex; j < nums.length; j++) {
                nextIndex = j + 1;
                if (nums[j] > num) {
                    ans++;
                    break;
                }
            }
        }

        return ans;
    }
}

/*
public int maximizeGreatness(int[] A) {
    Arrays.sort(A);
    int res = 0;
    for (int a : A)
        if (a > A[res])
            res++;
    return res;
    // 글네, 어차피 sort 하고 loop 돌면서 큰 부분을 현재까지의 큰 값으로 구하면 되겠네. 이전 index 를 알 필요가 없네
}
 */