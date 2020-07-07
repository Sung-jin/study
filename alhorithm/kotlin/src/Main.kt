fun main() {
    val kakao2020 = KAKAO2020()
    println(kakao2020.compression("aabbaccc").length)
    println(kakao2020.compression("ababcdcdababcdcd").length)
    println(kakao2020.compression("abcabcdede").length)
    println(kakao2020.compression("abcabcabcabcdededededede").length)
    println(kakao2020.compression("xababcdcdababcdcd").length)
}
