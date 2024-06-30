## [진짜 메시지](https://www.acmicpc.net/problem/9324)

### 요구사항
* 변형된 메시지는 각 문자가 세 번째 등장할 때 한번 더 문자가 삽입된다.
* 첫번 째 줄에 100 이하의 테스트 케이스의 개수가 주어진다.
* 대문자로만 이루어진 10만자 이하의 문자열이 한 줄에 주어진다.

### 문제풀이
* 시간 제한 1초이고, input 값은 몇개가 들어올 지 알수 없는 상황이다.
* 세 번쨰 등장마다 한번 더 반복이기 때문에 등장한 숫자를 저장해야 한다.
* t의 문자열 크기만큼 비교하기 때문에 시간 복잡도는 O(n)이 된다

### 코드
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine().toInt()

    for (i in 0 until input) {
        val map = mutableMapOf<Char, Int>()
        val message = bufferedReader.readLine()
        var isOk = true
        var index = 0

        while (index < message.length) {
            val char = message[index]

            if (map.containsKey(char)) {
                map.put(char, map.getValue(char) + 1)
            } else {
                map.put(char, 1)
            }

            val count = map.getValue(char)

            if (count % 3 == 0) {
                if (index >= message.length - 1 || message[index + 1] != char) {
                    isOk = false
                    break
                }
                index += 1
            }
            index += 1
        }

        if (isOk) {
           bufferedWriter.write("OK\n")
        } else {
            bufferedWriter.write("FAKE\n")
        }
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}
```

### 다른 코드
```
private val br = System.`in`.bufferedReader()
private val bw = System.out.bufferedWriter()

fun main() {
    val t = br.readLine().toInt()

    repeat(t) {
        val str = br.readLine()
        if (check(str)) bw.write("OK") else bw.write("FAKE")
        bw.newLine()
    }

    bw.close()
    br.close()
}

private fun check(str: String): Boolean {
    val count = IntArray(26)

    var i = 0
    while (i in str.indices) {
        val cur = str[i] - 'A'
        count[cur]++

        if (count[cur] == 3) {
            if (i == str.lastIndex) return false
            if (str[i] == str[i+1]) {
                count[cur] = 0
                i++
            } else {
                return false
            }
        }

        i++
    }

    return true
}
```