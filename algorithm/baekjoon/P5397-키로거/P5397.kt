package algorithm.baekjoon.`P5397-키로거`

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val t = br.readLine().toInt()
    val sb = StringBuilder()

    for (tc in 0 until t) {
        var cursor = -1
        var tempCursor = -1

        val str = br.readLine()
        val len = str.length

        val right = CharArray(1000000)
        val left = CharArray(1000000)

        for (i in 0 until len) {
            val ch = str[i]

            when (ch) {
                '<' -> if (cursor != -1) left[++tempCursor] = right[cursor--]
                '>' -> if (tempCursor != -1) right[++cursor] = left[tempCursor--]
                '-' -> if (cursor != -1) cursor--
                else -> right[++cursor] = ch
            }
        }

        for (i in 0..cursor) {
            sb.append(right[i])
        }
        while (tempCursor != -1) {
            sb.append(left[tempCursor--])
        }
        sb.append('\n')
    }
    println(sb)
}