## [나이트 투어](https://www.acmicpc.net/problem/1331)

### 요구사항
* 체스판에서 나이트가 모든 칸을 정확히 `한 번씩 방문`
* `마지막으로 방문하는 칸에서 시작점으로 돌아올 수 있는 경로`
* `36개의 줄에 나이트가 방문한 순서대로 입력`이 주어진다.
* 올바른 것이면 Valid, 올바르지 않으면 Invalid를 출력

### 문제풀이
* 체스의 나이트는 직선으로 두 칸 이동 후 옆으로 한 칸으로 L자로 이동한다.
* 가로는 A부터 E, 세로는 1부터 6까지로 x, y 2차원 배열로 좌표를 표현할 수 있다.
* 중복된 좌표가 존재하는 지, 이동 가능한 좌표인지, 마지막 좌표에서 처음 좌표로 돌아갈 수 있는지 판단하면 된다.

### 코드
```
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
```