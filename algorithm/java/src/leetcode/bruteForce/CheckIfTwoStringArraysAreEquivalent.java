package leetcode.bruteForce;

/*
Q.1662. Check If Two String Arrays are Equivalent
Given two string arrays word1 and word2, return true if the two arrays represent the same string, and false otherwise.

A string is represented by an array if the array elements concatenated in order forms the string.
 */
public class CheckIfTwoStringArraysAreEquivalent {
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder();
        for (String s : word1) {
            sb1.append(s);
        }
        for (String s : word2) {
            sb2.append(s);
        }

        return sb1.toString().contentEquals(sb2);
    }
}

/*
public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
	int p1 = 0, p2 = 0; // inner pointer
	int w1 = 0, w2 = 0; // outer pointer

	while (w1 < word1.length && w2 < word2.length) {
		String curr1 = word1[w1], curr2 = word2[w2];

		if (curr1.charAt(p1) != curr2.charAt(p2)) return false;

		if (p1 < curr1.length() - 1) {
			p1++;
		} else {
			p1 = 0;
			w1++;
		}

		if (p2 < curr2.length() - 1) {
			p2++;
		} else {
			p2 = 0;
			w2++;
		}
	}

	return w1 == word1.length && w2 == word2.length;
}
 */