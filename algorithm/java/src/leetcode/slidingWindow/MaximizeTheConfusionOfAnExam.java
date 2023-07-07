package leetcode.slidingWindow;

import java.util.LinkedList;
import java.util.Queue;

/*
Q. 2024
A teacher is writing a test with n true/false questions, with 'T' denoting true and 'F' denoting false. He wants to confuse the students by maximizing the number of consecutive questions with the same answer (multiple trues or multiple falses in a row).

You are given a string answerKey, where answerKey[i] is the original answer to the ith question. In addition, you are given an integer k, the maximum number of times you may perform the following operation:

Change the answer key for any question to 'T' or 'F' (i.e., set answerKey[i] to 'T' or 'F').
Return the maximum number of consecutive 'T's or 'F's in the answer key after performing the operation at most k times.
 */
public class MaximizeTheConfusionOfAnExam {
    public int maxConsecutiveAnswers(String answerKey, int k) {
        return Math.max(
                getConsecutiveCountAfterChangeAnswer(answerKey, 'T', k),
                getConsecutiveCountAfterChangeAnswer(answerKey, 'F', k)
        );
    }

    private int getConsecutiveCountAfterChangeAnswer(String answerKey, char target, int changeCount) {
        Queue<Integer> changedIndex = new LinkedList<>();
        int remainCount = changeCount;
        int maxConsecutiveCount = 0;
        int startIndex = 0;

        for (int i = 0; i < answerKey.length(); i++) {
            if (answerKey.charAt(i) != target) {
                changedIndex.offer(i + 1);
                if (remainCount == 0) startIndex = changedIndex.poll();
                else remainCount--;
            }

            maxConsecutiveCount = Math.max(maxConsecutiveCount, i - startIndex + 1);
        }

        return maxConsecutiveCount;
    }
}

/*
 public int characterReplacement(String s, int k) {
    int res = 0, maxf = 0, count[] = new int[128];
    for (int i = 0; i < s.length(); ++i) {
        maxf = Math.max(maxf, ++count[s.charAt(i)]);
        if (res - maxf < k)
            res++;
        else
            count[s.charAt(i - res)]--;
    }
    return res;
}

public int characterReplacement(String s, int k) {
    int maxf = 0, i = 0, n = s.length(), count[] = new int[26];
    for (int j = 0; j < n; ++j) {
        maxf = Math.max(maxf, ++count[s.charAt(j) - 'A']);
        if (j - i + 1 > maxf + k)
            --count[s.charAt(i++) - 'A'];
    }
    return n - i;
}
 */