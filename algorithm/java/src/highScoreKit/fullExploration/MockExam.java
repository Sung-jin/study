package highScoreKit.fullExploration;

import java.util.Arrays;
import java.util.Objects;

/*
수포자는 수학을 포기한 사람의 준말입니다. 수포자 삼인방은 모의고사에 수학 문제를 전부 찍으려 합니다. 수포자는 1번 문제부터 마지막 문제까지 다음과 같이 찍습니다.

1번 수포자가 찍는 방식: 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, ...
2번 수포자가 찍는 방식: 2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5, ...
3번 수포자가 찍는 방식: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...

1번 문제부터 마지막 문제까지의 정답이 순서대로 들은 배열 answers가 주어졌을 때, 가장 많은 문제를 맞힌 사람이 누구인지 배열에 담아 return 하도록 solution 함수를 작성해주세요.

제한 조건
시험은 최대 10,000 문제로 구성되어있습니다.
문제의 정답은 1, 2, 3, 4, 5중 하나입니다.
가장 높은 점수를 받은 사람이 여럿일 경우, return하는 값을 오름차순 정렬해주세요.
 */
public class MockExam {
    private static final int[] FIRST_ANSWER_LIST = new int[]{1, 2, 3, 4, 5};
    private static final int[] SECOND_ANSWER_LIST = new int[]{2, 1, 2, 3, 2, 4, 2, 5};
    private static final int[] THIRD_ANSWER_LIST = new int[]{3, 3, 1, 1, 2, 2, 4, 4, 5, 5};

    public int[] solution(int[] answers) {
        int firstCorrectCount = 0, secondCorrectCount = 0, thirdCorrectCount = 0;

        for (int i = 0; i < answers.length; i++) {
            if (FIRST_ANSWER_LIST[i % FIRST_ANSWER_LIST.length] == answers[i]) firstCorrectCount++;
            if (SECOND_ANSWER_LIST[i % SECOND_ANSWER_LIST.length] == answers[i]) secondCorrectCount++;
            if (THIRD_ANSWER_LIST[i % THIRD_ANSWER_LIST.length] == answers[i]) thirdCorrectCount++;
        }

        int maxCount = Math.max(Math.max(firstCorrectCount, secondCorrectCount), thirdCorrectCount);

        return Arrays.asList(
                (maxCount == firstCorrectCount) ? 1 : null,
                (maxCount == secondCorrectCount) ? 2 : null,
                (maxCount == thirdCorrectCount) ? 3 : null
        ).stream().filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .toArray();
    }
}

/*
- 자료구조/알고리즘: 완전탐색
- 시간 복잡도: O(n)
- 공간 복잡도: O(1)

class Solution {
    public int[] solution(int[] answers) {
        int[] supoja1Answers = {1, 2, 3, 4, 5};
        int[] supoja2Answers = {2, 1, 2, 3, 2, 4, 2, 5};
        int[] supoja3Answers = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};

        int[] correctAnswers = new int[3]; // O(3) == O(1) 공간
        for (int i = 0; i < answers.length; i++) { // O(n) 시간
            if (answers[i] == supoja1Answers[i % supoja1Answers.length]) {
                correctAnswers[0]++;
            }
            if (answers[i] == supoja2Answers[i % supoja2Answers.length]) {
                correctAnswers[1]++;
            }
            if (answers[i] == supoja3Answers[i % supoja3Answers.length]) {
                correctAnswers[2]++;
            }
        }
        int maxCorrectAnswers = Math.max(correctAnswers[0], Math.max(correctAnswers[1], correctAnswers[2]));
        List<Integer> mostCorrect = new ArrayList<>();
        for (int i = 0; i < 3; i++) { // O(3) == O(1) 시간
            if (correctAnswers[i] == maxCorrectAnswers) {
                mostCorrect.add(i + 1);
            }
        }
        return mostCorrect.stream().mapToInt(Integer::intValue).toArray();
    }
}
 */