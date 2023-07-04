package leetcode.greedy;

import java.util.HashMap;
import java.util.Map;

/*
Q.409
Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.

Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
 */
public class LongestPalindrome {
    public int longestPalindrome(String s) {
        int longestPalindromeWordCount = 0;
        int oddCount = 0;
        Map<Character, Integer> wordCounts = new HashMap<>();

        for (char c : s.toCharArray()) {
            wordCounts.put(c, wordCounts.getOrDefault(c, 0) + 1);
        }

        for (Integer count : wordCounts.values()) {
            longestPalindromeWordCount += count;

            if (count % 2 == 1) oddCount++;
        }

        return longestPalindromeWordCount - (oddCount > 1 ? oddCount - 1 : 0);
    }
}

/*
public int longestPalindrome(String s) {
    if(s==null || s.length()==0) return 0;
    HashSet<Character> hs = new HashSet<Character>();
    int count = 0;
    for(int i=0; i<s.length(); i++){
        if(hs.contains(s.charAt(i))){
            hs.remove(s.charAt(i));
            count++;
        }else{
            hs.add(s.charAt(i));
        }
    }
    if(!hs.isEmpty()) return count*2+1;
    return count*2;
}
 */