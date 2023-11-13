package leetcode.sorting;

import java.util.*;

/*
Q.2785. Sort Vowels in a String
Given a 0-indexed string s, permute s to get a new string t such that:

All consonants remain in their original places. More formally, if there is an index i with 0 <= i < s.length such that s[i] is a consonant, then t[i] = s[i].
The vowels must be sorted in the nondecreasing order of their ASCII values. More formally, for pairs of indices i, j with 0 <= i < j < s.length such that s[i] and s[j] are vowels, then t[i] must not have a higher ASCII value than t[j].
Return the resulting string.

The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in lowercase or uppercase. Consonants comprise all letters that are not vowels.
 */
public class SortVowelsInAString {
    private final static Map<Character, Integer> VOWEL_INDEX = new HashMap<>();
    static {
        VOWEL_INDEX.put('A', 1);
        VOWEL_INDEX.put('E', 2);
        VOWEL_INDEX.put('I', 3);
        VOWEL_INDEX.put('O', 4);
        VOWEL_INDEX.put('U', 5);
        VOWEL_INDEX.put('a', 6);
        VOWEL_INDEX.put('e', 7);
        VOWEL_INDEX.put('i', 8);
        VOWEL_INDEX.put('o', 9);
        VOWEL_INDEX.put('u', 10);
    }

    public String sortVowels(String s) {
        StringBuilder answer = new StringBuilder();
        char[] chars = s.toCharArray();
        List<Integer> vowelIndex = new ArrayList<>();
        List<Character> vowels = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (isVowel(chars[i])) {
                vowelIndex.add(i);
                vowels.add(chars[i]);
            }
        }
        vowels.sort(Comparator.comparingInt(VOWEL_INDEX::get));

        for (int i = 0; i < vowelIndex.size(); i++) {
            int index = vowelIndex.get(i);
            chars[index] = vowels.get(i);
        }
        for (char c : chars) {
            answer.append(c);
        }

        return answer.toString();
    }

    private boolean isVowel(char c) {
        for (char vowel : VOWEL_INDEX.keySet()) {
            if (c == vowel) return true;
        }
        return false;
    }
}

/*
class Solution {
    public String sortVowels(String s) {
        // Step 1: Collect vowels and sort them in descending order
        List<Character> vowels = new ArrayList<>();
        for (char c : s.toCharArray()) {
            if ("aeiouAEIOU".indexOf(c) != -1) {
                vowels.add(c);
            }
        }
        Collections.sort(vowels, Collections.reverseOrder());

        // Step 2: Construct the answer string by replacing vowels in sorted order
        StringBuilder result = new StringBuilder();
        for (char c : s.toCharArray()) {
            if ("aeiouAEIOU".indexOf(c) != -1) {
                result.append(vowels.get(vowels.size() - 1));
                vowels.remove(vowels.size() - 1);
            } else {
                result.append(c);
            }
        }

        // Step 3: Return the final string
        return result.toString();
    }
}
 */