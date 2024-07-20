## [도로와 신호등](https://www.acmicpc.net/problem/2980)
### 요구사항
* 시간 제한 1초, 메모리 제한 128MB
* 트럭이 일직선 도로를 운전하고 있고, 도로에는 신호등이 설치되어 있다.
* 각 신호등은 빨간 불이 지속되는 시간과 초록 불이 지속되는 시간이 존재한다.
* 트럭이 도로에 진입했을 때 모든 신호등 색상은 빨간색이고, 트럭은 1초에 1미터를 움직인다.
* 신호등의 색상이 빨간불이면 멈추고, 초록불일 때 움직인다.
* 트럭이 도로의 끝까지 이동하는데 걸리는 시간을 구하는 프로그램을 작성한다.

### 문제풀이
* 트럭의 현재 위치와 시간을 별도의 변수로 기록해둔다.
* 문제는 신호등인데 신호등의 반복적인 부분을 찾아야한다.
* 신호등은 첫 진입시 항상 빨간불이고, 트럭이 진입된 시점부터 시간이 지나면서 초록불과 빨간불이 움직인다.
* 트럭이 신호등에 도착했을 때의 시간과 (해당 신호등의 초록불 시간 + 빨간불 시간)을 현재 시간으로 나눠서 나머지 값이 빨간불 시간보다 작다면 현재는 빨간불이라고 단정할 수 있다.
* 마지막에 전체 길이와 현재 위치의 차이만큼 시간을 더해주면 결과가 나온다.

### 코드
```
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
```