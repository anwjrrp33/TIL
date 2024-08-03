package algorithm.baekjoon.`P4949-균형잡힌 세상`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val bw = BufferedWriter(OutputStreamWriter(System.out))

    while (true) {
        val input = br.readLine() ?: break
        if (input == ".") break

        bw.write(isBalance(input))
    }

    bw.flush()
    bw.close()
}

fun isBalance(s: String): String {
    val stack = CharArray(s.length)
    var index = -1

    for (char in s) {
        when (char) {
            '(', '[' -> {
                if (index + 1 < stack.size) {
                    stack[++index] = char
                }
            }
            ')' -> {
                if (index >= 0 && stack[index] == '(') {
                    index--
                } else {
                    return "no\n"
                }
            }
            ']' -> {
                if (index >= 0 && stack[index] == '[') {
                    index--
                } else {
                    return "no\n"
                }
            }
        }
    }

    return if (index == -1) "yes\n" else "no\n"
}