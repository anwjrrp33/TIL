package algorithm.baekjoon.`P1431-시리얼 번호`

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.Arrays

fun main() = with(System.`in`.bufferedReader()) {
    val n = readLine().toInt()
    val arr = Array(n) { readLine() }

    Arrays.sort(arr, compareBy<String> { it.length }
        .thenBy { it.sumOf { char -> if (char.isDigit()) char - '0' else 0 } }
        .thenBy { it })

    BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
        arr.forEach { writer.write("$it\n") }
    }
}