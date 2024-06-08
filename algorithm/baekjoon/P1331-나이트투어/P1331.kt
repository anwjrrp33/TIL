package algorithm.baekjoon.`P1331-나이트투어`


val position = listOf(Pair(-2, -1), Pair(-2, 1), Pair(-1, 2), Pair(1, 2), Pair(2, 1), Pair(2, -1), Pair(1, -2), Pair(-1, -2))

fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    // 입력
    val locations = List(36) {
        val input = readLine()
        Pair(input[0].code - 64, input[1].code - 48)
    }
    // 동일한 경로 방문 여부
    if (locations.size != locations.distinct().size) {
        println("Invalid")
        return
    }
    // 이동 가능한 경로 여부
    for (i in 0..34) {
        if (!isMove(locations[i], locations[i + 1])) {
            println("Invalid")
            return
        }
    }
    // 처음 위치 가능한 여부
    when (isMove(locations[35], locations[0])) {
        true -> println("Valid")
        false -> println("Invalid")
    }
}

fun isMove(previous: Pair<Int, Int>, current: Pair<Int, Int>): Boolean {
    return position.contains(Pair(previous.first - current.first, previous.second - current.second))
}