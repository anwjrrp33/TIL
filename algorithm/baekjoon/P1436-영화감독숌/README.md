## [영화감독 숌](https://www.acmicpc.net/problem/1436)

### 요구사항
* 종말의 수란 666이 들어가는 숫자를 말한다.
* 영화 제목에는 666이란 숫자가 들어가야 하며 입력된 숫자에 맞는 666이 들어가는 숫자가 제목이다.
* 입력은 1부터 10,000보다 작거나 같은 자연수이다.

### 문제풀이
* 시간 제한은 2초, 입력은 10,000보다 작은 수이기 때문에 시간 복잡도를 크게 생각하지 않았다.
* 실버 4 문제이기도 하고, 브루드 포트를 사용하기로 했다.
* [브루드 포트란?](https://olrlobt.tistory.com/33)
* 무식한 힘을 갖는 알고리즘이란 뜻으로, 완전 탐색 알고리즘의 한 종류이지만 완전 탐색의 또 다른 이름으로 쓰이기도 한다. 브루트 포스 알고리즘은 완전탐색으로 답을 도출하는 알고리즘이며, 대부분은 반복문과 조건문을 통하여 답을 도출한다, 모든 경우의 수를 전부 탐색하기 때문에, 100%의 정확성을 보장하지만, 모든 경우의 수를 전부 탐색하기 때문에, 높은 시간 복잡도를 갖는다.
  * 사용 조건
    * 문제에서 달성하고자 하는 솔루션이 잘 정의 되어 있어야한다.
    * 문제를 해결할 수 있는 풀이의 수가 제한되어 있어야 한다.
  * 전체 탐색하는 방법으로는 선형 구조를 전체적으로 탐색하는 순차 탐색, 비선형 구조를 전체적으로 탐색하는 깊이 우선 탐색(DFS), 너비 우선 탐색(BFS) 가 기본적인 도구입니다.
  * 어떤 방식으로든 전체 탐색으로 문제를 해결한다면 브루트 포스 알고리즘으로 풀었다고 할 수 있습니다.
  
### 코드
* 1차 코드
  * 666을 contains를 통해서 종말의 수를 판단한다.
  * 미리 10만 까지의 종말의 수를 넣어서 가져온다.
  * 하지만, 메모리 초과로 실패했다.
```
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val titles = arrayOfNulls<Int>(size = 100_000)
    var title = 666
    var count = 0
    // 1부터 100,000까지의 영화 제목 담기
    while (count < 100_000) {
        while (true) {
            if (title.toString().contains("666")) {
                titles[count] = title
                title += 1
                break
            }
            title += 1
        }
        count += 1
    }
    // 입력된 순서의 제목 출력
    print(titles[readLine().toInt() - 1])
}
```
* 2차 코드
  * 666을 contains를 통해서 종말의 수를 판단한다.
  * N번 까지의 숫자만 구한다.
```
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val input = readLine().toInt()
    var title = 0
    var count = 0
    // 1부터 100,000까지의 영화 제목 담기
    while (count < input) {
        while (true) {
            title += 1

            if (title.toString().contains("666")) {
                break
            }
        }
        count += 1
    }
    // 입력된 순서의 제목 출력
    print(title)
}
```

### 해설
* 해당 문제는 브루트 포스 알고리즘 문제로, 모든 수를 나열하면서 중간에 666이 있는 지 검사하면 된다.
* 1666, 2666 등은 1000으로 나누면 나머지가 666이 되기 때문에 가능하지만 중간에 666이 있는 경우 나머지가 666이 되지 않아서 일의 자리를 지워준다.
* contains를 사용하는 경우 성능에 좋지 않기 때문에 해당 방법이 효율적이다.
```
fun main(args: Array<String>): Unit = with(System.`in`.bufferedReader()) {
    val input = readLine().toInt()
    var count = 1
    var title = 665
    var temp: Int
    // 입력된 input 까지의 666 값을 가진 숫자까지 연산
    while (count <= input) {
        title += 1
        temp = title
        while (temp >= 666) {
            if (temp % 1000 == 666) {
                count += 1
                break
            } else {
                temp /= 10
            }
        }
    }
    // 입력된 순서의 제목 출력
    print(title)
}
```