## [가장 가까운 같은 글자](https://school.programmers.co.kr/learn/courses/30/lessons/142086)

해당 문제는 주어진 문자열 s의 각 위치마다 자보다 앞에 나왔으면서 자신과 가장 가까운 곳에 있는 같은 글자의 위치를 구하는 문제인데 단순 반복문으로 구현이 가능하다
코드를 구현할 때 Array를 선언해서 구했는데 Map을 사용해서 좀 더 짧게 구현이 가능하다.

### 변경 전
```
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        
        char[] chars = s.toCharArray();
        List<Character> characters = new ArrayList<>();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            int index = characters.indexOf(c);

            if (index > -1) {
                characters.set(index, '0');
                index = i - index;
            }
            
            characters.add(c);
            answer[i] = index;
        }

        return answer;
    }
}
```

### 변경 후 
```
import java.util.*;

class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        
        HashMap<Character, Integer> map = new HashMap<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            answer[i] = i - map.getOrDefault(c, i + 1);
            map.put(c, i);
        }

        return answer;
    }
}
```