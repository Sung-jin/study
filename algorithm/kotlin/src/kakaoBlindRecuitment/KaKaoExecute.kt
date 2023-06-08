package kakaoBlindRecuitment

import kakaoBlindRecuitment._2022.KAKAO2020COMPRESSION
import kakaoBlindRecuitment._2022.KAKAO2020ParenthesesConversion

class KaKaoExecute {
    init {
        val kakao2020Compression = KAKAO2020COMPRESSION()
        val kakao2020ParenthesesConversion = KAKAO2020ParenthesesConversion()

        // 1번 문제
        println("1번 문제 - 문자열 압축\n")
        println("aabbaccc : ${kakao2020Compression.compression("aabbaccc")}")
        println("ababcdcdababcdcd : ${kakao2020Compression.compression("ababcdcdababcdcd")}")
        println("abcabcdede : ${kakao2020Compression.compression("abcabcdede")}")
        println("abcabcabcabcdededededede : ${kakao2020Compression.compression("abcabcabcabcdededededede")}")
        println("xababcdcdababcdcd : ${kakao2020Compression.compression("xababcdcdababcdcd")}")

        println("-----------------------------------------------------------------")

        // 2번 문제
        println("2번 문제 - 괄호 변환\n")
        println("(()())() : ${kakao2020ParenthesesConversion.parenthesesConversion("(()())()")}")
        println(")( : ${kakao2020ParenthesesConversion.parenthesesConversion(")(")}")
        println("()))((() : ${kakao2020ParenthesesConversion.parenthesesConversion("()))((()")}")
    }
}
