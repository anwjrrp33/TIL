## 다리를 지나는 트럭

### 문제 설명
트럭 여러 대가 강을 가로지르는 일차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 알아내야 합니다. 다리에는 트럭이 최대 bridge_length대 올라갈 수 있으며, 다리는 weight 이하까지의 무게를 견딜 수 있습니다. 단, 다리에 완전히 오르지 않은 트럭의 무게는 무시합니다.

예를 들어, 트럭 2대가 올라갈 수 있고 무게를 10kg까지 견디는 다리가 있습니다. 무게가 [7, 4, 5, 6]kg인 트럭이 순서대로 최단 시간 안에 다리를 건너려면 다음과 같이 건너야 합니다.

<table class="table">
    <thead>
        <tr>
            <th>경과 시간</th>
            <th>다리를 지난 트럭</th>
            <th>다리를 건너는 트럭</th>
            <th>대기 트럭</th>
        </tr>
    </thead>
    <tbody>
    <tr>
        <td>0</td>
        <td>[]</td>
        <td>[]</td>
        <td>[7,4,5,6]</td>
        </tr>
        <tr>
        <td>1~2</td>
        <td>[]</td>
        <td>[7]</td>
        <td>[4,5,6]</td>
        </tr>
        <tr>
        <td>3</td>
        <td>[7]</td>
        <td>[4]</td>
        <td>[5,6]</td>
        </tr>
        <tr>
        <td>4</td>
        <td>[7]</td>
        <td>[4,5]</td>
        <td>[6]</td>
        </tr>
        <tr>
        <td>5</td>
        <td>[7,4]</td>
        <td>[5]</td>
        <td>[6]</td>
        </tr>
        <tr>
        <td>6~7</td>
        <td>[7,4,5]</td>
        <td>[6]</td>
        <td>[]</td>
        </tr>
        <tr>
        <td>8</td>
        <td>[7,4,5,6]</td>
        <td>[]</td>
        <td>[]</td>
    </tr>
    </tbody>
</table>

따라서, 모든 트럭이 다리를 지나려면 최소 8초가 걸립니다.

solution 함수의 매개변수로 다리에 올라갈 수 있는 트럭 수 bridge_length, 다리가 견딜 수 있는 무게 weight, 트럭 별 무게 truck_weights가 주어집니다. 이때 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 return 하도록 solution 함수를 완성하세요.

### 제한 조건
* bridge_length는 1 이상 10,000 이하입니다.
* weight는 1 이상 10,000 이하입니다.
* truck_weights의 길이는 1 이상 10,000 이하입니다.
* 모든 트럭의 무게는 1 이상 weight 이하입니다.

해당문제는 특정 무게까지 견디는 특정 길이의 다리에 트럭이 지나가는데 다리가 견딜 수 있을 정도로만 트럭이 건너고 있을 경우 최소 몇 초가 걸리는지 구하는 문제이다 코드는 아래와 같이 구현했지만 코드를 짜면서 조금 많은 고민을 했다. 결국엔 단순 반복문을 통한 문제풀이에 계속되는 조건문을 통한 분기처리까지 보기에 한눈에 들어오는 코드도 아니였는데 아직까진 이런 문제를 손쉽게 풀기에는 많이 부족하다.

### 코드
```
import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> bridge = new LinkedList<>();
		int sum = 0;
		int answer = 0; 

		for (int truck: truck_weights) {
            while(true) {
				if(bridge.isEmpty()) { 
					bridge.add(truck);
					sum += truck;
					answer++;
					break;
				}
                if(bridge.size() == bridge_length) {
					sum -= bridge.poll();
                    continue;
				}
                if(sum + truck <= weight) {
                    bridge.add(truck);
                    sum += truck;
                    answer++;
                    break;
                }  
                bridge.add(0);
                answer++;
			}
		}
		return answer + bridge_length; 
    }
}
```