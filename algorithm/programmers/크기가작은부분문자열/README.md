## [크기가 작은 부분문자열](https://school.programmers.co.kr/learn/courses/30/lessons/147355)

최근에 계속 나를 기준으로 어려운 문제만 풀어서 조금 쉬운 문제 하나를 풀려고 선택한 문제이다.

문제를 읽어보면 입력된 문자열 t에서 입력된 문자열 p보다 작거나 같은 문자열 구해야하는데 문자열 t안에서 무작위 값도 아닌 앞에서부터 길이만 같에 잘라주면 되는 단순 문자열 문제였다.

제한사항을 보면 길이가 18자까지 이기떄문에 형타입을 int로 쓰면 런타임 에러가 발생하기에 Long값을 써주기만 하면 쉽게 구현했다. 조금 더 간단하게 구현할려면 stream을 써서 구현해도 된다.

### 코드
```
class Solution {
    public int solution(String t, String p) {
        int answer = 0;
        Long a = Long.parseLong(p);

        for (int i = 0; i <= t.length() - p.length(); i++) {
            Long b = Long.parseLong(t.substring(i, i + p.length()));
        
            if (a >= b) {
                answer++;
            }
        }

        return answer;
    }
}
```