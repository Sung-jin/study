package leetcode.binarySearch;

/*
Q.34. Find First and Last Position of Element in Sorted Array
Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.

If target is not found in the array, return [-1, -1].

You must write an algorithm with O(log n) runtime complexity.
 */
public class FindFirstAndLastPositionOfElementInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int startIndex = findTargetIndex(nums, target);

        if (startIndex == -1) return new int[]{-1, -1};
        int start = 0, end = 0;

        for (int i = startIndex; i >= 0; i--) {
            if (nums[i] == target) start = i;
            else break;
        }
        for (int i = startIndex; i < nums.length; i++) {
            if (nums[i] == target) end = i;
            else break;
        }

        return new int[]{start, end};
    }

    private int findTargetIndex(int[] nums, int target) {
        if (nums.length == 0) return -1;

        int left = 0, right = nums.length - 1;
        int index = (left + right) / 2;

        while (index >= 0 && index < nums.length && nums[index] != target) {
            if (left > right) return - 1;
            if (nums[index] > target) right = index - 1;
            else left = index + 1;

            index = (left + right) / 2;
        }

        return index;
    }
}

/*
public class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);
        return result;
    }

    private int findFirst(int[] nums, int target){
        int idx = -1;
        int start = 0;
        int end = nums.length - 1;
        while(start <= end){
            int mid = (start + end) / 2;
            if(nums[mid] >= target){
                end = mid - 1;
            }else{
                start = mid + 1;
            }
            if(nums[mid] == target) idx = mid;
        }
        return idx;
    }

    private int findLast(int[] nums, int target){
        int idx = -1;
        int start = 0;
        int end = nums.length - 1;
        while(start <= end){
            int mid = (start + end) / 2;
            if(nums[mid] <= target){
                start = mid + 1;
            }else{
                end = mid - 1;
            }
            if(nums[mid] == target) idx = mid;
        }
        return idx;
    }
}
 */