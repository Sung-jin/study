package leetcode.greedy;

import java.util.*;

/*
Q.139
Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.

Note that the same word in the dictionary may be reused multiple times in the segmentation.
 */
public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        wordDict.sort((a, b) -> b.length() - a.length());

        return helper(s, wordDict);
    }

    private boolean helper(String s, List<String> wordDict) {
        if (s.length() == 0) return true;

        for (String word : wordDict) {
            boolean isPossible = false;

            if (s.indexOf(word) == 0) {
                isPossible = helper(s.substring(word.length()), wordDict);
            }

            if (isPossible) return true;
        }

        return false;
    }
}

/*
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int len = s.length();
        boolean[] dp = new boolean[len];

        for (int i = 0; i < len; i++) {
            for (String word : wordDict) {
                if (word.length() <= i + 1 && s.substring(i - word.length() + 1, i + 1).equals(word)) {
                    int index = i - word.length();
                    if (index < 0)  {
                        dp[i] =  true;
                    } else {
                        dp[i] = dp[index];
                    }
                    if(dp[i]) break;
                }
            }
        }

        return dp[len - 1];
    }
    // 앞에서부터 dict 에 있는 단어가 있으면, 해당 index 부분에 true 를 하고,
    // dict 단어보다 큰 길이 이후는, 이전에 단어에 있는지 여부를 만든 dp 에 현재 체크하는 word 와 같은게 있으면,
    // 그 word 를 제외한 단어가 이전 dp 에 만들수 있는 단어인지 여부로 생성할 수 있는지 여부를 체크할 수 있음
}
 */