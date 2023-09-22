package leetcode.slidingWindow;

/*
Q.392. Is Subsequence
Given two strings s and t, return true if s is a subsequence of t, or false otherwise.

A subsequence of a string is a new string that is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a subsequence of "abcde" while "aec" is not).
 */
public class IsSubsequence {
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        if (t.length() == 0) return false;

        int index = 0;
        char[] sChars = s.toCharArray(), tChars = t.toCharArray();

        for (int i = 0; i < sChars.length; i++) {
            boolean subsequenceChar = false;

            while (index < tChars.length && !subsequenceChar) {
                subsequenceChar = tChars[index] == sChars[i];
                index++;

                if (subsequenceChar) if (i == sChars.length - 1) return true;
            }
        }

        return false;
    }
}

/*
class Solution:
    def isSubsequence(self, s: str, t: str) -> bool:
        if len(s) > len(t):return False
        if len(s) == 0:return True
        subsequence=0
        for i in range(0,len(t)):
            if subsequence <= len(s) -1:
                print(s[subsequence])
                if s[subsequence]==t[i]:

                    subsequence+=1
        return  subsequence == len(s)
 */