package algorithm.baekjoon.`P11866-요세푸스 문제 0`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine()
    val st = StringTokenizer(input)

    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()
    val list = LinkedList((1..n).toList())
    var index = 0
    val sb = StringBuilder("<")

    while (list.isNotEmpty()) {
        index = (index + k - 1) % list.size
        sb.append("${list[index]}, ")
        list.removeAt(index)
    }

    sb.delete(sb.length - 2, sb.length)
    sb.append(">")

    bufferedWriter.write(sb.toString())
    bufferedWriter.flush()
    bufferedWriter.close()
}