## [미로 만들기](https://www.acmicpc.net/problem/1347)
### 요구사항
* 시간 제한 2초, 메모리 제한 128MB
* 미로 안의 한 칸에 남쪽을 보며 서있다.
* 미로는 직사각형의 격자모양이다.
* 각 칸은 이동할 수 있거나 벽을 포함하고 있다.
* 모든 행과 열에는 하나의 이동할 수 있는 칸이 있다.
* 첫 번째 줄에는 적은 내용의 길이가 주어지고 길이는 0보다 크고 50보다 작으며, 둘째 줄에는 적은 내용의 내용이 주어진다.

### 문제풀이
* 첫 번쨰로 입력을 생각해야 한다, 길이는 0보다 크고 50보다 작다
  * 이는 2차원 배열로 표현했을 때 현재 위치에서 += 50까지의 경로까지 이동 가능하다는 의미이다.
  * 즉, 2차원 배열로 표현했을 때 현재 위치에서 +- 50까지의로 했을 경우 101, 101 크기의 좌표가 나온다.
* 두 번쨰로 입력을 통해서 어떤 방식으로 이동을 해야하는지 판단해야 한다.
  * 예제 입력과 예제 출력
    ```
      -- 예제 입력
      5
      RRFRF
      ```
    ```
      -- 예제 출력
      ..
      .#
    ```
  * 예제 입력과 예제 출력을 보고 과정을 분석해야 한다, 처음 시작하는 미로에서 남쪽으로 시작하고 2번 오른쪽으로 방향을 돌리고 앞으로 한칸 이동 후 1번 오른쪽으로 방향을 돌리고 앞으로 한칸 이동하고 마무리했다.
  * 즉 경로 상으로 3칸 이동 가능했고, 직사각형의 특징으로 인해 이동하지 않은 곳은 #을 채우면 된다.
  * 모든 경로를 #으로 채운 뒤 이동한 곳만 .으로 채우면 출력을 표현할 수 있다.

### 코드
```
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
```