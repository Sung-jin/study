package highScoreKit.hash;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 전화번호부에 적힌 전화번호 중, 한 번호가 다른 번호의 접두어인 경우가 있는지 확인하려 합니다.
 * 전화번호가 다음과 같을 경우, 구조대 전화번호는 영석이의 전화번호의 접두사입니다.
 *
 * 구조대 : 119
 * 박준영 : 97 674 223
 * 지영석 : 11 9552 4421
 * 전화번호부에 적힌 전화번호를 담은 배열 phone_book 이 solution 함수의 매개변수로 주어질 때, 어떤 번호가 다른 번호의 접두어인 경우가 있으면 false를 그렇지 않으면 true를 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한 사항
 * phone_book의 길이는 1 이상 1,000,000 이하입니다.
 * 각 전화번호의 길이는 1 이상 20 이하입니다.
 * 같은 전화번호가 중복해서 들어있지 않습니다.
 */
public class PhoneNumberList {
    public boolean solution(String[] phone_book) {
        for (int i = 0; i < phone_book.length; i++) {
            if (isPrefixPhoneNumber(phone_book, i)) {
                return false;
            }
        }

        return true;
    }

    private boolean isPrefixPhoneNumber(String[] source, int targetIndex) {
        ArrayList<String> original = new ArrayList<>(Arrays.asList(source));
        String target = original.remove(targetIndex);
        return original.stream()
                .map(value -> value.substring(0, Math.min(target.length(), value.length())))
                .anyMatch(value -> value.equals(target));
    }
}

/*
class Solution {
    // 답안 (1)
    // 자료구조/알고리즘: 정렬
    // 시간 복잡도: O(nlogn + n * m)
    // 공간 복잡도: O(1)
    public boolean solution(String[] phoneBook) {
        Arrays.sort(phoneBook);
        for (int i = 0; i < phoneBook.length - 1; i++) {
            if (phoneBook[i + 1].startsWith(phoneBook[i])) {
                return false;
            }
        }
        return true;
    }

    // 답안 (2)
    // 자료구조/알고리즘: 해시
    // 시간 복잡도: O(n * m)
    // 공간 복잡도: O(n)
    public boolean solution(String[] phoneBook) {
        Set<String> phoneNumbers = Set.of(phoneBook);
        for (String phoneNumber : phoneBook) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                String prefix = phoneNumber.substring(0, i);
                if (phoneNumbers.contains(prefix)) {
                    return false;
                }
            }
        }
        return true;
    }
}
 */
