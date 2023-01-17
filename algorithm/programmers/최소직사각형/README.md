## [최소직사각형](https://school.programmers.co.kr/learn/courses/30/lessons/86491)

### 문제설명
명함 지갑을 만드는 회사에서 지갑의 크기를 정하려고 합니다. 다양한 모양과 크기의 명함들을 모두 수납할 수 있으면서, 작아서 들고 다니기 편한 지갑을 만들어야 합니다. 이러한 요건을 만족하는 지갑을 만들기 위해 디자인팀은 모든 명함의 가로 길이와 세로 길이를 조사했습니다.

아래 표는 4가지 명함의 가로 길이와 세로 길이를 나타냅니다.

<table class="table">
    <thead>
        <tr>
            <th>명함 번호</th>
            <th>가로 길이</th>
            <th>세로 길이</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>1</td>
            <td>60</td>
            <td>50</td>
        </tr>
        <tr>
            <td>2</td>
            <td>30</td>
            <td>70</td>
        </tr>
        <tr>
            <td>3</td>
            <td>60</td>
            <td>30</td>
        </tr>
        <tr>
            <td>4</td>
            <td>80</td>
            <td>40</td>
        </tr>
    </tbody>
</table>

가장 긴 가로 길이와 세로 길이가 각각 80, 70이기 때문에 80(가로) x 70(세로) 크기의 지갑을 만들면 모든 명함들을 수납할 수 있습니다. 하지만 2번 명함을 가로로 눕혀 수납한다면 80(가로) x 50(세로) 크기의 지갑으로 모든 명함들을 수납할 수 있습니다. 이때의 지갑 크기는 4000(=80 x 50)입니다.

모든 명함의 가로 길이와 세로 길이를 나타내는 2차원 배열 sizes가 매개변수로 주어집니다. 모든 명함을 수납할 수 있는 가장 작은 지갑을 만들 때, 지갑의 크기를 return 하도록 solution 함수를 완성해주세요.

### 제한 사항
* sizes의 길이는 1 이상 10,000 이하입니다.
* sizes의 원소는 [w, h] 형식입니다.
* w는 명함의 가로 길이를 나타냅니다.
* h는 명함의 세로 길이를 나타냅니다.
* w와 h는 1 이상 1,000 이하인 자연수입니다.

완전탐색 유형에 해당하는 문제인데 명함을 눕혀서 넣을 수 있기 때문에 각 가로와 세로의 최대 사이즈가 아닌 복합적으로 봐야한다. 제한 사항의 경우 길이는 10000이하이며 가로와 세로의 길이가 1000 이하인 자연수기 때문에 int 형 타입으로 연산이 가능하다.

자바스럽게 명함 자체를 객체로 선언 후 정렬을 통해서 문제를 해결하려고 했으나 임의로 변하는 가로와 세로 값 때문에 우선순위 큐로 가로와 세로를 따로 담은 뒤 내림차순으로 정렬하도록 변경했고 제일 각각 첫 번째 값으로 연산하도록 구현했다.

객체를 생성해서 가로와 세로에 해당하는 우선순위 큐에 각 객체를 담는건 너무 비효율적이라고 생각했다.

```
import java.util.Collections;
import java.util.PriorityQueue;

class Solution {
    public int solution(int[][] sizes) {
        PriorityQueue<Integer> width = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> height = new PriorityQueue<>(Collections.reverseOrder());

        for (int[] size: sizes) {
            if (size[0] >= size[1]) {
                width.offer(size[0]);
                height.offer(size[1]);
                continue;
            }

            width.offer(size[1]);
            height.offer(size[0]);
        }

        return width.poll() * height.poll();
    }
}
```