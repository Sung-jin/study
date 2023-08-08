package leetcode.binarySearch;

/*
Q.33
There is an integer array nums sorted in ascending order (with distinct values).

Prior to being passed to your function, nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].

Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.

You must write an algorithm with O(log n) runtime complexity.
 */
public class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        if (nums.length == 1) return nums[0] == target ? 0 : -1;

        int pivotIndex = findPivotIndex(nums);
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int adjustIndex = (mid + pivotIndex) % nums.length;
            int targetNumber = nums[adjustIndex];

            if (targetNumber == target) return adjustIndex;
            else if (targetNumber > target) right = mid - 1;
            else left = mid + 1;
        }

        return -1;
    }

    private int findPivotIndex(int[] nums) {
        if (nums.length == 1) return 0;
        else if (nums.length == 2) return nums[0] > nums[1] ? 1 : 0;
        else if (nums[0] < nums[nums.length - 1]) return 0;

        int left = 0, right = nums.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;

            if (mid == 0) return nums[0] > nums[1] ? 1 : 0;
            else if (mid == nums.length - 1) return nums[nums.length - 1] > nums[nums.length - 2] ? nums.length - 2 : nums.length - 1;
            else if (nums[mid] > nums[0]) left = mid + 1;
            else {
                if (nums[mid - 1] > nums[mid + 1]) return mid;
                else right = mid - 1;
            }
        }

        return left;
    }
}

/*
class Solution {
    public int search(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1;

        // Find the index of the pivot element (the smallest element)
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[n - 1]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // 단순하게 pivot 을 mid 랑 mid 왼쪽이랑만 비교해서 찾을 수 있네ㅍㅍㅍ

        return shiftedBinarySearch(nums, left, target);
    }

    // Shift elements in a circular manner, with the pivot element at index 0.
    // Then perform a regular binary search
    private int shiftedBinarySearch(int[] nums, int pivot, int target) {
        int n = nums.length;
        int shift = n - pivot;
        int left = (pivot + shift) % n;
        int right = (pivot - 1 + shift) % n;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[(mid - shift + n) % n] == target) {
                return (mid - shift + n) % n;
            } else if (nums[(mid - shift + n) % n] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
 */