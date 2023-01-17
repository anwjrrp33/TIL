package algorithm.programmers.최소직사각형;

class Solution {
    public int solution(int[][] sizes) {
        int length = 0; 
        int height = 0;

        for (int[] card : sizes) {
            length = Math.max(length, Math.max(card[0], card[1]));
            height = Math.max(height, Math.min(card[0], card[1]));
        }

        return length * height;
    }
}