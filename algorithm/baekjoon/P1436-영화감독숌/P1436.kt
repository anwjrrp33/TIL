package algorithm.baekjoon.`P1436-영화감독숌`

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