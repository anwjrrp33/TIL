package algorithm.programmers.가장큰수;

import java.util.*;
import java.util.stream.Collectors;

class Solution {
    public String solution(int[] numbers) {
        List<String> list = Arrays.stream(numbers).mapToObj(String::valueOf).collect(Collectors.toList());

        Collections.sort(list, (o1, o2) -> (o2 + o1).compareTo(o1 + o2));

        if(list.get(0).toString().equals("0")) {
            return "0";
        }

        return list.toString().replaceAll("[\\[, \\]]", "");
    }
}