## [펜린드롬 만들기](https://www.acmicpc.net/problem/1213)

### 요구사항
* 주어진 영어 이름의 알파벳 순서를 적절히 바꿔서 펠린드롬을 만들어야 한다.
* 펠린드롬이란?
  * 회문 또는 팰린드롬은 거꾸로 읽어도 제대로 읽는 것과 같은 문장이나 낱말, 숫자, 문자열 등이다. 보통 낱말 사이에 있는 띄어쓰기나 문장 부호는 무시한다.

### 문제풀이
* 시간 제한 2초이고, 메모리 제한 128MB, 주어진 N값은 최대 50글자이다.
* 펠린드롬이 불가능한 경우 I'm Sorry Hansoo를 출력한다.
* 정답이 여러 개인 경우 사전순으로 앞서는 것을 출력한다.

### 코드
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine()
    val array = IntArray(26)

    input.forEach { array[it - 'A']++ }

    val oddNumbers = array.mapIndexed { index, count ->
        if (count % 2 != 0) {
            'A' + index
        } else {
            null
        }
    }.filterNotNull()

    if (oddNumbers.size > 1) {
        bufferedWriter.write("I'm Sorry Hansoo")
    } else {
        val halfNumber = StringBuilder()
        for (i in array.indices) {
            repeat(array[i] / 2) {
                halfNumber.append('A' + i)
            }
        }

        val result = StringBuilder(halfNumber)
        if (oddNumbers.size == 1) {
            result.append(oddNumbers[0])
        }
        result.append(halfNumber.reverse())

        bufferedWriter.write(result.toString())
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}
```