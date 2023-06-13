package highScoreKit.stackAndQueue;

import java.util.Stack;

/*
괄호가 바르게 짝지어졌다는 것은 '(' 문자로 열렸으면 반드시 짝지어서 ')' 문자로 닫혀야 한다는 뜻입니다. 예를 들어

"()()" 또는 "(())()" 는 올바른 괄호입니다.
")()(" 또는 "(()(" 는 올바르지 않은 괄호입니다.
'(' 또는 ')' 로만 이루어진 문자열 s가 주어졌을 때, 문자열 s가 올바른 괄호이면 true를 return 하고, 올바르지 않은 괄호이면 false를 return 하는 solution 함수를 완성해 주세요.

제한사항
문자열 s의 길이 : 100,000 이하의 자연수
문자열 s는 '(' 또는 ')' 로만 이루어져 있습니다.
 */
public class CorrectParentheses {
    boolean solution(String s) {
        Stack<Character> parentheses = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '(') parentheses.add(c);
            else if (c == ')') {
                if (parentheses.empty()) return false;
                else parentheses.pop();
            }
            else throw new RuntimeException("잘못된 문자가 있습니다. [" + c + "]");
        }

        return parentheses.empty();
    }
}

/*
// 자료구조/알고리즘: 스택
// 시간 복잡도: O(n)
// 공간 복잡도: O(n)

class Solution {
    boolean solution(String s) {
        Stack<Character> openingBrackets = new Stack<>();
        for (char bracket : s.toCharArray()) {
            if (bracket == '(') {
                openingBrackets.push(bracket);
            } else {
                if (openingBrackets.isEmpty()) {
                    return false;
                }
                openingBrackets.pop();
            }
        }
        return openingBrackets.isEmpty();
    }
}
 */
