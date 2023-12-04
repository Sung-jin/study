package leetcode.bruteForce;

/*
Q.2264. Largest 3-Same-Digit Number in String
You are given a string num representing a large integer. An integer is good if it meets the following conditions:

It is a substring of num with length 3.
It consists of only one unique digit.
Return the maximum good integer as a string or an empty string "" if no such integer exists.

Note:

A substring is a contiguous sequence of characters within a string.
There may be leading zeroes in num or a good integer.
 */
public class Largest3SameDigitNumberInString {
    public String largestGoodInteger(String num) {
        int answer = -1;
        char[] nums = num.toCharArray();
        for (int i = 0; i < nums.length;) {
            int target = Character.getNumericValue(nums[i]);

            if (answer >= target) {
                i++;
                continue;
            }

            int count = 1;
            i++;

            while(i < nums.length) {
                if (target != Character.getNumericValue(nums[i])) break;
                else count++;
                i++;
            }

            if (count > 2) answer = target;
        }

        return answer != -1 ? String.valueOf(answer) + answer + answer : "";
    }
}

/*
public String largestGoodInteger(String num) {
    int result = -1;
    for (int i = 0; i + 2 < num.length(); i++) {
        if (num.charAt(i) == num.charAt(i + 1) && num.charAt(i) == num.charAt(i + 2)) {
            result = Math.max(result, num.charAt(i) - '0');
        }
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 3; i++) {
        builder.append((char)(48 + result));
    }
    return result == -1 ? "" : builder.toString();
}
 */