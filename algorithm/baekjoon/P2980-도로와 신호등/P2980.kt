package algorithm.baekjoon.`P2980-도로와 신호등`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    var st = StringTokenizer(bufferedReader.readLine())
    val N = st.nextToken().toInt()
    val L = st.nextToken().toInt()

    var time = 0
    var location = 0

    repeat(N) {
        st = StringTokenizer(bufferedReader.readLine())
        val D = st.nextToken().toInt()
        val R = st.nextToken().toInt()
        val G = st.nextToken().toInt()

        time += D - location
        location = D

        val wait = time % (R + G)
        if (wait < R) {
            time += R - wait
        }
    }

    time += L - location

    bufferedWriter.write(time.toString())
    bufferedWriter.flush()
    bufferedReader.close()
}