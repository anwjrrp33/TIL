## [NBA 농구](https://www.acmicpc.net/problem/2852)
### 요구사항
* 시간 제한 1초, 메모리 제한 128MB
* 농구 경기는 48분 동안 진행되며, 각 팀이 몇 분동안 이기고 있었는지 출력하는 프로그을 작성한다.

### 문제풀이
* N는 1이상 100이하로 해당 문제는 시간제한을 고려하지 않아도 되고, 메모리또한 고려대상에서 제외했다.
* 문제에 주어진 요구사항을 살펴봤을 때 인자에 주어진 골이 들어간 시간과 팀을 통해서 이기고 있던 시간만 구하면 된다.
* 해당 문제에서 살펴봐야하는 건 시간인데 분으로 주어지며, 이런 경우에는 초 단위로 바꿔서 계산하고 출력할 때만 변환하는 것이 좋다.

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
    val teamGoal = IntArray(3)
    val teamTime = IntArray(3)
    var lastTime = 0

    repeat(input) {
        val information = bufferedReader.readLine().split(" ")
        val goalTeam = information[0].toInt()
        val timeParts = information[1].split(":")
        val goalTime = timeParts[0].toInt() * 60 + timeParts[1].toInt()

        when {
            teamGoal[1] > teamGoal[2] -> teamTime[1] += goalTime - lastTime
            teamGoal[2] > teamGoal[1] -> teamTime[2] += goalTime - lastTime
        }

        teamGoal[goalTeam] += 1
        lastTime = goalTime
    }

    val endTime = 48 * 60
    when {
        teamGoal[1] > teamGoal[2] -> teamTime[1] += endTime - lastTime
        teamGoal[2] > teamGoal[1] -> teamTime[2] += endTime - lastTime
    }

    bufferedWriter.write(String.format("%02d:%02d", teamTime[1] / 60, teamTime[1] % 60) + "\n")
    bufferedWriter.write(String.format("%02d:%02d", teamTime[2] / 60, teamTime[2] % 60))
    bufferedWriter.flush()
    bufferedWriter.close()
}
```