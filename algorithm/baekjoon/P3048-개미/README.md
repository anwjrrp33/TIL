## [개미](https://www.acmicpc.net/problem/3048)
### 요구사항
* 시간 제한 1초, 메모리 제한 128MB
* 개미가 일렬로 이동할 때, 가장 앞의 개미를 제외한 나머지 개미는 모두 앞에 개미가 한 마리씩 있다.
* 즉, 두 그룹이 만났을 때, 1초에 한번씩 개미는 서로를 뛰어 넘는다. (한 개미가 다른 개미를 뛰어 넘고, 다른 개미는 그냥 전진한다고 생각해도 된다)
* T초가 지난 후에 개미의 순서를 구하는 프로그램을 작성한다.

### 문제풀이
* 모든 개미를 하나의 배열에 순차적으로 넣는다.
* 1초 당 개미들이 이동을 시키는 것을 구현하기 위해서 스왑을 통해서 하나씩 전진시킨다.
* 왜 스왑하는 방식을 사용했는가? 스왑하는 방식을 사용하지 않는 경우 시간이 0초인 경우, 개미의 숫자가 다른 경우, 개미의 숫자가 T보다 짧은 경우 등 여러 케이스를 if 문으로 제어해야했다.

### 코드
```
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main() {
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(System.out))

    // 그룹의 길이 입력
    val st = StringTokenizer(reader.readLine())
    val N1 = st.nextToken().toInt()
    val N2 = st.nextToken().toInt()

    val arr = Array(N1 + N2) { IntArray(2) }

    val ant1 = reader.readLine()
    for (i in 0 until N1) {
        arr[N1 - i - 1][0] = ant1[i].code
        arr[N1 - i - 1][1] = -1
    }

    val ant2 = reader.readLine()
    for (i in N1 until N1 + N2) {
        arr[i][0] = ant2[i - N1].code
        arr[i][1] = 1
    }

    val T = reader.readLine().toInt()

    repeat(T) {
        var j = 0
        while (j < N1 + N2 - 1) {
            if (arr[j][1] != arr[j + 1][1] && arr[j][1] == -1) {
                val temp = arr[j][0]
                arr[j][0] = arr[j + 1][0]
                arr[j + 1][0] = temp

                arr[j][1] = -arr[j][1]
                arr[j + 1][1] = -arr[j + 1][1]

                j++
            }
            j++
        }
    }

    writer.write(arr.joinToString("") { it[0].toChar().toString() })
    writer.flush()
    writer.close()
}
```