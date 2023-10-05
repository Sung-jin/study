package leetcode.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
Q.229. Majority Element II
Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
 */
public class MajorityElementII {
    public List<Integer> majorityElement(int[] nums) {
        if (nums.length < 3) return Arrays.stream(nums)
                .distinct()
                .boxed()
                .collect(Collectors.toList());

        Arrays.sort(nums);
        List<Integer> answer = new ArrayList<>();
        int overCount = (nums.length / 3) + 1;
        int compareNum = nums[0];
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == compareNum) count++;
            else {
                if (count >= overCount) answer.add(compareNum);
                compareNum = nums[i];
                count = 1;
            }
        }

        if (count >= overCount) answer.add(compareNum);

        return answer;
    }
}

/*
class Solution:
# @param {integer[]} nums
# @return {integer[]}
def majorityElement(self, nums):
    if not nums:
        return []
    count1, count2, candidate1, candidate2 = 0, 0, 0, 1
    for n in nums:
        if n == candidate1:
            count1 += 1
        elif n == candidate2:
            count2 += 1
        elif count1 == 0:
            candidate1, count1 = n, 1
        elif count2 == 0:
            candidate2, count2 = n, 1
        else:
            count1, count2 = count1 - 1, count2 - 1
    return [n for n in (candidate1, candidate2)
                    if nums.count(n) > len(nums) // 3]
 */