package algorithm.programmers.프린터;

import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int solution(int[] priorities, int location) {
        Queue<Print> pq = new LinkedList<>();

        for (int i = 0; i < priorities.length; i++) {
            pq.offer(new Print(i, priorities[i]));
        }

        int answer = 0;

        while (!pq.isEmpty()) {
            Print print = pq.poll();

            if(print.isPriority(pq)) {
                pq.offer(print);
                continue;
            }

            answer++;

            if(print.isLocation(location)) {
                break;
            }
        }

        return answer;
    }

    class Print {
        int location;

        int priority;

        public Print(int location, int priority) {
            this.location = location;
            this.priority = priority;
        }

        public boolean isPriority(Queue<Print> pq) {
            for (Print p : pq) {
                if (this.priority < p.priority) {
                    return true;
                } 
            }
            
            return false;
        }

        public boolean isLocation(int location) {
            if (this.location == location) {
                return true;
            }

            return false;
        }
    }
}