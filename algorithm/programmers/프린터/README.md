## [프린터](https://school.programmers.co.kr/learn/courses/30/lessons/42587)

### 문제 설명
일반적인 프린터는 인쇄 요청이 들어온 순서대로 인쇄합니다. 그렇기 때문에 중요한 문서가 나중에 인쇄될 수 있습니다. 이런 문제를 보완하기 위해 중요도가 높은 문서를 먼저 인쇄하는 프린터를 개발했습니다. 이 새롭게 개발한 프린터는 아래와 같은 방식으로 인쇄 작업을 수행합니다.
```
1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
3. 그렇지 않으면 J를 인쇄합니다.
```
예를 들어, 4개의 문서(A, B, C, D)가 순서대로 인쇄 대기목록에 있고 중요도가 2 1 3 2 라면 C D A B 순으로 인쇄하게 됩니다.

내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 알고 싶습니다. 위의 예에서 C는 1번째로, A는 3번째로 인쇄됩니다.

현재 대기목록에 있는 문서의 중요도가 순서대로 담긴 배열 priorities와 내가 인쇄를 요청한 문서가 현재 대기목록의 어떤 위치에 있는지를 알려주는 location이 매개변수로 주어질 때, 내가 인쇄를 요청한 문서가 몇 번째로 인쇄되는지 return 하도록 solution 함수를 작성해주세요.

### 제한 사항
* 현재 대기목록에는 1개 이상 100개 이하의 문서가 있습니다.
* 인쇄 작업의 중요도는 1~9로 표현하며 숫자가 클수록 중요하다는 뜻입니다.
* location은 0 이상 (현재 대기목록에 있는 작업 수 - 1) 이하의 값을 가지며 대기목록의 가장 앞에 있으면 0, 두 번째에 있으면 1로 표현합니다.

문제를 처음 읽었을 때 중요도에 따른 순서가 정해져있다라는 말을 보고 바로 우선순위 큐를 떠올렸고 당연히 우선순위 큐로 문제를 구현했다. 하지만 우리가 하고 있는 언어는 바로 Java다.

단순히 우선순위 큐에 리버스로 정렬해서 while으로 for문을 돌려서 하나씩 제거해준 후 큐가 사라질때까지 돌리는 작업보다 class에 책임과 역할을 부여해주고 싶었는데 class를 우선순위 큐로 구현하면 순서에 따른 Queue 따로 가지고 있어야 하는 필요가 있었다.

그래서 해당 문제는 우선순위 큐로 문제를 푸는 방향이였지만 큐로 구현하고 큐 안에 더 큰 중요도가 있다면 뒤로 다시 넣는 식으로 코드를 구현했다.

### 코드
```
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int solution(int[] priorities, int location) {
        Queue<Print> pq = new LinkedList<>();

        for (int i = 0; i < priorities.length; i++) {
            pq.offer(new Print(i, priorities[i]));
        }

        int answer = 0;

        while (!pq.isEmpty()) {
            Print print = pq.poll();

            if(print.isPriority(pq)) {
                pq.offer(print);
                continue;
            }

            answer++;

            if(print.isLocation(location)) {
                break;
            }
        }

        return answer;
    }

    class Print {
        int location;

        int priority;

        public Print(int location, int priority) {
            this.location = location;
            this.priority = priority;
        }

        public boolean isPriority(Queue<Print> pq) {
            for (Print p : pq) {
                if (this.priority < p.priority) {
                    return true;
                } 
            }
            
            return false;
        }

        public boolean isLocation(int location) {
            if (this.location == location) {
                return true;
            }

            return false;
        }
    }
}
```