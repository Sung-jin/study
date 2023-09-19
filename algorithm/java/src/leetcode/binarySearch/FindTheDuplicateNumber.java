package leetcode.binarySearch;

/*
Q.287. Find the Duplicate Number
Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.

There is only one repeated number in nums, return this repeated number.

You must solve the problem without modifying the array nums and uses only constant extra space.
 */
public class FindTheDuplicateNumber {
    public int findDuplicate(int[] nums) {
        boolean[] visited = new boolean[nums.length];

        return findDuplicateNumber(nums, 0, nums.length - 1, visited);
    }

    private int findDuplicateNumber(int[] nums, int from, int to, boolean[] visited) {
        int mid = (to + from) / 2;
        int target = nums[mid];
        if (visited[target]) return nums[mid];

        visited[target] = true;

        int answer  = -1;

        if (from <= mid - 1) answer = findDuplicateNumber(nums, from, mid - 1, visited);
        if (answer != -1) return answer;
        if (to >= mid + 1) answer = findDuplicateNumber(nums, mid + 1, to, visited);

        return answer;
    }
}

/*
Binary Search
public static int findDuplicate_bs(int[] nums) {
    int len = nums.length;
    int low = 1;
    int high = len - 1;
    while (low < high) {
        int mid = low + (high - low) / 2;
        int cnt = 0;
        for (int i = 0; i < len; i++) {
            if (nums[i] <= mid) {
                cnt++;
            }
        }

        if (cnt <= mid) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }

    return low;
}

Fast Slow Pointers
public int findDuplicate_fastSlow(int[] nums) {
    int slow = 0;
    int fast = 0;
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow != fast);

    slow = 0;
    while (slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
    }

    return slow;
}
 */