package algorithm.baekjoon.`P1654-랜선 자르기`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())

    val K = st.nextToken().toInt()
    val N = st.nextToken().toInt()

    val arr = IntArray(K)
    var max = Int.MIN_VALUE.toLong()
    for (i in 0 until K) {
        arr[i] = br.readLine().toInt()
        max = maxOf(max, arr[i].toLong())
    }

    var low = 0L
    var high = max + 1
    var ans = Int.MIN_VALUE.toLong()

    while (low + 1 < high) {
        val mid = (low + high) / 2
        var result = 0L

        for (i in 0 until K) {
            result += arr[i] / mid
        }

        if (result >= N) {
            ans = maxOf(ans, mid)
        }

        if (result < N) {
            high = mid
        } else {
            low = mid
        }
    }

    BufferedWriter(OutputStreamWriter(System.out)).use { bw ->
        bw.write("$ans\n")
    }
}