package programmersBasicTraning

import programmersBasicTraning.l0.L0P120818
import programmersBasicTraning.l0.L0P120824
import programmersBasicTraning.l0.L0P181866
import programmersBasicTraning.l1.L1P12910
import programmersBasicTraning.l1.L1P12915
import programmersBasicTraning.l1.L1P12931
import programmersBasicTraning.l1.L1P12940
import programmersBasicTraning.l1.L1P12947
import programmersBasicTraning.l1.L1P92334

class ProgrammersExecute {
    init {
        val l0P120818 = L0P120818()
        val l0P120824 = L0P120824()
        val l0P181866 = L0P181866()

        val l1P12910 = L1P12910()
        val l1P12915 = L1P12915()
        val l1P12931 = L1P12931()
        val l1P12940 = L1P12940()
        val l1P12947 = L1P12947()
        val l1P92334 = L1P92334()

        println("120818 > 150,000: ${l0P120818.solution(150_000)}")
        println("120818 > 580,000: ${l0P120818.solution(580_000)}")
        println("------")
        println("120824 > [1, 2, 3, 4, 5]: ${l0P120824.solution(intArrayOf(1, 2, 3, 4, 5)).joinToString(", ")}")
        println("120824 > [1, 3, 5, 7]: ${l0P120824.solution(intArrayOf(1, 3, 5, 7)).joinToString(", ")}")
        println("------")
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
        println("------")
        println("12940 > 3, 12: ${l1P12940.solution(3, 12).joinToString(", ")}")
        println("12940 > 2, 5: ${l1P12940.solution(2, 5).joinToString(", ")}")
        println("12940 > 6, 4: ${l1P12940.solution(6, 4).joinToString(", ")}")
        println("12940 > 144, 18: ${l1P12940.solution(144, 18).joinToString(", ")}")
        println("------")
        println("12947 > 10: ${l1P12947.solution(10)}")
        println("12947 > 12: ${l1P12947.solution(12)}")
        println("12947 > 11: ${l1P12947.solution(11)}")
        println("12947 > 13: ${l1P12947.solution(13)}")
        println("------")
        println("92334 > [muzi, frodo, apeach, neo], [muzi frodo,apeach frodo,frodo neo,muzi neo,apeach muzi], 2: ${l1P92334.solution(arrayOf("muzi", "frodo", "apeach", "neo"), arrayOf("muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"), 2).joinToString(", ")}")
        println("92334 > [con, ryan], [ryan con, ryan con, ryan con, ryan con], 3: ${l1P92334.solution(arrayOf("con", "ryan"), arrayOf("ryan con", "ryan con", "ryan con", "ryan con"), 3).joinToString(", ")}")
    }
}
