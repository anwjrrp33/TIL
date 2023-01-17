## [기능개발](https://school.programmers.co.kr/learn/courses/30/lessons/42586)

### 문제 설명
프로그래머스 팀에서는 기능 개선 작업을 수행 중입니다. 각 기능은 진도가 100%일 때 서비스에 반영할 수 있습니다.

또, 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다.

먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses와 각 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때 각 배포마다 몇 개의 기능이 배포되는지를 return 하도록 solution 함수를 완성하세요.

### 제한 사항
* 작업의 개수(progresses, speeds배열의 길이)는 100개 이하입니다.
* 작업 진도는 100 미만의 자연수입니다.
* 작업 속도는 100 이하의 자연수입니다.
* 배포는 하루에 한 번만 할 수 있으며, 하루의 끝에 이루어진다고 가정합니다. 예를 들어 진도율이 95%인 작업의 개발 속도가 하루에 4%라면 배포는 2일 뒤에 이루어집니다.

각 기능의 배포 날짜를 배열에 담아줘야 하는 문제인데 먼저 배포되어야 하는 앞 순서의 배포가 되지 않으면 끝날때까지 배포를 할 수 없다. 제한 사항의 경우 배열의 길이가 100개 이하기 때문에 알고리즘의 효율을 따지는 문제는 아니다.

먼저 상태를 계속 가지고 있어야 하는 작업을 클래스로 생성한 후 작업은 날짜, 속도를 가지고 있게했다 이를 통해서 코드는 아래와 같이 구현했다.

```
import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Work> works = new LinkedList<>();
        List<Integer> completes = new ArrayList<>();

        for (int i = 0; i < progresses.length; i++) {
            works.offer(new Work(progresses[i], speeds[i], 0));
        }
        
        int day = 0;
        while (works.size() > 0) {
            if (works.peek().offWork()) {
                Work work = works.poll();

                if (day < work.day) {
                    day = work.day;
                    completes.add(1);
                    continue;
                }

                completes.set(completes.size() - 1, completes.get(completes.size() - 1) + 1);
            }
        }

        return completes.stream()
            .mapToInt(Integer::intValue)
            .toArray();
    }

    class Work {
        int progress;

        int speed;

        int day;

        public Work(int progress, int speed, int day) {
            this.progress = progress;
            this.speed = speed;
            this.day = day;
        }

        public boolean offWork() {
            if (this.progress < 100) {
                this.progress += speed;
                this.day++;
            }
            if (this.progress >= 100) {
                return true;
            }
            return false;
        }
    }
}
```