package com;

import java.util.HashMap;
import java.util.Map;

public class No3 {
    public int lengthOfLongestSubstring(String s) {
        int head = 0, tail = 0, result = 0;
        Map<Character, Integer> location = new HashMap<>();
        for (head = 0; head < s.length(); head++) {
            if (location.get(s.charAt(head)) != null) {
                tail = Math.max(location.get(s.charAt(head)) + 1, tail);//tail不能往回走
                location.put(s.charAt(head), head);
            } else {
                location.put(s.charAt(head), head);
            }
            result = Math.max(result, head - tail +1);
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "abba";
        No3 no3 = new No3();
        System.out.println(no3.lengthOfLongestSubstring(s));
    }
}
