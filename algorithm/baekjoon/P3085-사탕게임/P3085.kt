package algorithm.baekjoon.`P3085-사탕게임`

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().toInt()
    val arr = Array(n) { br.readLine().toCharArray() }
    var max = 0

    for (i in 0 until n) {
        for (j in 0 until n) {
            if (j < n - 1) {
                swap(arr, i, j, i, j + 1)
                max = maxOf(max, getMax(arr, i, j))
                max = maxOf(max, getMax(arr, i, j + 1))
                swap(arr, i, j, i, j + 1)
            }
            if (i < n - 1) {
                swap(arr, i, j, i + 1, j)
                max = maxOf(max, getMax(arr, i, j))
                max = maxOf(max, getMax(arr, i + 1, j))
                swap(arr, i, j, i + 1, j)
            }
        }
    }

    println(max)
}

fun getMax(arr: Array<CharArray>, y: Int, x: Int): Int {
    var col = 0
    var row = 0
    val tmp = arr[y][x]

    for (j in x + 1 until arr.size) {
        if (arr[y][j] != tmp) break
        col++
    }
    for (j in x - 1 downTo 0) {
        if (arr[y][j] != tmp) break
        col++
    }

    for (i in y + 1 until arr.size) {
        if (arr[i][x] != tmp) break
        row++
    }
    for (i in y - 1 downTo 0) {
        if (arr[i][x] != tmp) break
        row++
    }

    return maxOf(col + 1, row + 1)
}

fun swap(arr: Array<CharArray>, i: Int, j: Int, i2: Int, j2: Int) {
    val tmp = arr[i][j]
    arr[i][j] = arr[i2][j2]
    arr[i2][j2] = tmp
}