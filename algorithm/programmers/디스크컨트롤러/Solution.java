package algorithm.programmers.디스크컨트롤러;

import java.util.*;

class Solution {
    public int solution(int[][] jobs) {
        Arrays.sort(jobs, (a, b) -> a[0] - b[0]);
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        
        int index = 0;
        int count = 0;
        int total = 0;
        int end = 0;

        while(count < jobs.length) {
            while(index < jobs.length && jobs[index][0] <= end) {
                pq.add(jobs[index++]);
            }
            if(pq.isEmpty()) {
                end = jobs[index][0];
            } else {
                int[] job = pq.poll();
                total += job[1] + end - job[0];
                end += job[1];
                count++;
            }
        }

        return total / jobs.length;
    }
}

class Main {
    public static void main(String[] args) {
        int[][] jobs1 = new int[][] {{0, 3}, {1, 9}, {2, 6}};
        new Solution().solution(jobs1);
    }
}