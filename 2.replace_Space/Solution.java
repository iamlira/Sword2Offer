public class Solution {
	public String replaceSpace(StringBuffer str) {//思路:从尾到头遍历字符串
		int origin_len=str.length(),space_num=0;
		for(int i=0;i<str.length();i++){//首先统计空格个数，重设字符串长度
			if(str.charAt(i)==' ')
				space_num++;
		}
		str.setLength(origin_len+space_num*2);
		for(int p1=origin_len-1,p2=str.length()-1;p1>=0;){//p1指向原字符串尾，p2指向新的字符串尾，即接下来要复制到的索引
			if(str.charAt(p1)==' '){//若当前字符串是空格，则p2位置写成'%20'，然后p1--,p2--
				str.setCharAt(p2, '0');
				p2--;
				str.setCharAt(p2, '2');
				p2--;
				str.setCharAt(p2, '%');
				p2--;
				p1--;
			}else{//若当前字符不是空格，直接将p1复制到p2索引
				str.setCharAt(p2, str.charAt(p1));
				p1--;
				p2--;
			}
		}
		return str.toString();
	}
}