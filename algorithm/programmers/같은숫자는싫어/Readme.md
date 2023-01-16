## [같은 숫자는 싫어](https://school.programmers.co.kr/learn/courses/30/lessons/12906)

숫자 0부터 9까지 이루어진 배열 arr가 주어지면 연속적으로 나타나는 숫자는 하나만 남기고 전부 제거 후 남은 수들을 반환하는 문제이다.
- arr = [1, 1, 3, 3, 0, 1, 1] 이면 [1, 3, 0, 1] 을 return 합니다.
- arr = [4, 4, 4, 3, 3] 이면 [4, 3] 을 return 합니다.

제한사항
- 배열 arr의 크기 : 1,000,000 이하의 자연수
- 배열 arr의 원소의 크기 : 0보다 크거나 같고 9보다 작거나 같은 정수

이 문제는 코딩테스트 고득점 Kit 스택/큐에 분류되어 있었고 처음 문제를 읽었을 때 단순히 HashSet을 이용해서 중복된 숫자를 제거하면 되는거 아닌가? 라는 생각으로 구현을 했지만 당연하게도 연속적으로 나타내는 숫자 하나만 남기기 때문에 실패했다.
```
public class Solution {
    public int[] solution(int[] arr) {
        Set<Integer> set = new HashSet<>();

        for (int number : arr) {
            set.add(number);
        }

        return set.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
```
두 번쨰로는 Queue를 사용해서 문제를 풀었는데 Queue의 경우 무조건 처음에 들어가는 숫자만 기억할 수 있어서 이 방법도 옳지않는 방법이였다.
```
public class Solution {
    public int[] solution(int[] arr) {
        Queue<Integer> queue = new LinkedList<>();

        for (int number : arr) {
            if (queue.isEmpty()) {
                queue.offer(number);
            }
            if (queue.peek() != number) {
                queue.offer(number);
            }
        }

        return queue.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
```
마지막으로 Stack을 사용해서 풀제를 풀었는데 해당 문제 자체가 Stack으로 풀 수 있도록 의도 자체를 정한거 같았다.
```
public class Solution {
    public int[] solution(int[] arr) {
        Stack<Integer> stack = new Stack<>();

        for (int number : arr) {
            if (stack.empty()) {
                stack.push(number);
            }
            if (stack.peek() != number) {
                stack.push(number);
            }
        }

        int[] answer = new int[stack.size()];

        for (int i = answer.length - 1; i >= 0; i--) {
            answer[i] = stack.pop();
        }

        return answer;
    }
}
```