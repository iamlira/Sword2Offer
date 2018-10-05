public class Solution {
	/*
	 * 题目描述：在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,
	 * 并返回它的位置, 如果没有则返回 -1.
	 * 思路:同样是空间换时间问题，这里使用hash表，每个char在ASCII码中的数字索引作为他的hash码
	 * 第一次遍历字符串，把对应hashcode的索引处加一
	 * 第二次遍历字符串，把第一个出现字符的hash表中等于1的return索引求解
	 */
	public int FirstNotRepeatingChar(String str) {
		if(str==null||str.length()==0)
			return -1;
		int[] hash=new int[256];
		for(int i=0;i<str.length();i++){//在hash表中递增
			hash[str.charAt(i)]++;
		}
		for(int i=0;i<str.length();i++){//第二次遍历字符串，返回第一个为1的字符索引
			if(hash[str.charAt(i)]==1)
				return i;
		}
		return -1;//若不存在则输出-1
    }
}