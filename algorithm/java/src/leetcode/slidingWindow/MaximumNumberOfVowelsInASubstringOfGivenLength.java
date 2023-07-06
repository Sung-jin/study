package leetcode.slidingWindow;

import java.util.HashSet;
import java.util.Set;

/*
Q.1456

Given a string s and an integer k, return the maximum number of vowel letters in any substring of s with length k.

Vowel letters in English are 'a', 'e', 'i', 'o', and 'u'.
 */
public class MaximumNumberOfVowelsInASubstringOfGivenLength {
    private final static Set<Character> VOWELS = new HashSet<>();

    static {
        // s consists of lowercase English letters.
        VOWELS.add('a');
        VOWELS.add('e');
        VOWELS.add('i');
        VOWELS.add('o');
        VOWELS.add('u');
    }

    public int maxVowels(String s, int k) {
        int maxContinuousVowelCount = 0;

        for (int i = 0; i < k; i++) {
            if (VOWELS.contains(s.charAt(i))) maxContinuousVowelCount++;
        }

        int continuousVowelCount = maxContinuousVowelCount;

//        for (int i = k; i < s.toCharArray().length; i++) {
//        와... 좋은걸 배웠네. toCharArray() 는 copyOfRange 로 전체 수 만큼 loop 을 도는데. for 는 각 룹마다 돌기 때문에, n^2 이 되어버리네.
        for (int i = k; i < s.length(); i++) {
            if (maxContinuousVowelCount == k) return k;
            if (VOWELS.contains(s.charAt(i - k))) continuousVowelCount--;
            if (VOWELS.contains(s.charAt(i))) continuousVowelCount++;

            maxContinuousVowelCount = Math.max(maxContinuousVowelCount, continuousVowelCount);
        }

        return maxContinuousVowelCount;
    }
}

/*
public int maxVowels(String s, int k) {
        int ans = 0;
        // Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        var vowels = Set.of('a', 'e', 'i', 'o', 'u'); // Java 11 Collection factory method, credit to @Sithis
        for (int i = 0, winCnt = 0; i < s.length(); ++i) {
            if (vowels.contains(s.charAt(i))) {
                ++winCnt;
            }
            if (i >= k && vowels.contains(s.charAt(i - k))) {
                --winCnt;
            }
            ans = Math.max(winCnt, ans);
        }
        return ans;
    }
 */