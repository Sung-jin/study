package leetcode.greedy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
Q.316. Remove Duplicate Letters

Given a string s, remove duplicate letters so that every letter appears once and only once. You must make sure your result is
the smallest in lexicographical order among all possible results.

Lexicographically Smaller
A string a is lexicographically smaller than a string b if in the first position where a and b differ, string a has a letter that appears earlier in the alphabet than the corresponding letter in b.
If the first min(a.length, b.length) characters do not differ, then the shorter string is the lexicographically smaller one.
 */
public class RemoveDuplicateLetters {
    public String removeDuplicateLetters(String s) {
        if (s.isEmpty()) return "";

        Set<String> res = new HashSet<>();

        removeAndCombine(s.toCharArray(), 0, new ArrayList<>(), res);

        return res.stream()
                .sorted()
                .findFirst()
                .get();
    }

    private void removeAndCombine(char[] input, int index, List<Character> combine, Set<String> res) {
        if (index == input.length) {
            StringBuilder sb = new StringBuilder();

            for (Character c : combine) {
                sb.append(c);
            }

            res.add(sb.toString());

            return;
        }

        char target = input[index];

        if (combine.contains(target)) {
            removeAndCombine(input, index + 1, combine, res);
            combine.remove((Object) target);
        }

        combine.add(target);
        removeAndCombine(input, index + 1, combine, res);
    }
}

/*
class Solution {
    public String removeDuplicateLetters(String s) {
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++){
            lastIndex[s.charAt(i) - 'a'] = i; // track the lastIndex of character presence
        }

        boolean[] seen = new boolean[26]; // keep track seen
        Stack<Integer> st = new Stack();

        for (int i = 0; i < s.length(); i++) {
            int curr = s.charAt(i) - 'a';
            if (seen[curr]) continue; // if seen continue as we need to pick one char only
            while (!st.isEmpty() && st.peek() > curr && i < lastIndex[st.peek()]){
                seen[st.pop()] = false; // pop out and mark unseen
            }
            st.push(curr); // add into stack
            seen[curr] = true; // mark seen
        }

        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty())
            sb.append((char) (st.pop() + 'a'));
        return sb.reverse().toString();
    }
}
 */