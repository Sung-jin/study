package leetcode.greedy;

import java.util.HashMap;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.MOD;

/*
Q.1814. Count Nice Pairs in an Array
You are given an array nums that consists of non-negative integers. Let us define rev(x) as the reverse of the non-negative integer x. For example, rev(123) = 321, and rev(120) = 21. A pair of indices (i, j) is nice if it satisfies all of the following conditions:

0 <= i < j < nums.length
nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
Return the number of nice pairs of indices. Since that number can be too large, return it modulo 109 + 7.
 */
public class CountNicePairsInAnArray {
    public int countNicePairs(int[] nums) {
        Map<Integer, Integer> reverseMap = new HashMap<>();
        int answer = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) reverseMap.put(nums[i], reverseNumber(nums[i]));

            for (int j = i + 1; j < nums.length; j++) {
                if (i == 0) reverseMap.put(nums[j], reverseNumber(nums[j]));

                if (nums[i] + reverseMap.get(nums[j]) == reverseMap.get(nums[i]) + nums[j]) answer++;
            }
        }

        return answer % MOD;
    }

    private int reverseNumber(int value) {
        int remain = Math.abs(value);
        int answer = 0;
        while(remain != 0) {
            answer *= 10;
            answer += remain % 10;
            remain /= 10;
        }

        return answer;
    }
}

/*
public int countNicePairs(int[] A) {
    int res = 0, mod = (int)1e9 + 7;
    Map<Integer, Integer> count = new HashMap<>();;
    for (int a : A) {
        int b = rev(a), v = count.getOrDefault(a - b, 0);
        count.put(a - b, v + 1);
        res = (res + v) % mod;
    }
    return res;
}

public int rev(int a) {
    int b = 0;
    while (a > 0) {
        b = b * 10 + (a % 10);
        a /= 10;
    }
    return b;
}
 */
