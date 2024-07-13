## [로프](https://www.acmicpc.net/problem/2217)
### 요구사항
* 시간 제한 2초, 메모리 제한 192MB
* 로프는 1부터 100,000개의 로프가 존재한다.
* 각 로프는 들 수 있는 중량이 서로 다르다.
* 여러 개의 로프를 병렬로 연결하면 중량이 균등하게 나뉘게 된다.
* 꼭 모든 로프를 사용할 필요 없이, 임의의 로프를 골라서 사용할 수 있다.

### 문제풀이
* 로프의 개수는 최소 1개, 최대 100,000개로 시간 제한에 대한 문제는 아니다.
* 여러 개의 로프를 병렬로 연결하면 중량이 균등하게 나뉘지만, 임의의 로프를 골라서 사용할 수 있다에 집중해야 한다.
  * 예제 입력과 예제 출력
    ```
    -- 예제 입력
    2
    10
    15
    -- 예제 출력
    20
    ```
  * 예제에는 로프가 2개 주어졌고, 각각 10, 15의 중량을 견디기 때문에 둘다 사용했을 때 20의 결과가 나온다.
  * 하지만 다른 반례를 살펴봤을 때 전혀 다른 결과가 나올 수도 있다.
    ```
    -- 예제 입력
    3
    10
    15
    100
    -- 예제 출력
    100
    ```
  * 해당 입력으로는 100을 견디는 로프 하나만 사용했을 때 로프 전부를 사용했을 때보다 많은 중량을 견딜 수 있기 때문에 결과는 100이 된다.
  * 즉, 최대의 중량이 되는 임의의 로프 개수를 생각해야 한다.
* 임의의 수는 어떻게 구해야할까?
  * 각 로프의 무게는 달라지는데 무게가 클수록 그리고 근사치에 가까울 수록 유리하다.
  * 즉 로프의 무게를 내림차순으로 정렬해 현재 로프의 견디는 중량 값과 사용된 로프의 개수를 곱해서 이전 무게와 비교하면 된다.

### 코드
* 1차 풀이
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.PriorityQueue

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val n = bufferedReader.readLine().toInt()
    val loops = PriorityQueue<Int>(compareByDescending { it })
    for (i in 0 until n) {
        loops.add(bufferedReader.readLine().toInt())
    }

    var maxWeight = 0
    for (i in 0 until n) {
        val weight = loops.poll()
        maxWeight = maxOf(maxWeight, weight * (i + 1))
    }

    bufferedWriter.write(maxWeight.toString())
    bufferedWriter.flush()
    bufferedWriter.close()
}
```
* 2차 풀이
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val n = bufferedReader.readLine().toInt()
    val loops = IntArray(n).apply {
        repeat(n) {
            this[it] = bufferedReader.readLine().toInt()
        }
    }
    loops.sortDescending()

    var maxWeight = 0
    for (i in loops.indices) {
        val weight = loops[i]
        maxWeight = maxOf(maxWeight, weight * (i + 1))
    }

    bufferedWriter.write("${maxWeight}")
    bufferedWriter.flush()
    bufferedWriter.close()
}
```