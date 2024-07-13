package algorithm.baekjoon.`P2217-로프`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val n = bufferedReader.readLine().toInt()
    val loops = IntArray(n).apply {
        repeat(n) {
            this[it] = bufferedReader.readLine().toInt()
        }
    }
    loops.sortDescending()

    var maxWeight = 0
    for (i in loops.indices) {
        val weight = loops[i]
        maxWeight = maxOf(maxWeight, weight * (i + 1))
    }

    bufferedWriter.write("${maxWeight}")
    bufferedWriter.flush()
    bufferedWriter.close()
}