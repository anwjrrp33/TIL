package algorithm.programmers.주식가격;

import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];

        Queue<History> queue = new LinkedList<>();

        for (int i = 0; i < prices.length; i++) {
            queue.offer(new History(prices[i], 0, i));
        }

        while (!queue.isEmpty()) {
            History past = queue.poll();

            for (History history : queue) {
                past.addSecond();

                if (past.price > history.price) {
                    break;
                }
            }

            answer[past.order] = past.second;
        }

        return answer;
    }

    class History {
        int price;

        int second;

        int order;

        public History(int price, int second, int order) {
            this.price = price;
            this.second = second;
            this.order = order;
        }

        public void addSecond() {
            this.second++;
        }
    }
}

class Main {
    public static void main(String[] args) {
        new Solution().solution(new int[] { 1, 2, 3, 2, 3 });
    }
}