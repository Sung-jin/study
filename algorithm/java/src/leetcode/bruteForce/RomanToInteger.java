package leetcode.bruteForce;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Q.13

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, 2 is written as II in Roman numeral, just two ones added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9.
X can be placed before L (50) and C (100) to make 40 and 90.
C can be placed before D (500) and M (1000) to make 400 and 900.
Given a roman numeral, convert it to an integer.
 */
public class RomanToInteger {

    private static final Map<String, Integer> ROMAN_TO_NUMBER = new HashMap<>();
    private static final Set<String> MIX_PREFIX = new HashSet<>();

    static {
        ROMAN_TO_NUMBER.put("I", 1);
        ROMAN_TO_NUMBER.put("IV", 4);
        ROMAN_TO_NUMBER.put("IX", 9);
        ROMAN_TO_NUMBER.put("V", 5);
        ROMAN_TO_NUMBER.put("X", 10);
        ROMAN_TO_NUMBER.put("XL", 40);
        ROMAN_TO_NUMBER.put("XC", 90);
        ROMAN_TO_NUMBER.put("L", 50);
        ROMAN_TO_NUMBER.put("C", 100);
        ROMAN_TO_NUMBER.put("CD", 400);
        ROMAN_TO_NUMBER.put("CM", 900);
        ROMAN_TO_NUMBER.put("D", 500);
        ROMAN_TO_NUMBER.put("M", 1000);

        MIX_PREFIX.add("I");
        MIX_PREFIX.add("X");
        MIX_PREFIX.add("C");
    }

    public int romanToInt(String s) {
        // s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
        int result = 0;
        String[] splitS = s.split("");

        for (int i = 0; i < splitS.length; i++) {
            if (MIX_PREFIX.contains(splitS[i]) && i < splitS.length - 1) {
                if (ROMAN_TO_NUMBER.containsKey(splitS[i] + splitS[i + 1])) {
                    result += ROMAN_TO_NUMBER.get(splitS[i] + splitS[i + 1]);
                    i++;
                } else {
                    result += ROMAN_TO_NUMBER.get(splitS[i]);
                }
            } else {
                result += ROMAN_TO_NUMBER.get(splitS[i]);
            }
        }

        return result;
    }
}

/*
public int romanToInt(String s) {
    int ans = 0, num = 0;
    for (int i = s.length()-1; i >= 0; i--) {
    switch(s.charAt(i)) {
        case 'I': num = 1; break;
        case 'V': num = 5; break;
        case 'X': num = 10; break;
        case 'L': num = 50; break;
        case 'C': num = 100; break;
        case 'D': num = 500; break;
        case 'M': num = 1000; break;
    }
    if (4 * num < ans) ans -= num;
        else ans += num;
    }
    return ans;
}
 */