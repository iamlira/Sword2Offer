public class Solution {
	/*
	 * ��Ŀ��������һ���ַ���(0<=�ַ�������<=10000��ȫ������ĸ���)���ҵ���һ��ֻ����һ�ε��ַ�,
	 * ����������λ��, ���û���򷵻� -1.
	 * ˼·:ͬ���ǿռ任ʱ�����⣬����ʹ��hash��ÿ��char��ASCII���е�����������Ϊ����hash��
	 * ��һ�α����ַ������Ѷ�Ӧhashcode����������һ
	 * �ڶ��α����ַ������ѵ�һ�������ַ���hash���е���1��return�������
	 */
	public int FirstNotRepeatingChar(String str) {
		if(str==null||str.length()==0)
			return -1;
		int[] hash=new int[256];
		for(int i=0;i<str.length();i++){//��hash���е���
			hash[str.charAt(i)]++;
		}
		for(int i=0;i<str.length();i++){//�ڶ��α����ַ��������ص�һ��Ϊ1���ַ�����
			if(hash[str.charAt(i)]==1)
				return i;
		}
		return -1;//�������������-1
    }
}