package algorithm.baekjoon.`P9324-진짜메시지`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine().toInt()

    for (i in 0 until input) {
        val map = mutableMapOf<Char, Int>()
        val message = bufferedReader.readLine()
        var isOk = true
        var index = 0

        while (index < message.length) {
            val char = message[index]

            if (map.containsKey(char)) {
                map.put(char, map.getValue(char) + 1)
            } else {
                map.put(char, 1)
            }

            val count = map.getValue(char)

            if (count % 3 == 0) {
                if (index >= message.length - 1 || message[index + 1] != char) {
                    isOk = false
                    break
                }
                index += 1
            }
            index += 1
        }

        if (isOk) {
            bufferedWriter.write("OK\n")
        } else {
            bufferedWriter.write("FAKE\n")
        }
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}