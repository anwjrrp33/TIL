## [디스크 컨트롤러](https://school.programmers.co.kr/learn/courses/30/lessons/42627)

### 문제 설명
* 하드디스크는 한 번에 하나의 작업만 수행할 수 있습니다. 디스크 컨트롤러를 구현하는 방법은 여러 가지가 있습니다. 가장 일반적인 방법은 요청이 들어온 순서대로 처리하는 것입니다.

### 문제 풀이
* 프로그래머스 > 고득점 Kit > Heap에 해당하는 문제입니다. Heap 해당하는 문제이기 때문에 우선순위 큐를 사용해야 합니다. 먼저 job을 정렬한 후 작업시간이 짧은 순서대로 처리하도록 진행합니다. 코드는 아래와 같이 구현됬습니다.

### 코드
```
import java.util.*;

class Solution {
    public int solution(int[][] jobs) {
        Arrays.sort(jobs, (a, b) -> a[0] - b[0]);
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        
        int index = 0;
        int count = 0;
        int total = 0;
        int end = 0;

        while(count < jobs.length) {
            while(index < jobs.length && jobs[index][0] <= end) {
                pq.add(jobs[index++]);
            }
            if(pq.isEmpty()) {
                end = jobs[index][0];
            } else {
                int[] job = pq.poll();
                total += job[1] + end - job[0];
                end += job[1];
                count++;
            }
        }

        return total / jobs.length;
    }
}
```