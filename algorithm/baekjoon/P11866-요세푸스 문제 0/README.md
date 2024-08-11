## [요세푸스 문제 0](https://www.acmicpc.net/problem/11866)
### 요구사항
![img.png](img.png)

* 시간 제한 2초, 메모리 제한 512MB, 주어진 입력의 경우 (1 ≤ K ≤ N ≤ 1,000)

### 문제풀이
* 주어진 N명의 사람이 원을 그리며 앉아 있다고 가정한다.
* 주어진 숫자에 맞춰서 K번째 사람 한명을 제거한 뒤 남은 사람들은 다시 원을 이뤄서 이 과정을 이어나간다.
* 해당 문제의 순서를 보았을 떄 선입선출인 큐가 적합해보인다.
* 큐를 통해서 K만큼 숫자를 제거한 뒤 다시 숫자를 삽입하고 K번 째는 영원히 제거한다.
* 하지만, 큐를 통해서 문제를 풀 경우 속도가 너무 느리기 때문에 배열이나 리스트를 통한 방법을 풀어서 속도를 개선한다.

### 코드
* 1차 시도, 큐를 사용한 문제 풀이
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine()
    val st = StringTokenizer(input)

    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()
    val queue = LinkedList((1..n).toList())

    bufferedWriter.write("<")

    while (queue.size > 1) {
        for (i in 0 until k - 1) {
            queue.offer(queue.poll())
        }
        bufferedWriter.write("${queue.poll()}, ")
    }

    bufferedWriter.write("${queue.poll()}>")
    bufferedWriter.flush()
    bufferedWriter.close()
}
```
* 2차 시도, 속도를 개선한 문제풀이
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    val input = bufferedReader.readLine()
    val st = StringTokenizer(input)

    val n = st.nextToken().toInt()
    val k = st.nextToken().toInt()
    val list = LinkedList((1..n).toList())
    var index = 0
    val sb = StringBuilder("<")

    while (list.isNotEmpty()) {
        index = (index + k - 1) % list.size
        sb.append("${list[index]}, ")
        list.removeAt(index)
    }

    sb.delete(sb.length - 2, sb.length)
    sb.append(">")

    bufferedWriter.write(sb.toString())
    bufferedWriter.flush()
    bufferedWriter.close()
}
```