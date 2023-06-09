package programmersBasicTraning.l0

/**
 * 정수가 담긴 리스트 num_list가 주어질 때, num_list의 원소 중 짝수와 홀수의 개수를 담은 배열을 return 하도록 solution 함수를 완성해보세요.
 *
 * 제한사항
 * 1 ≤ num_list의 길이 ≤ 100
 * 0 ≤ num_list의 원소 ≤ 1,000
 */
class L0P120824 {
    fun solution(num_list: IntArray): IntArray {
        return num_list.fold(intArrayOf(0, 0)) { res, value ->
            if (value % 2 == 0) res[0] += 1
            else res[1] += 1

            res
        }
    }
}
