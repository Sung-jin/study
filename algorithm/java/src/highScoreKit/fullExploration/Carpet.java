package highScoreKit.fullExploration;

import java.util.ArrayList;
import java.util.List;

/*
Leo는 카펫을 사러 갔다가 중앙에는 노란색으로 칠해져 있고 테두리 1줄은 갈색으로 칠해져 있는 격자 모양 카펫을 봤습니다. <- 테두리는 1줄이라는 조건이 있었네 ㅡㅡ

Leo는 집으로 돌아와서 아까 본 카펫의 노란색과 갈색으로 색칠된 격자의 개수는 기억했지만, 전체 카펫의 크기는 기억하지 못했습니다.

Leo가 본 카펫에서 갈색 격자의 수 brown, 노란색 격자의 수 yellow가 매개변수로 주어질 때 카펫의 가로, 세로 크기를 순서대로 배열에 담아 return 하도록 solution 함수를 작성해주세요.

제한사항
갈색 격자의 수 brown은 8 이상 5,000 이하인 자연수입니다.
노란색 격자의 수 yellow는 1 이상 2,000,000 이하인 자연수입니다.
카펫의 가로 길이는 세로 길이와 같거나, 세로 길이보다 깁니다.
 */
public class Carpet {
    public int[] solution(int brown, int yellow) {
        List<Integer> totalCommonMultiple = findCommonMultiple(brown + yellow);
        List<Integer> yellowCommonMultiple = findCommonMultiple(yellow);

        return getSurroundedRange(totalCommonMultiple, yellowCommonMultiple);
    }

    private List<Integer> findCommonMultiple(int num) {
        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= num; i++) {
            if (num % i == 0) result.add(i);
        }

        return result;
    }

    private int[] getSurroundedRange(List<Integer> bigCommonMultiple, List<Integer> smallCommonMultiple) {
        int checkRange = bigCommonMultiple.size() / 2;
        int smallCheckRange = smallCommonMultiple.size() / 2;

        if (checkRange % 2 == 1) checkRange++;
        if (smallCheckRange % 2 == 1) smallCheckRange--;

        for (int i = 1; i < checkRange; i++) {
            int[] bigRange = getCommonMultipleByIndex(bigCommonMultiple, i);

            for (int j = 0; j <= smallCheckRange; j++) {
                int[] smallRange = getCommonMultipleByIndex(smallCommonMultiple, j);

                boolean possibleX = isPossibleSurrounded(bigRange[0], smallRange[0]);
                boolean possibleY = isPossibleSurrounded(bigRange[1], smallRange[1]);

                if (possibleX && possibleY) return new int[]{
                        Math.max(bigRange[0], bigRange[1]),
                        Math.min(bigRange[0], bigRange[1])
                };
            }
        }

        return new int[]{};
    }

    private int[] getCommonMultipleByIndex(List<Integer> commonMultiple, int index) {
        boolean isOdd = commonMultiple.size() % 2 == 1;
        int x = commonMultiple.get(index);
        int y = isOdd && commonMultiple.size() % 2 == index ? x : commonMultiple.get(commonMultiple.size() - index - 1);

        return new int[]{x, y};
    }

    private boolean isPossibleSurrounded(int big, int small) {
        boolean isBigOdd = big % 2 == 1;
        boolean isSmallOdd = small % 2 == 1;

        return isBigOdd == isSmallOdd && big - 2 == small;
    }
}

/*
- 자료구조/알고리즘: 완전탐색
- 시간 복잡도: O(n)
- 공간 복잡도: O(1)

class Solution {
    public int[] solution(int brown, int yellow) {
        int[] carpetSize = new int[2];
        int totalCount = brown + yellow;
        for (int width = 1; width < brown; width++) { // O(n) 시간, n == brown
            int height = totalCount / width;
            if ((width - 2) * (height - 2) == yellow) {
                carpetSize[0] = width;
                carpetSize[1] = height;
            }
        }
        return carpetSize;
    }
}
 */