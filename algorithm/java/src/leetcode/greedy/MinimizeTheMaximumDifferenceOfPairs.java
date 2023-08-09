package leetcode.greedy;

/*
Q. 2616
You are given a 0-indexed integer array nums and an integer p. Find p pairs of indices of nums such that the maximum difference amongst all the pairs is minimized. Also, ensure no index appears more than once amongst the p pairs.

Note that for a pair of elements at the index i and j, the difference of this pair is |nums[i] - nums[j]|, where |x| represents the absolute value of x.

Return the minimum maximum difference among all p pairs. We define the maximum of an empty set to be zero.

문제가 이해가 안되네. pair 의 의미는 뭐고, p 가 뭔데
 */
public class MinimizeTheMaximumDifferenceOfPairs {
    public int minimizeMax(int[] nums, int p) {
        int indicateDiff = 0, index = -1;
        int maxDiff = 0;

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];

            if (index == -1) {
                if (num == p) index = i;
            } else if (num == nums[index]) {
                indicateDiff = i - p;
                break;
            }

            if (i == nums.length - 1) return 0;
        }

        for (int i = index + 1 + indicateDiff; i < nums.length; i++) {
            maxDiff = Math.max(maxDiff, Math.abs(nums[i - indicateDiff] - nums[i]));
        }

        return maxDiff;
    }
}

/*
public int minimizeMax(int[] A, int p) {
    Arrays.sort(A);
    int n = A.length, left = 0, right = A[n - 1] - A[0];
    while (left < right) {
        int mid = (left + right) / 2, k = 0;
        for (int i = 1; i < n && k < p; ++i) {
            if (A[i] - A[i - 1] <= mid) {
                k++;
                i++;
            }
        }
        if (k >= p)
            right = mid;
        else
            left = mid + 1;
    }
    return left;
}
 */