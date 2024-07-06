package algorithm.baekjoon.`P1213-펠린드롬 만들기`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine()
    val array = IntArray(26)

    input.forEach { array[it - 'A']++ }

    val oddNumbers = array.mapIndexed { index, count ->
        if (count % 2 != 0) {
            'A' + index
        } else {
            null
        }
    }.filterNotNull()

    if (oddNumbers.size > 1) {
        bufferedWriter.write("I'm Sorry Hansoo")
    } else {
        val halfNumber = StringBuilder()
        for (i in array.indices) {
            repeat(array[i] / 2) {
                halfNumber.append('A' + i)
            }
        }

        val result = StringBuilder(halfNumber)
        if (oddNumbers.size == 1) {
            result.append(oddNumbers[0])
        }
        result.append(halfNumber.reverse())

        bufferedWriter.write(result.toString())
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}