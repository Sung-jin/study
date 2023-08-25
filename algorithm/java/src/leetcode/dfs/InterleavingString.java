package leetcode.dfs;

/*
Q.97 Interleaving String
Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.

An interleaving of two strings s and t is a configuration where s and t are divided into n and m
substrings
 respectively, such that:

s = s1 + s2 + ... + sn
t = t1 + t2 + ... + tm
|n - m| <= 1
The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
Note: a + b is the concatenation of strings a and b.
 */
public class InterleavingString {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.isEmpty() && s2.isEmpty() && s3.isEmpty()) return true;
        else if (s1.length() + s2.length() != s3.length()) return false;

        return checkInterleave(s1.toCharArray(), s2.toCharArray(), s3.toCharArray(), 0, 0, 0);
    }

    private boolean checkInterleave(char[] a, char[] b, char[] target, int aIndex, int bIndex, int targetIndex) {
        Character left = a.length > aIndex ? a[aIndex] : null, right = b.length > bIndex ? b[bIndex] : null;
        char targetChar = target[targetIndex];
        boolean isInterleave = false;

        if (target.length -1 == targetIndex) {
            if (left != null && left == targetChar) return true;
            return right != null && right == targetChar;
        }
        if (left != null && left == targetChar) isInterleave = checkInterleave(a, b, target, aIndex + 1, bIndex, targetIndex + 1);
        if (!isInterleave && right != null && right == targetChar) isInterleave = checkInterleave(a, b, target, aIndex, bIndex + 1, targetIndex + 1);

        return isInterleave;
    }
}

/*
public boolean isInterleave(String s1, String s2, String s3) {

    if ((s1.length()+s2.length())!=s3.length()) return false;

    boolean[][] matrix = new boolean[s2.length()+1][s1.length()+1];

    matrix[0][0] = true;

    for (int i = 1; i < matrix[0].length; i++){
        matrix[0][i] = matrix[0][i-1]&&(s1.charAt(i-1)==s3.charAt(i-1));
    }

    for (int i = 1; i < matrix.length; i++){
        matrix[i][0] = matrix[i-1][0]&&(s2.charAt(i-1)==s3.charAt(i-1));
    }

    for (int i = 1; i < matrix.length; i++){
        for (int j = 1; j < matrix[0].length; j++){
            matrix[i][j] = (matrix[i-1][j]&&(s2.charAt(i-1)==s3.charAt(i+j-1)))
                    || (matrix[i][j-1]&&(s1.charAt(j-1)==s3.charAt(i+j-1)));
        }
    }

    return matrix[s2.length()][s1.length()];

}
 */