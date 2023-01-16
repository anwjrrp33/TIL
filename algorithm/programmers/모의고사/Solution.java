package algorithm.programmers.모의고사;

import java.util.*;

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

class Main {
    public static void main(String[] args) {
        new Solution().solution(new int[] { 1, 2, 3, 4, 5 });
    }
}