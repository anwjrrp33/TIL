package algorithm.programmers.크기가작은부분문자열;

class Solution {
    public int solution(String t, String p) {
        int answer = 0;
        Long a = Long.parseLong(p);

        for (int i = 0; i <= t.length() - p.length(); i++) {
            Long b = Long.parseLong(t.substring(i, i + p.length()));
        
            if (a >= b) {
                answer++;
            }
        }

        return answer;
    }
}