## [수 이어가기](https://www.acmicpc.net/problem/2635)

### 요구사항
* 규칙에 따라 수를 만든다.
  1. 첫 번째 수로 1부터 30,000까지의 정수가 입력된다.
  2. 두 번째 수는 양의 정수 중에서 하나를 선택한다.
  3. 세 번째부터 이후에 나오는 모든 수는 앞의 앞의 수에서 앞의 수를 빼서 만든다

### 문제풀이
* 시간 제한 1초로 모든 숫자를 계산할 수 밖에 없지만, 숫자 연산이기 때문에 러프한 편이다.
* 두 번째 수는 임의의 숫자인데, 3번 째 조건을 대입할 때 0부터 3번째 숫자 값까지가 두 번째 수가 될 수 있다.

### 코드
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main()  {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine().toInt()
    var result = mutableListOf<Int>()

    for (i in 0..input) {
        var beforeNumber = input
        var afterNumber = i
        val sequence = mutableListOf(beforeNumber, afterNumber)

        while (true) {
            val number = beforeNumber - afterNumber

            if (number < 0) {
                break
            }

            sequence.add(number)
            beforeNumber = afterNumber
            afterNumber = number
        }

        if (result.size < sequence.size) {
            result = sequence
        }
    }

    bufferedWriter.write("${result.size}\n${result.joinToString(separator = " ")}")
    bufferedWriter.flush()
    bufferedReader.close()
}
```