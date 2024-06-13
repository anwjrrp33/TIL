## [콘도 선정](https://www.acmicpc.net/problem/2246)

### 요구사항
* 조건에 맞는 콘도 후보의 개수를 구한다.
  1. 기준보다 바닷가에 더 가까운 콘도들은 모두 기준보다 더 비싸다.
  2. 기준보다 더 싼 콘도들은 모두 기준보다 바닷가에서 멀다.
* 콘도의 최대 개수는 10,000개 이하이다.

### 문제풀이
* 시간 제한 2초, 최대 개수는 10,000개라서 시간복잡도 O(n²)으로 풀어도 상관없다.
* 해당 문제는 정말 단순히 기준에 맞는 콘도의 후보를 구하는 문제이며, 별다른 문제풀이가 존재하지 않는다.
* 요구사항의 조건에 부합하는 콘도일 경우에만 후보 목록에 들어가며, 반대로 얘기하면 조건에 부합하지 않는 경우 바로 제외하면 된다.

### 코드
```
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
```
