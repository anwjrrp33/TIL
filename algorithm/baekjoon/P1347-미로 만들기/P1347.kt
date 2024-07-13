package algorithm.baekjoon.`P1347-미로 만들기`

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val n = bufferedReader.readLine().toInt()
    val movements = bufferedReader.readLine()

    // 미로
    val maze = Array(101) { CharArray(101) { '#' } }
    // 방향(북, 동, 남, 서)
    val dx = arrayOf(-1, 0, 1, 0)
    val dy = arrayOf(0, 1, 0, -1)
    // 현재 방향(0 - 북, 1 - 동, 2 - 남, 3 - 서)
    var direction = 2
    // 현재 위치
    var x = 50
    var y = 50
    // 시작 위치 바로 표시
    maze[x][y] = '.'

    for (i in 0 until n) {
        val movement = movements[i]
        // 미로 이동
        when (movement) {
            'F' -> {
                x += dx[direction]
                y += dy[direction]
                maze[x][y] = '.'
            }
            'L' -> {
                direction = (direction + 3) % 4
            }
            'R' -> {
                direction = (direction + 1) % 4
            }
        }
    }
    // 이동 경로 범위 계산
    var minX = 101
    var minY = 101
    var maxX = 0
    var maxY = 0

    maze.forEachIndexed { i, row ->
        row.forEachIndexed { j, col ->
            if (col == '.') {
                minX = minOf(minX, i)
                maxX = maxOf(maxX, i)
                minY = minOf(minY, j)
                maxY = maxOf(maxY, j)
            }
        }
    }

    // 결과 출력
    for (i in minX..maxX) {
        for (j in minY..maxY) {
            bufferedWriter.write(maze[i][j].toString())
        }
        bufferedWriter.newLine()
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}