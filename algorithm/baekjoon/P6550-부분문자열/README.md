## [부분 문자열](https://www.acmicpc.net/problem/6550)

### 요구사항
* 2개의 문자열 s, t가 주어질 때 s가 t의 부분 문자열인지 판단해야 한다.
* t에서 문자열을 제거하고 순서를 바꾸지 않고 합쳤을 때 s가 되는 경우이다.
* s와 t의 길이는 10만을 넘지 않는다.
* 그리디 알고리즘, 부분 문자열

### 문제풀이
* 시간 제한 1초이고, input 값은 몇개가 들어올 지 알수 없는 상황이다.
* s가 t의 부분 문자열인지 비교하기 위해서는 s라는 문자열을 낱개 단위로 char 타입으로 비교해야 한다.
* t의 문자열 크기만큼 비교하기 때문에 시간 복잡도는 O(n)이 된다

### 코드
```
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.StringTokenizer

fun main() {
    val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    while (true) {
        val input = bufferedReader.readLine()

        if (input.isNullOrEmpty()) {
            break
        }

        val stringTokenizer = StringTokenizer(input)
        val s1 = stringTokenizer.nextToken()
        val s2 = stringTokenizer.nextToken()

        var index = 0

        for (char in s2) {
            if (s1[index] == char) {
                index += 1
            }
            if (index == s1.length) {
                break
            }
        }

        if (index == s1.length) {
            bufferedWriter.write("Yes\n")
        } else {
            bufferedWriter.write("No\n")
        }
    }

    bufferedWriter.flush()
    bufferedWriter.close()
}
```
```
-- 극한의 라인 수 줄이기
fun main()=System.`in`.bufferedReader().run{System.out.bufferedWriter().run{while(true){
    java.util.StringTokenizer(readLine()?:break).run{
        val a=nextToken(); val b=nextToken()
        var i=0; val l=a.length
        b.forEach{if(a[i]==it&&++i==l)return@run write("Yes\n")}
        write("No\n")
    }
};flush()}}

-- indexOf 활용하기
fun main() = with(System.`in`.bufferedReader()) {
    while (true) {
        val input = readLine() ?: break
        val (s, t) = input.split(" ")

        var start = 0
        var result = "Yes"
        for (c in s) {
            start = t.indexOf(c, start) + 1
            if (start == 0) {
                result = "No"
            }
        }
        println(result)
    }

    close()
}
```