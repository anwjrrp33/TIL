## [올바른 괄호](https://school.programmers.co.kr/learn/courses/30/lessons/12909?language=java)

### 문제 설명
괄호가 바르게 짝지어졌다는 것은 '(' 문자로 열렸으면 반드시 짝지어서 ')' 문자로 닫혀야 한다는 뜻입니다. 예를 들어
* "()()" 또는 "(())()" 는 올바른 괄호입니다.
* ")()(" 또는 "(()(" 는 올바르지 않은 괄호입니다.
'(' 또는 ')' 로만 이루어진 문자열 s가 주어졌을 때, 문자열 s가 올바른 괄호이면 true를 return 하고, 올바르지 않은 괄호이면 false를 return 하는 solution 함수를 완성해 주세요.

### 제한 사항
* 문자열 s의 길이 : 100,000 이하의 자연수
* 문자열 s는 '(' 또는 ')' 로만 이루어져 있습니다.

문제를 읽어보면 올바른 괄호이기 때문에 큐 문제가 아닌 스택으로 풀어야 하는 문제이다 또한 주어지는 문자열 s의 길이가 100,000 이하의 자연수 이기 때문에 효율성 부분에서는 크게 신경쓸 필요가 없다. 코드는 아래와 같이 구현했는데 스택에 `(`를 쌓고 `)`일때 스택에서 데이터 하나를 제거해주면 된다 제거할 때 스택의 사이즈가 0이라면 해당 괄호는 올바르지 않기 때문에 바로 false를 해주면 된다. 모든 괄호의 체크가 끝났을 때 스택의 사이즈가 0이 아니라면 해당의 경우도 false이다.

### 코드
```
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
```