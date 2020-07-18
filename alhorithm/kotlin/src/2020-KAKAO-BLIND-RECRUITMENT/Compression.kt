// 2020 KAKAO BLIND RECRUITMENT
// https://programmers.co.kr/learn/challenges?selected_part_id=17214
// https://programmers.co.kr/learn/courses/30/lessons/60057

class KAKAO2020COMPRESSION {
    // 문자열 압축
    fun compression(original: String): String {
        val compressionStrings = mutableListOf<String>()
        for (i in 1..original.length / 2) {
            val chunkedString = original.chunked(i)
            var sameCount = 0
            var compare = chunkedString[0]
            var compressionString = ""

            chunkedString.forEachIndexed{ index, chunk ->
                if (chunk == compare && index < chunkedString.size + 1) sameCount++
                else {
                    compressionString += "${if (sameCount > 1) sameCount else ""}$compare"
                    compare = chunk
                    sameCount = 1
                }
            }

            if (compare == compressionString) compressionString = "2$compare"
            else compressionString += "${if (sameCount > 1) sameCount else ""}$compare"

            compressionStrings.add(compressionString)
        }

        return compressionStrings.minBy{ it.length } ?: original
    }
}
