package leetcode.slidingWindow;

/*
Q.209
Given an array of positive integers nums and a positive integer target, return the minimal length of a
subarray whose sum is greater than or equal to target. If there is no such subarray, return 0 instead.
 */
public class MinimumSizeSubArraySum {
    public int minSubArrayLen(int target, int[] nums) {
        int minCount = Integer.MAX_VALUE;
        int from = 0;
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sum >= target) {
                int overflow = 0;

                while (true) {
                    if (overflow + nums[from] <= sum - target) overflow += nums[from++];
                    else break;
                }
                sum -= overflow;
                minCount = Math.min(minCount, i - from + 1);
            }
        }

        return minCount == Integer.MAX_VALUE ? 0 : minCount;
    }
}

/*
public int minSubArrayLen(int s, int[] a) {
  if (a == null || a.length == 0)
    return 0;

  int i = 0, j = 0, sum = 0, min = Integer.MAX_VALUE;

  while (j < a.length) {
    sum += a[j++];

    while (sum >= s) {
      min = Math.min(min, j - i);
      sum -= a[i++];
    }
  }

  return min == Integer.MAX_VALUE ? 0 : min;
}
 */