## [더 맵게](https://school.programmers.co.kr/learn/courses/30/lessons/42626)

### 제한 사항
scoville의 길이는 2 이상 1,000,000 이하입니다.
K는 0 이상 1,000,000,000 이하입니다.
scoville의 원소는 각각 0 이상 1,000,000 이하입니다.
모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1을 return 합니다.

프로그래머스 > 고득점 Kit > 힙에 관련된 문제인데 문제 내용은 우선순위 큐를 활용해서 풀어야한다.
힙에 대한 지식은 [여기](https://st-lab.tistory.com/205)를 활용해서 다시 한번 더 지식을 다듬었다.
코드는 아래와 같이 구현했다.

```
import java.util.PriorityQueue;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for (int i = 0; i < scoville.length; i++) {
            pq.offer(scoville[i]);
        }
        
        while(pq.peek() < K) {
            if (pq.size() == 1)
                return -1;
            pq.add(pq.poll() + pq.poll() * 2);
            answer++;
        }
        
        return answer;
    }
}
```