public class Solution {
	/*
	 * ��Ŀ����������һ���������飬
	 * ʵ��һ�����������������������ֵ�˳��
	 * ʹ�����е�����λ�������ǰ�벿�֣�
	 * ���е�ż��λ��λ������ĺ�벿�֣�
	 * ����֤������������ż����ż��֮������λ�ò��䡣
	 * 
	 * ������Ŀ��Ҫ�����λ�ò��䣬���Բ���ʹ������ָ��ָ��������������ҽ����ƶ�����
	 * ����ʹ����������ֱ��¼��������ż����Ȼ���ٱ�����������д��ԭ����
	 */
	public void reOrderArray(int [] array) {
		int[] odd=new int[array.length],even=new int[array.length];
		int odd_index=0,even_index=0;
		for(int i=0;i<array.length;i++){
			if(array[i]%2==1)
				odd[odd_index++]=array[i];
			else
				even[even_index++]=array[i];
		}
		for(int i=0;i<odd_index;i++){
			array[i]=odd[i];
		}
		for(int i=odd_index,j=0;i<array.length;i++,j++){
			array[i]=even[j];
		}
	}
}