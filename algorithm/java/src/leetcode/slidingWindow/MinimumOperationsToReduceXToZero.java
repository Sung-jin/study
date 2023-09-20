package leetcode.slidingWindow;

import java.util.Arrays;

/*
Q.1658. Minimum Operations to Reduce X to Zero
You are given an integer array nums and an integer x. In one operation, you can either remove the leftmost or the rightmost element from the array nums and subtract its value from x. Note that this modifies the array for future operations.

Return the minimum number of operations to reduce x to exactly 0 if it is possible, otherwise, return -1.
 */
public class MinimumOperationsToReduceXToZero {
    public int minOperations(int[] nums, int x) {
        Arrays.sort(nums);

        if (nums[0] == x) return 1;
        if (nums[0] > x) return -1;

        int minOperations = -1;
        int count = 1;
        int from = 0, to = 1;
        int sum = nums[0];

        while (to < nums.length) {
            if (nums[to] > x) return minOperations;

            sum += nums[to];
            count++;

            if (sum > x) {
                while (sum > x && from < to) {
                    sum -= nums[from];
                    from++;
                    count--;
                }
            }
            if (sum == x) {
                if (minOperations == -1) minOperations = count;
                else minOperations = Math.min(minOperations, count);
            }

            to++;
        }

        return minOperations;
    }
}

/*
int target = -x;
for (int num : nums) target += num;

if (target == 0) return nums.length;  // since all elements are positive, we have to take all of them

Map<Integer, Integer> map = new HashMap<>();
map.put(0, -1);
int sum = 0;
int res = Integer.MIN_VALUE;

for (int i = 0; i < nums.length; ++i) {

	sum += nums[i];
	if (map.containsKey(sum - target)) {
		res = Math.max(res, i - map.get(sum - target));
	}

    // no need to check containsKey since sum is unique
	map.put(sum, i);
}

return res == Integer.MIN_VALUE ? -1 : nums.length - res;
 */