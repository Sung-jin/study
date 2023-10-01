package leetcode.slidingWindow;

/*
Q.557. Reverse Words in a String III
Given a string s, reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.
 */
public class ReverseWordsInAStringIII {
    public String reverseWords(String s) {
        StringBuilder res = new StringBuilder();
        for (String splits : s.split(" ")) {
            res.append(reverse(splits));
            res.append(" ");
        }

        res.delete(res.length() - 1, res.length());

        return res.toString();
    }

    private String reverse(String s) {
        StringBuilder res = new StringBuilder();
        char[] chars = s.toCharArray();

        for (int i = chars.length - 1; i >= 0; i--) {
            res.append(chars[i]);
        }

        return res.toString();
    }
}

/*
public class Solution {
    public String reverseWords(String s) {
        char[] ca = s.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] != ' ') {   // when i is a non-space
                int j = i;
                while (j + 1 < ca.length && ca[j + 1] != ' ') { j++; } // move j to the end of the word
                reverse(ca, i, j);
                i = j;
            }
        }
        return new String(ca);
    }

    private void reverse(char[] ca, int i, int j) {
        for (; i < j; i++, j--) {
            char tmp = ca[i];
            ca[i] = ca[j];
            ca[j] = tmp;
        }
    }
}
 */
