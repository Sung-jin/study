package leetcode.bruteForce;

/*
Q.779. K-th Symbol in Grammar
We build a table of n rows (1-indexed). We start by writing 0 in the 1st row. Now in every subsequent row, we look at the previous row and replace each occurrence of 0 with 01, and each occurrence of 1 with 10.

For example, for n = 3, the 1st row is 0, the 2nd row is 01, and the 3rd row is 0110.
Given two integer n and k, return the kth (1-indexed) symbol in the nth row of a table of n rows.
 */
public class KthSymbolInGrammar {
    public int kthGrammar(int n, int k) {
        if (n == 1) return 0;
        StringBuilder sb = new StringBuilder();

        sb.append("0");

        return createGrammar(sb, k - 1);
    }

    private int createGrammar(StringBuilder sb, int index) {
        int length = sb.length();
        for (int i = 0; i < length; i++) {
            if (sb.length() > index) return Character.getNumericValue(sb.charAt(index));

            char c = sb.charAt(i) == '0' ? '1' : '0';
            sb.append(c);
        }

        return createGrammar(sb, index);
    }
}

/*
class Solution {
    public int kthGrammar(int N, int K) {
        if(N==1) return 0;
        if(K%2==0){
            if (kthGrammar(N-1,K/2)==0) return 1;
            else return 0;
        }
        else{
            if(kthGrammar(N-1,(K+1)/2)==0) return 0;
            else return 1;
        }
    }
}
 */
