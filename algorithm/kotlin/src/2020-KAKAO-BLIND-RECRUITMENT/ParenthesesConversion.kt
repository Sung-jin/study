// 2020 KAKAO BLIND RECRUITMENT
// https://programmers.co.kr/learn/challenges?selected_part_id=17214
// https://programmers.co.kr/learn/courses/30/lessons/60058

class KAKAO2020ParenthesesConversion {
    fun parenthesesConversion(parenthesesStr: String): String {
        return if(isCorrectParentheses(parenthesesStr)) {
            parenthesesStr
        } else {
            val (u, v) = Pair(parenthesesStr.substring(0, getBalancedParenthesesStartIndex(parenthesesStr) + 1),
                parenthesesStr.substring(getBalancedParenthesesStartIndex(parenthesesStr) + 1, parenthesesStr.length)
            )

            return if (isCorrectParentheses(u)) {
                "$u${parenthesesConversion(v)}"
            } else {
                "(${parenthesesConversion(v)})${u.substring(1, u.length - 1).map { reverseParentheses(it) }.joinToString("")}"
            }
        }
    }

    private fun isCorrectParentheses(parenthesesStr: String): Boolean {
        var isCorrect = true
        var parenthesesPair = 0

        for(parentheses in parenthesesStr) {
            parenthesesPair += checkPairParentheses(parentheses)

            if(parenthesesPair < 0) {
                isCorrect = false
                break
            }
        }

        return isCorrect
    }

    private fun getBalancedParenthesesStartIndex(parenthesesStr: String): Int {
        var parenthesesPair = checkPairParentheses(parenthesesStr[0])

        for (index in (1 until parenthesesStr.length)) {
            parenthesesPair += checkPairParentheses(parenthesesStr[index])

            if (parenthesesPair == 0) return index
        }
        return parenthesesStr.length - 1
    }

    private fun checkPairParentheses(parentheses: Char): Int {
        return if (parentheses == '(') 1 else -1
    }

    private fun reverseParentheses(parentheses: Char): Char {
        return if(parentheses == '(') ')' else '('
    }
}
