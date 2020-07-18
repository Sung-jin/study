// 2020 KAKAO BLIND RECRUITMENT
// https://programmers.co.kr/learn/challenges?selected_part_id=17214
// https://programmers.co.kr/learn/courses/30/lessons/60058

class KAKAO2020ParenthesesConversion {
    fun parenthesesConversion(parenthesesStr: String): String {
        println("$parenthesesStr : ${isCorrectParentheses(parenthesesStr)}")

        return ""
    }

    private fun isCorrectParentheses(parenthesesStr: String): Boolean {
        var isCorrect = true
        var parenthesesPair = 0

        for(parentheses in parenthesesStr) {
            if(parentheses == '(') parenthesesPair++ else parenthesesPair--

            if(parenthesesPair < 0) {
                isCorrect = false
                break
            }
        }

        return isCorrect
    }

    private fun getBalancedParenthesesStartIndex(parenthesesStr: String): Int {
        var parenthesesPair = if (parenthesesStr[0] == '(') 1 else -1

        for (index in (1..parenthesesStr.length)) {

        }
    }

    private fun checkPairParentheses(parentheses: Char): Int {
        return parentheses
    }
}
