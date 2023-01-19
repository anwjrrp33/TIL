## [주식가격](https://school.programmers.co.kr/learn/courses/30/lessons/42584?language=java)

### 문제 설명
초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 solution 함수를 완성하세요.

### 제한사항
* prices의 각 가격은 1 이상 10,000 이하인 자연수입니다.
* prices의 길이는 2 이상 100,000 이하입니다.

해당 문제는 정확성 테스트뿐 아니라 효율성까지 테스트 하기 때문에 완전 탐색처럼 여러 개의 배열을 선언해서도 문제를 풀이할 수 있지만 효율성 테스트에서 실패하기 때문에 주어진 문제의 카테고리인 스택/큐 중에 선택해서 문제를 풀어야 한다.

문제 풀이의 구현을 처음엔 스택으로 처리하다 큐로 변경했는데 처음에 문제를 잘못 이해했다. `가격이 한번 떨어지면 나중에 오른다고 해도 올랐던 시간에 반영이 되지 않기` 때문에 큐로 처리하고 코드는 아래와 같이 구현했다.

슬슬 스택/큐 문제에 대해서 조금씩 익숙해져가는 느낌인데 조금씩 난이도를 높여야한다고 생각이 든다.

### 변경 전 코드
```
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];

        Queue<History> queue = new LinkedList<>();

        for (int i = 0; i < prices.length; i++) {
            queue.offer(new History(prices[i], 0, i));
        }

        while (!queue.isEmpty()) {
            History past = queue.poll();

            for (History history : queue) {
                past.addSecond();

                if (past.price > history.price) {
                    break;
                }
            }

            answer[past.order] = past.second;
        }

        return answer;
    }

    class History {
        int price;

        int second;

        int order;

        public History(int price, int second, int order) {
            this.price = price;
            this.second = second;
            this.order = order;
        }

        public void addSecond() {
            this.second++;
        }
    }
}
```

문제를 다 풀고 알게되었지만 효율성 부분에선 결국 O(n²) 이지만 문제의 케이스가 적기 때문에 통과하게 되었던 것이다. 그럼 효율적으로 풀기 위해선 어떤 방식을 채택해야 하는지를 보자면 처음 생각했던 스택의 방법이 맞았다. 문제의 의도가 정확한 코드는 아래와 같이 구현된다. 

### 변경 후 코드
```
import java.util.Stack;

class Solution {
    public int[] solution(int[] prices) {
        int[] ans = new int[prices.length];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[i] < prices[stack.peek()]) {
                ans[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            ans[stack.peek()] = prices.length - stack.peek() - 1;
            stack.pop();
        }

        return ans;
    }
}
```