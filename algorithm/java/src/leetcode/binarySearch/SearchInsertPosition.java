package leetcode.binarySearch;

/*
Q. 35
Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with O(log n) runtime complexity.
 */
public class SearchInsertPosition {
    public int searchInsert(int[] nums, int target) {
        // You must write an algorithm with O(log n) runtime complexity.
        // 이미 정렬된 배열을 전달함.
        int from = 0, to = nums.length;

        while (from < to) {
            int mid = (int) (double) ((from + to) / 2);
            int value = nums[mid];

            if (nums[mid] == target) return mid;
            else if (value > target) to = mid - 1;
            else from = mid + 1;
        }

        if (to == -1) return 0;
        else if (to == nums.length || nums[to] >= target) return to;
        else return to + 1;
    }
}

/*
class Solution {
	public int searchInsert(int[] nums, int target) {
		if(nums == null || nums.length == 0) return 0;

		int n = nums.length;
		int l = 0;
		int r = n - 1;
		while(l < r){
			int m = l + (r - l)/2;

			if(nums[m] == target) return m;
			else if(nums[m] > target) r = m; // right could be the result
			else l = m + 1; // m + 1 could be the result
		}

		// 1 element left at the end
		// post-processing
		return nums[l] < target ? l + 1: l;
	}
}
 */