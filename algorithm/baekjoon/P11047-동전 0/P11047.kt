package algorithm.baekjoon.`P11047-동전 0`

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())

    val n = st.nextToken().toInt()
    var k = st.nextToken().toInt()

    val coins = IntArray(n) {
        br.readLine().toInt()
    }

    var result = 0

    for (i in n - 1 downTo 0) {
        result += k / coins[i]
        k %= coins[i]
    }

    println(result)
}