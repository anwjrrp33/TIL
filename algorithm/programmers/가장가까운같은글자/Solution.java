package algorithm.programmers.가장가까운같은글자;

import java.util.*;

class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        
        HashMap<Character, Integer> map = new HashMap<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            answer[i] = i - map.getOrDefault(c, i + 1);
            map.put(c, i);
        }

        return answer;
    }
}

class Main {
    public static void main(String[] args) {
        new Solution().solution("banana");
    }
}
