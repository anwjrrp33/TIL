package algorithm.baekjoon.`P11399-ATM`

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val n = br.readLine().toInt()
    val arr = IntArray(n)

    val st = StringTokenizer(br.readLine())

    for (i in 0 until n) {
        arr[i] = st.nextToken().toInt()
    }

    arr.sort()

    var previous = 0
    var result = 0

    for (i in arr.indices) {
        result += previous + arr[i]
        previous += arr[i]
    }

    println(result)
}