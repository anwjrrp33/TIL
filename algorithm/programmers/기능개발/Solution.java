package algorithm.programmers.기능개발;

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