public class Solution {
	/*
	 * ��Ŀ��������������һ�����ֳ��ֵĴ����������鳤�ȵ�һ�룬���ҳ�������֡�
	 * ��������һ������Ϊ9������{1,2,3,2,2,2,5,4,2}����������2�������г�����5�Σ�
	 * �������鳤�ȵ�һ�룬������2����������������0��
	 * ˼·:��������ָoffer���г��룬�������������ܴ��ڳ��ִ����������ܳ���һ�����
	 * ԭ����˼·����һ����ʱnumber��count��¼��ǰ���������֣������һ�����͵�ǰnumberһ������count++��
	 * ����count--,���count=0��������ָ�ֵ��number��count=1����������֮�󳬹�һ������ֿ϶��ᱻ��¼
	 * ���������û�����������֣������Ȱ����������������һ����ͬ�������¼���ĳ��ȣ������ȴ������鳤��һ�룬��break
	 * Ȼ���ж�count�Ƿ��������һ��ȥ������
	 */
	public int MoreThanHalfNum_Solution(int [] array) {
		int number=Integer.MIN_VALUE,count=0;
		Arrays.sort(array);
		for(int i=0;i<array.length;i++){
			if(number!=array[i]){
				number=array[i];
				count=1;
			}else if(number==array[i]){
				count++;
			}
			if(count>array.length/2)
				break;
		}
		return count>array.length/2?number:0;
	}
}