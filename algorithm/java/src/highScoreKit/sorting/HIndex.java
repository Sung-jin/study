package highScoreKit.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다. 어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다. 위키백과1에 따르면, H-Index는 다음과 같이 구합니다.

어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상이고 나머지 논문이 h번 이하 인용되었다면 h의 최댓값이 이 과학자의 H-Index입니다.

어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때, 이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.

제한사항
과학자가 발표한 논문의 수는 1편 이상 1,000편 이하입니다.
논문별 인용 횟수는 0회 이상 10,000회 이하입니다.
 */
public class HIndex {
    public int solution(int[] citations) {
        int h = 0;
        List<Integer> reverseSortingCitations = Arrays.stream(citations)
                .boxed()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        while (h < citations.length + 1) {
            int overHIndexCitationsCount = 0;

            for (int citation : reverseSortingCitations) {
                if (citation >= h) overHIndexCitationsCount++;
                else break;
            }

            if (h > overHIndexCitationsCount) return h - 1;
            else h++;
        }

        return citations.length;
    }
}

/*
- 자료구조/알고리즘: 정렬
- 시간 복잡도: O(n + nlogn)
- 공간 복잡도: O(1)

class Solution {
    public int solution(int[] citations) {
        Arrays.sort(citations); // O(nlogn) 시간
        for (int i = 0; i < citations.length; i++) { // O(n) 시간
            int hIndex = citations.length - i;
            if (citations[i] >= hIndex){
                return hIndex;
            }
        }
        return 0;
    }
}
 */
