## [가장 큰 수](https://school.programmers.co.kr/learn/courses/30/lessons/42746)

### 문제 설명
0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요.
예를 들어, 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰 수는 6210입니다.
0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.

### 제한 사항
* numbers의 길이는 1 이상 100,000 이하입니다.
* numbers의 원소는 0 이상 1,000 이하입니다.
* 정답이 너무 클 수 있으니 문자열로 바꾸어 return 합니다.

최근에 계속 클래스에 Comparable를 implements를 쓰는 방식으로 문제를 해결하다 보니까 람다식으로만 해결하는 문제를 풀고 싶어서 선택했다. 주어진 배열에서 가장 큰 수를 정렬하는 것이 아닌 주어진 배열에서 제일 첫번째에 나온 숫자가 가장 큰수를 기준으로 정렬을 한 후 문제를 풀어야한다. 숫자를 잘라서 하는 방식은 효율적이지 못해서 숫자를 문자로 변환 후 정렬하는 방식으로 문제를 해결했다. 코드는 아래와 같이 구현했다.

### 코드
```
import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public String solution(int[] numbers) {
        List<String> list = Arrays.stream(numbers).mapToObj(String::valueOf).collect(Collectors.toList());

        Collections.sort(list, (o1, o2) -> (o2 + o1).compareTo(o1 + o2));

        if(list.get(0).toString().equals("0")) {
            return "0";
        }

        return list.toString().replaceAll("[\\[, \\]]", "");
    }
}
```