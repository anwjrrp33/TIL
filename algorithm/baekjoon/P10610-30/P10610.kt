package algorithm.baekjoon.`P10610-30`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine()
    val numbers = input.map { Character.getNumericValue(it) }
        .sortedDescending()
        .toIntArray()

    if (input.indexOf('0') > -1 && numbers.sum() % 3 == 0) {
        bufferedWriter.write(numbers.joinToString(""))
    } else {
        bufferedWriter.write("-1")
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}