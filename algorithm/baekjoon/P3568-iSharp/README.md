## [iSharp](https://www.acmicpc.net/problem/3568)
### 요구사항
* 시간 제한 1초, 메모리 제한 128MB
* C, C++, Java와는 다른 아주 세련된 언어를 만들었다. 선영이는 이 아름답고 예술적인 언어의 이름을 i#으로 정했다.
* i#은 기본 변수형과 배열([]), 참조(&), 포인터(*)를 제공한다.
* 배열, 참조, 포인터는 순서에 상관없이 혼합해서 사용할 수 있다. 즉, int의 참조의 참조의 배열의 포인터도 올바른 타입이다. int&&[]*
* i#은 여러 개의 변수를 한 줄에 정의할 수 있다. 공통된 변수형을 제일 먼저 쓰고, 그 다음에 각 변수의 이름과 추가적인 변수형을 쓰면 된다.
* 변수의 오른편에 있는 변수형은 순서를 뒤집어서 왼편에 붙일 수 있다.

### 문제풀이
* i# 언어의 입력은 기본 타입 + 변수명 + 타입을 붙인다.
* 기본 타입을 파싱해서 정한뒤 나머지 변수명+타입들을 걸러내서 문자열로 만들어 낸다.
* 정규 표현식으로 풀수도 있을 것 같은데...

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