package algorithm.programmers.올바른괄호;

import java.util.Stack;

class Solution {
    boolean solution(String s) {
        
        Stack<Character> brackets = new Stack<>();

        for (char bracket : s.toCharArray()) {
            if (bracket == '(') {
                brackets.push(bracket);
            }
            if (bracket == ')') {
                if (brackets.size() == 0) {
                    return false;
                }
                brackets.pop();
            }
        }

        if (brackets.size() > 0) {
            return false;
        }

        return true;
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println(new Solution().solution(")()("));
        
    }
}