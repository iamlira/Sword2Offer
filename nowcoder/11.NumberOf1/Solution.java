public class Solution {
	/*
	 * ���ⲻ�ɽ�����������������⣬��Ϊ�����ڲ����ʾ�����ƻ����1������������ѭ��
	 * ����Ӧ���ǽ�1�����������
	 */
	public int NumberOf1(int n) {
		int count=0,flag=1;
		while(flag!=0){
			if((n&flag)!=0)
				count++;
			flag=flag<<1;
		}
		return count;
    }
}