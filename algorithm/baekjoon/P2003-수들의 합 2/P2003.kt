package algorithm.baekjoon.`P2003-수들의 합 2`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    var st = StringTokenizer(br.readLine())

    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    st = StringTokenizer(br.readLine())

    val arr = IntArray(n).apply {
        repeat(n) {
            this[it] = st.nextToken().toInt()
        }
    }

    var left = 0
    var right = 0
    var sum = 0
    var result = 0

    while (right <= n) {
        if (sum >= m) {
            sum -= arr[left++]
        } else {
            if (right == n) {
                break
            }
            sum += arr[right++]
        }

        if (sum == m) {
            result++
        }
    }

    BufferedWriter(OutputStreamWriter(System.out)).use { bw ->
        bw.write(result.toString())
    }
}