package leetcode.bruteForce;

/*
Q.880. Decoded String at Index
You are given an encoded string s. To decode the string to a tape, the encoded string is read one character at a time and the following steps are taken:

If the character read is a letter, that letter is written onto the tape.
If the character read is a digit d, the entire current tape is repeatedly written d - 1 more times in total.
Given an integer k, return the kth letter (1-indexed) in the decoded string.
 */
public class DecodedStringAtIndex {
    public String decodeAtIndex(String s, int k) {
        StringBuilder tape = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (tape.length() > k) return getStringByIndex(tape, k);

            if (Character.isDigit(c)) repeated(tape, Character.getNumericValue(c));
            else tape.append(c);
        }

        return getStringByIndex(tape, k);
    }

    private void repeated(StringBuilder original, int count) {
        String origin = original.toString();
        original.setLength(0);

        for (int i = 0; i < count; i++) {
            original.append(origin);
        }
    }

    private String getStringByIndex(StringBuilder sb, int index) {
        return String.valueOf(sb.toString().charAt(index - 1));
    }
}

/*
public String decodeAtIndex(String S, int K) {
    int i;
    long N = 0;
    for (i = 0; N < K; i++) {
        N = Character.isDigit(S.charAt(i)) ? N * (S.charAt(i) - '0') : N + 1;
    }
    for (i--; i > 0; i--) {
        if (Character.isDigit(S.charAt(i))) {
            N /= S.charAt(i) - '0';
            K %= N;
        }
        else {
            if (K % N == 0) {
                break;
            }
            N--;
        }
    }
    return Character.toString(S.charAt(i));
}
 */