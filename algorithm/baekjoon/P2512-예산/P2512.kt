package algorithm.baekjoon.`P2512-예산`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))

    val n = bufferedReader.readLine().toInt()

    var left = 0
    var right = 0

    val st = StringTokenizer(bufferedReader.readLine())

    val arr = IntArray(n).apply {
        repeat(n) {
            val value = st.nextToken().toInt()
            this[it] = value
            right = Math.max(right, value)
        }
    }

    val m = bufferedReader.readLine().toInt()

    while (left <= right) {
        val mid = (left + right) / 2
        var budget = 0L

        for (i in 0 until n) {
            budget += if (arr[i] > mid) mid
            else arr[i]
        }
        if (budget <= m) {
            left = mid + 1
        } else {
            right = mid - 1
        }
    }

    BufferedWriter(OutputStreamWriter(System.out)).use { bw ->
        bw.write(right.toString())
    }
}