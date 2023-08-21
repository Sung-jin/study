package leetcode.greedy;

/*
Q.459. Repeated Substring Pattern

Given a string s, check if it can be constructed by taking a substring of it and appending multiple copies of the substring together.
 */
public class RepeatedSubstringPattern {
    public boolean repeatedSubstringPattern(String s) {
        for (int i = 1; i <= s.length() / 2; i++) {
            if (checkRepeatedSubstringPattern(s, i)) return true;
        }
        return false;
    }

    private boolean checkRepeatedSubstringPattern(String s, int length) {
        if (s.length() % length != 0) return false;

        String pattern = s.substring(0, length);

        for (int i = 1; i < (s.length() / length); i++) {
            String target = s.substring(length * i, length * i + length);

            if (!pattern.equals(target)) return false;
        }

        return true;
    }
}

/*
public boolean repeatedSubstringPattern(String str) {
	int l = str.length();
	for(int i=l/2;i>=1;i--) {
		if(l%i==0) {
			int m = l/i;
			String subS = str.substring(0,i);
			StringBuilder sb = new StringBuilder();
			for(int j=0;j<m;j++) {
				sb.append(subS);
			}
			if(sb.toString().equals(str)) return true;
		}
	}
	return false;
}
 */