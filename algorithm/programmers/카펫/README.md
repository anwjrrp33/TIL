## [카펫](https://school.programmers.co.kr/learn/courses/30/lessons/42842?language=java)

### 문제 설명
Leo는 카펫을 사러 갔다가 아래 그림과 같이 중앙에는 노란색으로 칠해져 있고 테두리 1줄은 갈색으로 칠해져 있는 격자 모양 카펫을 봤습니다.

![img](https://grepp-programmers.s3.ap-northeast-2.amazonaws.com/files/production/b1ebb809-f333-4df2-bc81-02682900dc2d/carpet.png)

Leo는 집으로 돌아와서 아까 본 카펫의 노란색과 갈색으로 색칠된 격자의 개수는 기억했지만, 전체 카펫의 크기는 기억하지 못했습니다.

Leo가 본 카펫에서 갈색 격자의 수 brown, 노란색 격자의 수 yellow가 매개변수로 주어질 때 카펫의 가로, 세로 크기를 순서대로 배열에 담아 return 하도록 solution 함수를 작성해주세요.

### 제한 사항
* 갈색 격자의 수 brown은 8 이상 5,000 이하인 자연수입니다.
* 노란색 격자의 수 yellow는 1 이상 2,000,000 이하인 자연수입니다.
* 카펫의 가로 길이는 세로 길이와 같거나, 세로 길이보다 깁니다.

문제를 읽어보고 단순히 완전 탐색이 아닌 특정한 조건을 잘 살펴봐야하는 문제이다. `테두리 1줄은 갈색으로 칠해져 있는`, `가로 길이는 세로 길이와 같거나, 세로 길이보다 긴` 이 문제를 통해서 문제의 공식을 구할 수 있다.

```
카펫 = 가로 * 세로
노란색 = (가로 - 2) * (세로 - 2)
```

위에 조건에 해당 된다면 임의의 수 가로, 세로가 정답으로 알 수 있다. 코드는 아래와 같이 자바 코드로 구현했다.

### 코드
```
class Solution {
    public int[] solution(int brown, int yellow) {
        Carpet carpet = new Carpet(brown + yellow, 0, brown + yellow);
        
        while (carpet.row >= carpet.column) {
            carpet.cut();

            if (carpet.sum % carpet.row != 0) {
                continue;
            }

            if (carpet.isComplete(yellow)) {
                break;
            }
        }

        return carpet.size();
    }

    class Carpet {
        int row;

        int column;

        int sum;

        public Carpet(int row, int column, int sum) {
            this.row = row;
            this.column = column;
            this.sum = sum;
        }

        public void cut() {
            this.row--;
            this.column = sum / row;
        }

        public boolean isComplete(int yellow) {
            return yellow == (this.row - 2) * (this.column - 2);
        }

        public int[] size() {
            return new int[] { this.row, this.column };
        }
    }
}
```