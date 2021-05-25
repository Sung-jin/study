fun main(args: Array<String>) {
    val input1 = readLine() ?: ""
    val input2 = readLine() ?: ""
    var (n, k) = input1.split(" ").map { it.toInt() }
    val i = input2.trim().split(" ").map{ it.toLong() }.toMutableList()

    val outIndex: MutableList<Int> = mutableListOf()
    val targetRemainCollection = MutableList(i.size) { 0L }

    while (!targetRemainCollection.containsAll(i)) {
        if (i[k - 1] != 0L) {
            i[k - 1] -= 1L

            if (i[k - 1] == 0L) {
                outIndex.add(k)
            }

        }

        if(k == n) k = 1 else k += 1 % (n + 1)
    }

    println(outIndex.joinToString(" "))
}
