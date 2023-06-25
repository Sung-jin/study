package highScoreKit.greedy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
어떤 숫자에서 k개의 수를 제거했을 때 얻을 수 있는 가장 큰 숫자를 구하려 합니다.

예를 들어, 숫자 1924에서 수 두 개를 제거하면 [19, 12, 14, 92, 94, 24] 를 만들 수 있습니다. 이 중 가장 큰 숫자는 94 입니다.

문자열 형식으로 숫자 number와 제거할 수의 개수 k가 solution 함수의 매개변수로 주어집니다. number에서 k 개의 수를 제거했을 때 만들 수 있는 수 중 가장 큰 숫자를 문자열 형태로 return 하도록 solution 함수를 완성하세요.

제한 조건
number는 2자리 이상, 1,000,000자리 이하인 숫자입니다.
k는 1 이상 number의 자릿수 미만인 자연수입니다.
 */
public class BigNumber {
    public String solution(String number, int k) {
        List<Integer> numbers = new ArrayList<>();
        char[] splitNumbers = number.toCharArray();
        int index = 0;

        for (int i = number.length() - k - 1; i >= 0; i--) {
            int lastIndex = splitNumbers.length - i;
            int nowMaxValue = 0;

            for (int j = index; j < lastIndex; j++) {
                int nowValue = Character.getNumericValue(splitNumbers[j]);

                if (nowValue > nowMaxValue) {
                    index = j + 1;
                    nowMaxValue = nowValue;
                }

                if (nowMaxValue == 9) {
                    break;
                }
            }

            numbers.add(nowMaxValue);
        }

        return numbers.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}

/*
// 자료구조/알고리즘: 탐욕법
// 시간 복잡도: O(n + k)
// 공간 복잡도: O(n - k)

class Solution {
    public String solution(String number, int k) {
        Stack<Character> digits = new Stack<>(); // O(n - k) 공간
        int canRemove = k;
        for (int i = 0; i < number.length(); i++) { // O(n + k) 시간
            char currentDigit = number.charAt(i);
            while (!digits.isEmpty() && currentDigit > digits.peek() && canRemove > 0) {
                digits.pop();
                canRemove--;
            }
            digits.push(currentDigit);
        }

        char[] maxNumber = new char[number.length() - k]; // O(n - k) 공간
        for (int i = 0; i < maxNumber.length; i++) {
            maxNumber[i] = digits.get(i);
        }
        return String.valueOf(maxNumber);
    }
}
 */