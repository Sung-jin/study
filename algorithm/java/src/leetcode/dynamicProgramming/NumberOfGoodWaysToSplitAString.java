package leetcode.dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/*
Q.1525
You are given a string s.

A split is called good if you can split s into two non-empty strings sleft and sright where their concatenation is equal to s (i.e., sleft + sright = s) and the number of distinct letters in sleft and sright is the same.

Return the number of good splits you can make in s.
 */
public class NumberOfGoodWaysToSplitAString {
    public int numSplits(String s) {
        Map<Character, Integer> left = new HashMap<>();
        Map<Character, Integer> right = new HashMap<>();
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            right.put(s.charAt(i), right.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length() - 1; i++) {
            char value = s.charAt(i);
            int rightCount = right.get(value);

            if (rightCount == 1) right.remove(value);
            else right.put(value, rightCount - 1);
            left.put(value, left.getOrDefault(value, 0) + 1);

            if (left.size() - right.size() == 0) ans++;
        }

        return ans;
    }
}

/*
public int numSplits(String str) {
    int l[] = new int[26], r[] = new int[26], d_l = 0, d_r = 0, res = 0;
    var s = str.toCharArray();
    for (char ch : s)
        d_r += ++r[ch - 'a'] == 1 ? 1 : 0;
    for (int i = 0; i < s.length; ++i) {
        d_l += ++l[s[i] - 'a'] == 1 ? 1 : 0;
        d_r -= --r[s[i] - 'a'] == 0 ? 1 : 0;
        res += d_l == d_r ? 1 : 0;
    }
    return res;
}
 */
