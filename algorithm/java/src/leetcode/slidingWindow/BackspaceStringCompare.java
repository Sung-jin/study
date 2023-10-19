package leetcode.slidingWindow;

/*
Q.844. Backspace String Compare
Given two strings s and t, return true if they are equal when both are typed into empty text editors. '#' means a backspace character.

Note that after backspacing an empty text, the text will continue empty.
 */
public class BackspaceStringCompare {
    public boolean backspaceCompare(String s, String t) {
        StringBuilder sStrings = new StringBuilder(), tStrings = new StringBuilder();

        processing(sStrings, s.toCharArray());
        processing(tStrings, t.toCharArray());

        return sStrings.toString().contentEquals(tStrings);
    }

    private void processing(StringBuilder sb, char[] inputs) {
        for (char input : inputs) {
            if (input == '#') {
                if (sb.length() > 0) sb.delete(sb.length() - 1, sb.length());
            }
            else sb.append(input);
        }
    }
}

/*
public boolean backspaceCompare(String S, String T) {
    int i = S.length() - 1, j = T.length() - 1, back;
    while (true) {
        back = 0;
        while (i >= 0 && (back > 0 || S.charAt(i) == '#')) {
            back += S.charAt(i) == '#' ? 1 : -1;
            i--;
        }
        back = 0;
        while (j >= 0 && (back > 0 || T.charAt(j) == '#')) {
            back += T.charAt(j) == '#' ? 1 : -1;
            j--;
        }
        if (i >= 0 && j >= 0 && S.charAt(i) == T.charAt(j)) {
            i--;
            j--;
        } else {
            break;
        }
    }
    return i == -1 && j == -1;
}
 */
