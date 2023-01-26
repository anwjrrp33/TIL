package algorithm.programmers.소수찾기;

import java.util.HashSet;
import java.util.Set;

class Solution {

    private static Set<Integer> set = new HashSet<>();

    private static boolean[] visited;

    public int solution(String numbers) {
        visited = new boolean[numbers.length()];

        for(int i=0; i < numbers.length(); i++){
            dfs(numbers,"", i + 1);
        }
        System.out.println(set.toString());
        return set.size();
    }

    private static void dfs(String str, String temp, int m) {
        if(temp.length() == m){
            int num = Integer.parseInt(temp);
            if(!set.contains(num) && !isPrime(num)){
                set.add(num);
            }
            return;
        }
    
        for(int i=0; i<str.length(); i++){
            if(!visited[i]){
                visited[i] = true;
                temp += str.charAt(i);
                dfs(str, temp, m);
                visited[i] = false;
                temp = temp.substring(0, temp.length() - 1);
            }
        }
}

    private static boolean isPrime(int n) {
        if (n == 0 || n == 1) {
            return true;
        }
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if (n % i == 0) {
                return true;
            }
        }
        return false;
    }
}

class Main {
    public static void main(String[] args) {
        new Solution().solution("17");
        // new Solution().solution("011");
    }
}