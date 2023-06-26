package highScoreKit.dynamicProgramming;

import java.util.Arrays;
import java.util.stream.Stream;

/*
삼각형의 꼭대기에서 바닥까지 이어지는 경로 중, 거쳐간 숫자의 합이 가장 큰 경우를 찾아보려고 합니다. 아래 칸으로 이동할 때는 대각선 방향으로 한 칸 오른쪽 또는 왼쪽으로만 이동 가능합니다.

삼각형의 정보가 담긴 배열 triangle이 매개변수로 주어질 때, 거쳐간 숫자의 최댓값을 return 하도록 solution 함수를 완성하세요.

제한사항
삼각형의 높이는 1 이상 500 이하입니다.
삼각형을 이루고 있는 숫자는 0 이상 9,999 이하의 정수입니다.
 */
public class IntegerTriangle {
    public int solution(int[][] triangle) {
        int[] combinations = new int[(int)Math.pow(2, triangle.length) + 1];

        for (int i = 0; i < triangle.length; i++) {
            int start = (int) Math.pow(2, i);
            int end = (int) Math.pow(2, i + 1) - 1;

            for (int j = start; j <= end; j++) {
                if (i == 0) combinations[j] = triangle[0][0];
                else {
//                    combinations[j] = combinations[j /2] + triangle[i][(j - start) / 2];
                    // 이전에 계산된 인덱스를 구할 방법을 모르겠음.
                }
            }
        }

        return Arrays.stream(combinations).max().getAsInt();
    }
}
