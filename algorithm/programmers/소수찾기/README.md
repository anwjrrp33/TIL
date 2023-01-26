## [소수 찾기](https://school.programmers.co.kr/learn/courses/30/lessons/42839?language=java)

### 문제 설명
한자리 숫자가 적힌 종이 조각이 흩어져있습니다. 흩어진 종이 조각을 붙여 소수를 몇 개 만들 수 있는지 알아내려 합니다.

각 종이 조각에 적힌 숫자가 적힌 문자열 numbers가 주어졌을 때, 종이 조각으로 만들 수 있는 소수가 몇 개인지 return 하도록 solution 함수를 완성해주세요.

### 제한 사항
* numbers는 길이 1 이상 7 이하인 문자열입니다.
* numbers는 0~9까지 숫자만으로 이루어져 있습니다.
* "013"은 0, 1, 3 숫자가 적힌 종이 조각이 흩어져있다는 의미입니다.

### 풀이
프로그래머스 > 완전 탐색에 해당하는 문제인데, 각 숫자들로 이루어진 모든 소수를 찾는 문제이다. 읽기만 해서는 가능한 모든 경로를 찾기 때문에 `DFS`로 문제를 풀이해야한다. 보통 순열 문제는 DFS로 풀어야한다. 하지만 조건을 읽어면 `소수`이기 때문에 DFS로 해를 찾는 도중에 소수가 아니면 제외해야하기 때문에 이문제는 `DFS로 구현하는 백트래킹` 문제다.

코드는 아래와 같이 구현했는데 기본적인 재귀 방식으로 DFS를 구현했고 중복된 숫자는 제거하기 위해서 HashSet을 사용해서 중복된 숫자가 나오지 않도록 방지했다.

코드는 아래와 같다.

### 코드
```
import java.util.HashSet;
import java.util.Set;

class Solution {

    private static Set<Integer> set = new HashSet<>();

    private static boolean[] visited;

    public int solution(String numbers) {
        visited = new boolean[numbers.length()];

        for(int i=0; i < numbers.length(); i++){
            dfs(numbers,"", i + 1);
        }
        System.out.println(set.toString());
        return set.size();
    }

    private static void dfs(String str, String temp, int m) {
        if(temp.length() == m){
            int num = Integer.parseInt(temp);
            if(!set.contains(num) && !isPrime(num)){
                set.add(num);
            }
            return;
        }
    
        for(int i=0; i<str.length(); i++){
            if(!visited[i]){
                visited[i] = true;
                temp += str.charAt(i);
                dfs(str, temp, m);
                visited[i] = false;
                temp = temp.substring(0, temp.length() - 1);
            }
        }
}

    private static boolean isPrime(int n) {
        if (n == 0 || n == 1) {
            return true;
        }
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if (n % i == 0) {
                return true;
            }
        }
        return false;
    }
}
```