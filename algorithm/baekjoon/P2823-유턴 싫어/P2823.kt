package algorithm.baekjoon.`P2823-유턴 싫어`

import java.io.*
import java.util.*

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    val r = st.nextToken().toInt()
    val c = st.nextToken().toInt()
    val a = Array(r) { br.readLine().toCharArray() }

    val dx = arrayOf(0, 1, 0, -1)
    val dy = arrayOf(1, 0, -1, 0)

    var result = 0
    for (i in 0 until r) {
        for (j in 0 until c) {
            var cnt = 0
            if (a[i][j] == '.') {
                for (k in 0 until 4) {
                    val x = i + dx[k]
                    val y = j + dy[k]
                    if (x in 0 until r && y in 0 until c) {
                        if (a[x][y] == 'X') {
                            cnt++
                        }
                    } else {
                        cnt++
                    }
                    if (cnt >= 3) {
                        result = 1
                        break
                    }
                }
            }
        }
    }
    println(result)
}
