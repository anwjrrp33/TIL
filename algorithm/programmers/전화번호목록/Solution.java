package algorithm.programmers.전화번호목록;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;

        Map<String, String> phoneBookMap = Arrays.stream(phone_book).collect(Collectors.toMap(s -> s, s -> s));

        for(String phone: phone_book) {
            for(int i = 1; i < phone.length(); i++) {
                if(phoneBookMap.containsKey(phone.substring(0, i))) {
                    return false;
                }
            }
        }

        return answer;
    }
}