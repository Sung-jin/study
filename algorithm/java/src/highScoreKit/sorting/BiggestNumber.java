package highScoreKit.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/*
0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요.

예를 들어, 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰 수는 6210입니다.

0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.

제한 사항
numbers의 길이는 1 이상 100,000 이하입니다.
numbers의 원소는 0 이상 1,000 이하입니다.
정답이 너무 클 수 있으니 문자열로 바꾸어 return 합니다.
 */
public class BiggestNumber {
    public String solution(int[] numbers) {
        return Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return check(o1, o2, 0);
                    }

                    int check(String o1, String o2, int index) {
                        int left = o1.charAt(Math.min(o1.length() - 1, index));
                        int right = o2.charAt(Math.min(o2.length() - 1, index));

                        if (left == right) {
                            if (o1.length() <= index + 1 && o2.length() <= index + 1) return o1.length() - o2.length();
                            else return check(o1, o2, index + 1);
                        } else return right - left;
                    }
                })
                .collect(Collectors.joining());
    }
}

/*
// 자료구조/알고리즘: 정렬
// 시간 복잡도: O(n + nlogn)
// 공간 복잡도: O(n)

class Solution {
    public String solution(int[] numbers) {
        String[] strNumbers = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            strNumbers[i] = String.valueOf(numbers[i]);
        }
        Arrays.sort(strNumbers, (a, b) -> (b + a).compareTo(a + b));

        if ("0".equals(strNumbers[0])) {
            return "0";
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < strNumbers.length; i++) {
            answer.append(strNumbers[i]);
        }
        return answer.toString();
    }
}
 */
