package algorithm.programmers.최소직사각형;

import java.util.Collections;
import java.util.PriorityQueue;

class Solution {
    public int solution(int[][] sizes) {
        PriorityQueue<Integer> width = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> height = new PriorityQueue<>(Collections.reverseOrder());

        for (int[] size: sizes) {
            if (size[0] >= size[1]) {
                width.offer(size[0]);
                height.offer(size[1]);
                continue;
            }

            width.offer(size[1]);
            height.offer(size[0]);
        }

        return width.poll() * height.poll();
    }
}