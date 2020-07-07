// https://programmers.co.kr/learn/challenges?selected_part_id=17214
// 2020 KAKAO BLIND RECRUITMENT

class KAKAO2020 {
    // 문자열 압축
    fun compression(original: String): String {
        return if (original.length == 1) original
        else {
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

            return compressionStrings.minBy{ it.length }!!
        }
    }
}
