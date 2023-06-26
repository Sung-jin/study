package highScoreKit.greedy;

import java.util.HashMap;
import java.util.Map;

/*
조이스틱으로 알파벳 이름을 완성하세요. 맨 처음엔 A로만 이루어져 있습니다.
ex) 완성해야 하는 이름이 세 글자면 AAA, 네 글자면 AAAA

조이스틱을 각 방향으로 움직이면 아래와 같습니다.

▲ - 다음 알파벳
▼ - 이전 알파벳 (A에서 아래쪽으로 이동하면 Z로)
◀ - 커서를 왼쪽으로 이동 (첫 번째 위치에서 왼쪽으로 이동하면 마지막 문자에 커서)
▶ - 커서를 오른쪽으로 이동 (마지막 위치에서 오른쪽으로 이동하면 첫 번째 문자에 커서)
예를 들어 아래의 방법으로 "JAZ"를 만들 수 있습니다.

- 첫 번째 위치에서 조이스틱을 위로 9번 조작하여 J를 완성합니다.
- 조이스틱을 왼쪽으로 1번 조작하여 커서를 마지막 문자 위치로 이동시킵니다.
- 마지막 위치에서 조이스틱을 아래로 1번 조작하여 Z를 완성합니다.
따라서 11번 이동시켜 "JAZ"를 만들 수 있고, 이때가 최소 이동입니다.
만들고자 하는 이름 name이 매개변수로 주어질 때, 이름에 대해 조이스틱 조작 횟수의 최솟값을 return 하도록 solution 함수를 만드세요.

제한 사항
name은 알파벳 대문자로만 이루어져 있습니다.
name의 길이는 1 이상 20 이하입니다.
 */
public class JoyStick {
    private static final Map<Character, Integer> MOVE_COUNT = new HashMap<>();
    static {
        MOVE_COUNT.put('A', 0);
        MOVE_COUNT.put('B', 1); MOVE_COUNT.put('Z', 1);
        MOVE_COUNT.put('C', 2); MOVE_COUNT.put('Y', 2);
        MOVE_COUNT.put('D', 3); MOVE_COUNT.put('X', 3);
        MOVE_COUNT.put('E', 4); MOVE_COUNT.put('W', 4);
        MOVE_COUNT.put('F', 5); MOVE_COUNT.put('V', 5);
        MOVE_COUNT.put('G', 6); MOVE_COUNT.put('U', 6);
        MOVE_COUNT.put('H', 7); MOVE_COUNT.put('T', 7);
        MOVE_COUNT.put('I', 8); MOVE_COUNT.put('S', 8);
        MOVE_COUNT.put('J', 9); MOVE_COUNT.put('R', 9);
        MOVE_COUNT.put('K', 10); MOVE_COUNT.put('Q', 10);
        MOVE_COUNT.put('L', 11); MOVE_COUNT.put('P', 11);
        MOVE_COUNT.put('M', 12); MOVE_COUNT.put('O', 12);
        MOVE_COUNT.put('N', 13);
    }

    public int solution(String name) {
        int count = 0;
        boolean passableA = false;
        int passableAInFrontCount = 0;
        int passableAInBackCount = 0;

        for (int i = 0; i < name.length(); i++) {
            char value = name.charAt(i);
            count += MOVE_COUNT.get(value) + 1;

            if ((i == 1 && value == 'A') || (passableA && value == 'A')) {
                passableA = true;
                passableAInFrontCount++;
            } else {
                passableA = false;
            }
        }

        passableA = false;

        for (int i = name.length() - 1; i >= 0; i--) {
            char value = name.charAt(i);

            if ((i == name.length() - 1 || passableA) && value == 'A') {
                passableA = true;
                passableAInBackCount++;
            } else break;
        }



        return count - 1 - (Math.max(passableAInFrontCount, passableAInBackCount));
    }
}

// 실패 케이스가 도대체 어떤건질 알수가 없네.
// 큰 틀은 2번째 이후 연속된 A 를 스킵하거나 맨 뒤에서부터 연속된 A 를 스킵 (둘중 더 많이 스킵되는 케이스만)