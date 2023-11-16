package leetcode.greedy;

import java.util.Arrays;

/*
Q.1980. Find Unique Binary String
Given an array of strings nums containing n unique binary strings each of length n, return a binary string of length n that does not appear in nums. If there are multiple answers, you may return any of them.
 */
public class FindUniqueBinaryString {
    public String findDifferentBinaryString(String[] nums) {
        int ans = 0;
        int length = nums[0].length();
        int[] convertedNums = Arrays.stream(nums)
                .mapToInt(v -> Integer.parseInt(v, 2))
                .sorted()
                .toArray();

        for (int num : convertedNums) {
            if (num == ans) ans++;
            else return intToBinaryWithLength(ans, length);
        }

        return intToBinaryWithLength(ans, length);
    }

    private String intToBinaryWithLength(int value, int length) {
        return String.format("%" + length + "s", Integer.toBinaryString(value))
                .replaceAll(" ", "0");
    }
}

/*
class Solution {
    public String findDifferentBinaryString(String[] nums) {
        StringBuilder ans= new StringBuilder();
        for(int i=0; i<nums.length; i++)
            ans.append(nums[i].charAt(i) == '0' ? '1' : '0');              // Using ternary operator
        return ans.toString();
    }
}
 */