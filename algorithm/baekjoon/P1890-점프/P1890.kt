package algorithm.baekjoon.`P1890-점프`

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val n = br.readLine().toInt()
    val board = Array(n) { IntArray(n) }
    val dp = Array(n) { LongArray(n) }

    for (i in 0 until n) {
        val st = StringTokenizer(br.readLine())
        for (j in 0 until n) {
            board[i][j] = st.nextToken().toInt()
        }
    }

    dp[0][0] = 1

    for (i in 0 until n) {
        for (j in 0 until n) {
            val jump = board[i][j]
            if (jump == 0) { break }
            if (j + jump < n) dp[i][j + jump] += dp[i][j]
            if (i + jump < n) dp[i + jump][j] += dp[i][j]
        }
    }

    println(dp[n - 1][n - 1])
}