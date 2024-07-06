## [30](https://www.acmicpc.net/problem/10610)

### 요구사항
* 무작위 숫자가 포함된 숫자들을 섞어 30의 배수가 되는 가장 큰수를 만들어야 한다.

### 문제풀이
* 시간 제한 1초이고, 메모리 제한 256MB, N은 10⁵개
* N은 0으로 시작하지 않는다.
* 30의 배수인 숫자가 존재하면 그 수를 출력하고, 존재하지 않으면 -1을 출력한다.

### 코드
* 1차 풀이 (실패)
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.PriorityQueue

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.`out`))

    val input = bufferedReader.readLine()
    var result = true
    // 재귀함수로 순열 생성
    generate(String(), input)

    while (numbers.size > 0) {
        val number = numbers.poll()

        if (number % 30 == 0) {
            result = false
            bufferedWriter.write("$number")
            break
        }
    }

    if (result) {
        bufferedWriter.write("-1")
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}

private val numbers = PriorityQueue<Int>(reverseOrder())

private fun generate(current: String, remain: String) {
    if (remain.isEmpty()) {
        numbers.add(current.toInt())
        return
    }

    for (i in remain.indices) {
        generate(current + remain[i], remain.removeRange(i, i + 1))
    }
}
```
* 2차 풀이 (성공)
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine()
    val numbers = input.map { Character.getNumericValue(it) }
        .sortedDescending()
        .toIntArray()

    if (input.indexOf('0') > -1 && numbers.sum() % 3 == 0) {
        bufferedWriter.write(numbers.joinToString(""))
    } else {
        bufferedWriter.write("-1")
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}
```
* 그 외 코드
```
-- 숫자 1~9까지를 배열에 저장한 뒤 순차적을 확인하는 효율적인 코드
fun main() {
    val br = System.`in`.bufferedReader()
    val n = br.readLine()

    br.close()

    val counts = IntArray(10)
    var tenCondition = false
    var sum = 0

    for (c in n) {
        val num = c - '0'

        if (!tenCondition && num == 0) {
            tenCondition = true
        }

        sum += num
        counts[num]++
    }

    if (tenCondition && sum % 3 == 0) {
        val sb = StringBuilder()

        for (num in 9 downTo 0) {
            repeat(counts[num]) {
                sb.append(num)
            }
        }

        println(sb)
    } else {
        println(-1)
    }
}
```
```
-- 숏코딩
fun main()=System.out.bufferedWriter().run{
    val a=IntArray(10); var s=0
    System.`in`.bufferedReader().readLine().forEach{val v=it-'0'; a[v]++; s+=v}
    if(s%3!=0||a[0]==0)write("-1")
    else for(i in 9 downTo 0){val c='0'+i; write(CharArray(a[i]){c})}
    flush()
}
```