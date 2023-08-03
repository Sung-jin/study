package leetcode.greedy;

import java.util.*;

/*
Q.17 Letter Combinations of a Phone Number
Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 */
public class LetterCombinationsOfAPhoneNumber {

    private static final Map<Character, char[]> DIAL_LETTERS = new HashMap<>();
    private Map<String, List<String>> cache = new HashMap<>();

    static {
        DIAL_LETTERS.put('2', new char[]{'a', 'b', 'c'});
        DIAL_LETTERS.put('3', new char[]{'d', 'e', 'f'});
        DIAL_LETTERS.put('4', new char[]{'g', 'h', 'i'});
        DIAL_LETTERS.put('5', new char[]{'j', 'k', 'l'});
        DIAL_LETTERS.put('6', new char[]{'m', 'n', 'o'});
        DIAL_LETTERS.put('7', new char[]{'p', 'q', 'r', 's'});
        DIAL_LETTERS.put('8', new char[]{'t', 'u', 'v'});
        DIAL_LETTERS.put('9', new char[]{'w', 'x', 'y', 'z'});
    }

    public List<String> letterCombinations(String digits) {
        if (digits.isEmpty()) return Collections.emptyList();

        if (!cache.containsKey(digits)) initCombinations(digits);

        return cache.get(digits);
    }

    private void initCombinations(String digits) {
        if (cache.containsKey(digits) || digits.isEmpty()) return;

        String beforeDigits = digits.substring(0, digits.length() - 1);

        initCombinations(beforeDigits);

        List<String> letters = cache.get(beforeDigits);
        List<String> nextLetters = new ArrayList<>();

        for (char dial: DIAL_LETTERS.get(digits.charAt(digits.length() - 1))) {
            if (digits.length() == 1) nextLetters.add(String.valueOf(dial));
            else {
                for (String letter : letters) {
                    nextLetters.add(letter + dial);
                }
            }
        }

        cache.put(digits, nextLetters);
    }
}

/*
public List<String> letterCombinations(String digits) {
    LinkedList<String> ans = new LinkedList<String>();
    if(digits.isEmpty()) return ans;
    String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    ans.add("");
    while(ans.peek().length()!=digits.length()){
        String remove = ans.remove();
        String map = mapping[digits.charAt(remove.length())-'0'];
        for(char c: map.toCharArray()){
            ans.addLast(remove+c);
        }
    }
    return ans;
}
 */
