package algorithm.baekjoon.`P2246-콘도선정`

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

data class Condo(val cost: Int, val instance: Int)

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val input = bufferedReader.readLine().toInt()
    // 콘도 목록
    val condos = Array(input) {
        val tokenizer = StringTokenizer(bufferedReader.readLine())
        Condo(tokenizer.nextToken().toInt(), tokenizer.nextToken().toInt())
    }

    var count = 0

    for (i in 0 until input) {
        var condition = true

        for (j in 0 until input) {
            // 1. 기준보다 바닷가에 더 가까운 콘도들은 모두 기준보다 숙박비가 더 비싸다.
            // 2. 기준보다 숙박비가 더 싼 콘도들은 모두 기준보다 바닷가에서 더 멀다.
            // 3. 자기 자신은 해당하지 않는다.
            if (i != j && condos[j].cost <= condos[i].cost && condos[j].instance <= condos[i].instance) {
                condition = false
                break
            }
        }

        if (condition) {
            count += 1
        }
    }

    println(count)
}