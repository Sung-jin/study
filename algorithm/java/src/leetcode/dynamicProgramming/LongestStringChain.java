package leetcode.dynamicProgramming;

import java.util.*;

/*
Q.1048. Longest String Chain
You are given an array of words where each word consists of lowercase English letters.

wordA is a predecessor of wordB if and only if we can insert exactly one letter anywhere in wordA without changing the order of the other characters to make it equal to wordB.

For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1, where word1 is a predecessor of word2, word2 is a predecessor of word3, and so on. A single word is trivially a word chain with k == 1.

Return the length of the longest possible word chain with words chosen from the given list of words.
 */
public class LongestStringChain {

    private Map<String, List<String>> CHAIN_MAP;
    private Map<Integer, int[]> LENGTH_RANGE;
    private int MAX_CHAIN_SIZE;

    public int longestStrChain(String[] words) {
        Arrays.sort(words);
        CHAIN_MAP = new HashMap<>();
        LENGTH_RANGE = new HashMap<>();
        MAX_CHAIN_SIZE = 0;

        int lengthUnit = words[0].length();
        int start = 0;


        for (int i = 0; i < words.length; i++) {
            int nowLength = words[i].length();

            if (nowLength > lengthUnit) {
                LENGTH_RANGE.put(lengthUnit, new int[]{start, i - 1});
                lengthUnit = nowLength;
                start = i;
            } else if (i == words.length - 1) {
                LENGTH_RANGE.put(lengthUnit, new int[]{start, i});
            }
        }

        initChain(words);

        return MAX_CHAIN_SIZE;
    }

//    private int getMaxChainSize(String target, int size) {
//        List<String> nextWords = CHAIN_MAP.get(target);
//
//        if (nextWords == null) return size;
//
//        int maxSize = 0;
//
//    }

    private void initChain(String[] words) {
        for (String word : words) {
            int length = word.length();
            int[] range = LENGTH_RANGE.get(length + 1);
            List<String> chain = new ArrayList<>();

            if (range == null) continue;

            for (int i = range[0]; i <= range[1]; i++) {
                String target = words[i];

                if (isCPredecessor(word, target)) chain.add(target);
            }

            CHAIN_MAP.put(word, chain);
        }
    }

    private boolean isCPredecessor(String word, String target) {
        char[] wordChars = word.toCharArray(), targetChars = target.toCharArray();
        boolean useOtherWord = false;
        int index = 0;

        for (char wordChar : wordChars) {
            while (index < targetChars.length) {
                if (wordChar == targetChars[index]) {
                    index++;
                    continue;
                }
                if (useOtherWord) return false;

                useOtherWord = true;
                index++;
            }
        }

        return true;
    }
}

/*
public int longestStrChain(String[] words) {
    Map<String, Integer> dp = new HashMap<>();
    Arrays.sort(words, (a, b)->a.length() - b.length());
    int res = 0;
    for (String word : words) {
        int best = 0;
        for (int i = 0; i < word.length(); ++i) {
            String prev = word.substring(0, i) + word.substring(i + 1);
            best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
        }
        dp.put(word, best);
        res = Math.max(res, best);
    }
    return res;
}
 */