package leetcode.bruteForce;

import java.util.HashMap;
import java.util.Map;

/*
Q. 859

Given two strings s and goal, return true if you can swap two letters in s so the result is equal to goal, otherwise, return false.

Swapping letters is defined as taking two indices i and j (0-indexed) such that i != j and swapping the characters at s[i] and s[j].

For example, swapping at indices 0 and 2 in "abcd" results in "cbad".
 */
public class BuddyStrings {
    public boolean buddyStrings(String s, String goal) {
        if (s.equals(goal)) {
            Map<Character, Integer> charCount = new HashMap<>();

            for (int i = 0; i < s.toCharArray().length; i++) {
                int count = charCount.getOrDefault(s.charAt(i), 0);

                if (count > 0) return true;
                else charCount.put(s.charAt(i), count + 1);
            }

            return false;
        } else if (s.length() != goal.length()) return false;

        boolean changed = false;
        Character notEqualCharFrom = null;
        Character notEqualCharTo = null;

        for (int i = 0; i < s.toCharArray().length; i++) {
            char from = s.charAt(i);
            char to = goal.charAt(i);

            if (from != to) {
                if (notEqualCharFrom == null) {
                    if (i == s.length() -1) return false;

                    notEqualCharFrom = from;
                    notEqualCharTo = to;
                }
                else if (changed || notEqualCharFrom != to || notEqualCharTo != from) return false;
                else changed = true;
            }
        }

        return changed;
    }
}

/*
public boolean buddyStrings(String A, String B) {
    if (A.length() != B.length()) return false;
    if (A.equals(B)) {
        Set<Character> s = new HashSet<Character>();
        for (char c : A.toCharArray()) s.add(c);
        return s.size() < A.length();
    }
    List<Integer> dif = new ArrayList<>();
    for (int i = 0; i < A.length(); ++i) if (A.charAt(i) != B.charAt(i)) dif.add(i);
    return dif.size() == 2 && A.charAt(dif.get(0)) == B.charAt(dif.get(1)) && A.charAt(dif.get(1)) == B.charAt(dif.get(0));
}
 */