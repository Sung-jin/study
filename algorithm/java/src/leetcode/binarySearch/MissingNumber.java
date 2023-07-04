package leetcode.binarySearch;

import java.util.Arrays;

/*
Q.268
Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.
 */
public class MissingNumber {
    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        int start = 0, end = nums.length - 1;

        while (start < end) {
            int mid = (start + end) / 2;

            if (nums[mid] > mid) end = mid - 1;
            else start = mid + 1;
        }

        if (nums[start] != start) return start;
        else {
            if (start == 0 && nums[0] == 0) return 1;
            else if (start + 1 == nums.length) return nums.length;
            else return start + 1;
        }
    }
}

/*
public int missingNumber(int[] nums) { //binary search
    Arrays.sort(nums);
    int left = 0, right = nums.length, mid= (left + right)/2;
    while(left<right){
        mid = (left + right)/2;
        if(nums[mid]>mid) right = mid;
        else left = mid+1;
    }
    return left;
}
-> 보정을 어떻게 하느냐인데.. 마지막 결과 도출 시 보정하는 부분이 진짜 모르겠다 뭐가 좋은 방법인지
 */