package algorithm.baekjoon.`P1931-회의`

import java.util.Arrays

fun read(): Int {
    var n = System.`in`.read() and 15
    var c: Int
    while (System.`in`.read().also { c = it } > 32) {
        n = (n shl 3) + (n shl 1) + (c and 15)
    }
    return n
}

fun main() {
    val n = read()
    val arr = Array(n) { IntArray(2) }

    for (i in 0 until n) {
        arr[i][0] = read()
        arr[i][1] = read()
    }

    // 회의 종료 시간이 가장 빠른 것을 앞으로, 종료 시간이 같다면 시작 시간이 빠른 것을 앞으로 정렬
    Arrays.sort(arr) { a, b -> if (a[1] != b[1]) a[1] - b[1] else a[0] - b[0] }

    var result = 1
    var endtime = arr[0][1]

    // 회의 종료 시간 이후 가장 빨리 시작하는 회의를 찾으며 진행
    for (i in 1 until n) {
        if (arr[i][0] >= endtime) {
            result++
            endtime = arr[i][1]
        }
    }

    println(result)
}