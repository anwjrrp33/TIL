## [K번째수](https://school.programmers.co.kr/learn/courses/30/lessons/42748)

### 문제 설명
배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때, k번째에 있는 수를 구하려 합니다.
예를 들어 array가 [1, 5, 2, 6, 3, 7, 4], i = 2, j = 5, k = 3이라면
1. array의 2번째부터 5번째까지 자르면 [5, 2, 6, 3]입니다.
2. 1에서 나온 배열을 정렬하면 [2, 3, 5, 6]입니다.
3. 2에서 나온 배열의 3번째 숫자는 5입니다.
배열 array, [i, j, k]를 원소로 가진 2차원 배열 commands가 매개변수로 주어질 때, commands의 모든 원소에 대해 앞서 설명한 연산을 적용했을 때 나온 결과를 배열에 담아 return 하도록 solution 함수를 작성해주세요.

### 제한 사항
* array의 길이는 1 이상 100 이하입니다.
* array의 각 원소는 1 이상 100 이하입니다.
* commands의 길이는 1 이상 50 이하입니다.
* commands의 각 원소는 길이가 3입니다.

프로그래머스의 1레벌 정렬에 해당하는 문제로 쉬운 문제이다 주어진 배열의 i, j사이의 인덱스를 자르고 가져와서 정렬을 했을 때 k번째의 수를 구하는 문제이다 해당 문제는 순수하게 자바코드로 구현하면 코드가 길어지는데 stream을 사용해서 짧게 구현해서 코드를 아래와 같이 작성했다.

### 코드
```
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        List<Integer> answer = new ArrayList<>();

        for(int[] command: commands) {
            answer.add(Arrays.stream(array)
                    .boxed()
                    .collect(Collectors.toList())
                    .subList(command[0] - 1, command[1])
                    .stream()
                    .sorted().collect(Collectors.toList()).get(command[2] - 1));
        }

        return answer.stream().mapToInt(i -> i).toArray();
    }
}
```