package leetcode.greedy;

import java.util.HashMap;
import java.util.Map;

/*
Q.1759. Count Number of Homogenous Substrings
Given a string s, return the number of homogenous substrings of s. Since the answer may be too large, return it modulo 109 + 7.

A string is homogenous if all the characters of the string are the same.

A substring is a contiguous sequence of characters within a string.
 */
public class CountNumberOfHomogenousSubstrings {
    public int countHomogenous(String s) {
        if (s.isEmpty()) return 0;

        char[] chars = s.toCharArray();
        char compareTarget = chars[0];
        Map<String, Integer> counts = new HashMap<>();
        int repeatingNumber = 1;
        int mod = 1_000_000_007;

        counts.put(String.valueOf(compareTarget), 1);

        for (int i = 1; i < chars.length; i++) {
            if (compareTarget == chars[i]) {
                repeatingNumber++;
            } else {
                compareTarget = chars[i];
                repeatingNumber = 1;
            }

            addCounting(compareTarget, repeatingNumber, counts);
        }

        return counts.values()
                .stream()
                .mapToInt(integer -> integer)
                .sum() % mod;
    }

    private void addCounting(char target, int repeatingCount, Map<String, Integer> counts) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < repeatingCount; i++) {
            sb.append(target);
            counts.put(sb.toString(), counts.getOrDefault(sb.toString(), 0) + 1);
        }
    }
}

/*
public int countHomogenous(String s) {
    int res = 0, cur = 0, count = 0, mod = 1_000_000_007;
    for (int i = 0; i < s.length(); ++i) {
        count = s.charAt(i) == cur ? count + 1 : 1;
        cur = s.charAt(i);
        res = (res + count) % mod;
    }
    return res;
}
 */
