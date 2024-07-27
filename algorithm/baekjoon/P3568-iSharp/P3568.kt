package algorithm.baekjoon.`P3568-iSharp`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))

    val input = reader.readLine()
    val iSharp = input.split(" ")
    val base = iSharp[0]

    val sb = StringBuilder()

    for (i in 1 until iSharp.size) {
        val variable = iSharp[i]
        val type = StringBuilder()
        val name = StringBuilder()

        for (char in variable) {
            when (char) {
                '*', '&' -> type.append(char)
                '[' -> type.append(']')
                ']' -> type.append('[')
                in 'A'..'Z', in 'a'..'z' -> name.append(char)
            }
        }

        sb.append(base).append(type.reverse()).append(" ").append(name).append(";\n")
    }

    writer.write(sb.toString())
    writer.flush()
    writer.close()
}