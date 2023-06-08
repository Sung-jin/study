package programmersBasicTraning

import programmersBasicTraning.l0.L0P181866
import programmersBasicTraning.l1.L1P12910
import programmersBasicTraning.l1.L1P12915
import programmersBasicTraning.l1.L1P12931

class ProgrammersExecute {
    init {
        val l0P181866 = L0P181866()

        val l1P12910 = L1P12910()
        val l1P12915 = L1P12915()
        val l1P12931 = L1P12931()

        println("181866 > axbxcxdx: ${l0P181866.solution("axbxcxdx").joinToString(", ")}")
        println("181866 > dxccxbbbxaaaa: ${l0P181866.solution("dxccxbbbxaaaa").joinToString(", ")}")
        println("------")
        println("12910 > [5, 9, 7, 10], 5: ${l1P12910.solution(intArrayOf(5, 9, 7, 10), 5).joinToString(", ")}")
        println("12910 > [2, 36, 1, 3]: ${l1P12910.solution(intArrayOf(2, 36, 1, 3), 1).joinToString(", ")}")
        println("12910 > [3, 2, 6]: ${l1P12910.solution(intArrayOf(3, 2, 6), 10).joinToString(", ")}")
        println("------")
        println("12915 > [sun, bed, car], 1: ${l1P12915.solution(arrayOf("sun", "bed", "car"), 1).joinToString(", ")}")
        println("12915 > [abce, abcd, cdx], 2: ${l1P12915.solution(arrayOf("abce", "abcd", "cdx"), 2).joinToString(", ")}")
        println("------")
        println("12931 > 123: ${l1P12931.solution(123)}")
        println("12931 > 987: ${l1P12931.solution(987)}")
    }
}
