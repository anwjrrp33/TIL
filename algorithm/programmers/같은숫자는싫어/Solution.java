package algorithm.programmers.같은숫자는싫어;

import java.util.*;

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
