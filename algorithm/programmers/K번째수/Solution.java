package algorithm.programmers.K번째수;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        List<Integer> answer = new ArrayList<>();

        for(int[] command: commands) {
            answer.add(Arrays.stream(array)
                    .boxed()
                    .collect(Collectors.toList())
                    .subList(command[0] - 1, command[1])
                    .stream()
                    .sorted().collect(Collectors.toList()).get(command[2] - 1));
        }

        return answer.stream().mapToInt(i -> i).toArray();
    }
}