public class Solution {
	public String replaceSpace(StringBuffer str) {//˼·:��β��ͷ�����ַ���
		int origin_len=str.length(),space_num=0;
		for(int i=0;i<str.length();i++){//����ͳ�ƿո�����������ַ�������
			if(str.charAt(i)==' ')
				space_num++;
		}
		str.setLength(origin_len+space_num*2);
		for(int p1=origin_len-1,p2=str.length()-1;p1>=0;){//p1ָ��ԭ�ַ���β��p2ָ���µ��ַ���β����������Ҫ���Ƶ�������
			if(str.charAt(p1)==' '){//����ǰ�ַ����ǿո���p2λ��д��'%20'��Ȼ��p1--,p2--
				str.setCharAt(p2, '0');
				p2--;
				str.setCharAt(p2, '2');
				p2--;
				str.setCharAt(p2, '%');
				p2--;
				p1--;
			}else{//����ǰ�ַ����ǿո�ֱ�ӽ�p1���Ƶ�p2����
				str.setCharAt(p2, str.charAt(p1));
				p1--;
				p2--;
			}
		}
		return str.toString();
	}
}