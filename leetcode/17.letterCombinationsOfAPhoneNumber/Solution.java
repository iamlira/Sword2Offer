package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class No17 {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if(digits == null || digits.equals("")){
            return result;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        StringBuilder str = new StringBuilder();
        backTrack(phoneMap, result, 0, str, digits);
        return result;
    }

    public void backTrack(Map<Character, String> phoneMap, List<String> result, int cur, StringBuilder str, String digits) {
        if (digits.length() == cur) {
            result.add(str.toString());
            return;
        }
        String key = phoneMap.get(digits.charAt(cur));
        for (int i = 0; i < key.length(); i++) {
            str.append(key.charAt(i));
            backTrack(phoneMap, result, cur + 1, str, digits);
            str.deleteCharAt(str.length() - 1);
        }
    }
}

