## [모의고사](https://school.programmers.co.kr/learn/courses/30/lessons/42840?language=java)

정답이 순서대로 들은 배열 answers이 주어졌을 때, 수포자1부터 3까지 중에서 가장 많은 문제를 맞힌 사람이 누구인지 배열에 담아 return 하는 문제다.

### 제한조건
* 시험은 최대 10,000 문제로 구성되어있습니다.
* 문제의 정답은 1, 2, 3, 4, 5중 하나입니다.
* 가장 높은 점수를 받은 사람이 여럿일 경우, return하는 값을 오름차순 정렬해주세요.

제한조건에 별 다른 사항은 없지만 점수가 같은 경우 이름을 기준으로 오름차순을 해야한다.

처음에는 단순 for문으로 단순히 맞으면 List에 수포자 인덱스에 맞춰서 count를 센 후 0을 제외해서 정답을 제외해서 답을 구했는데 코드를 보고나니까 이게 자바스러운 코드인가? 라는 의문점으로 시작해서 객체에 책임과 역할을 부여해서 다시 코드를 작성했다.

확실히 객체를 선언해서 코드를 작성하면 코드의 양이 길어지는데 좀 더 자바스럽게 OOP적 사고로 문제를 풀이하고 코드가 명확해진거 같다.
```
class Solution {
    public int[] solution(int[] answers) {
        Sufoja sufoja1 = new Sufoja(1, new int[] { 1, 2, 3, 4, 5 }, 0);
        Sufoja sufoja2 = new Sufoja(2, new int[] { 2, 1, 2, 3, 2, 4, 2, 5 }, 0);
        Sufoja sufoja3 = new Sufoja(3, new int[] { 3, 3, 1, 1, 2, 2, 4, 4, 5, 5 }, 0);

        for (int i = 0; i < answers.length; i++) {
            int answer = answers[i];

            sufoja1.grade(answer, i);
            sufoja2.grade(answer, i);
            sufoja3.grade(answer, i);
        }

        List<Sufoja> sufojas = Arrays.asList(sufoja1, sufoja2, sufoja3);
        
        Collections.sort(sufojas);

        return sufojas.stream()
            .filter(sufoja -> sufoja.count > 0)
            .mapToInt(sufoja -> sufoja.name)
            .toArray();
    }

    class Sufoja implements Comparable<Sufoja> {
        int name;

        int answers[];

        int count;

        public Sufoja(int name, int answers[], int count) {
            this.name = name;
            this.answers = answers;
            this.count = count;
        }

        public void grade(int answer, int index) {
            int mark = this.answers[index % (this.answers.length - 1)];

            if (answer == mark) {
                correct();
            }
        }

        private void correct() {
            this.count++;
        }

        @Override
        public int compareTo(Sufoja s) {
            if(s.count - this.count == 0) {
                return this.name - s.name;
            }
            return s.count - this.count;
        }
    }
}
```