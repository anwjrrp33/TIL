package algorithm.baekjoon.`P3048-개미`

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))

    val st = StringTokenizer(reader.readLine())
    val N1 = st.nextToken().toInt()
    val N2 = st.nextToken().toInt()

    val arr = Array(N1 + N2) { IntArray(2) }

    val ant1 = reader.readLine()
    for (i in 0 until N1) {
        arr[N1 - i - 1][0] = ant1[i].code
        arr[N1 - i - 1][1] = -1
    }

    val ant2 = reader.readLine()
    for (i in N1 until N1 + N2) {
        arr[i][0] = ant2[i - N1].code
        arr[i][1] = 1
    }

    val T = reader.readLine().toInt()

    repeat(T) {
        var j = 0
        while (j < N1 + N2 - 1) {
            if (arr[j][1] != arr[j + 1][1] && arr[j][1] == -1) {
                val temp = arr[j][0]
                arr[j][0] = arr[j + 1][0]
                arr[j + 1][0] = temp

                arr[j][1] = -arr[j][1]
                arr[j + 1][1] = -arr[j + 1][1]

                j++
            }
            j++
        }
    }

    writer.write(arr.joinToString("") { it[0].toChar().toString() })
    writer.flush()
    writer.close()
}
