public class Solution {
	/*
	 * ��Ŀ����������n���������ҳ�������С��K������
	 * ��������4,5,1,6,2,7,3,8��8�����֣�����С��4��������1,2,3,4,��
	 * ˼·��ʹ�ø���k��С�Ŀռ�ȥ��¼ÿ�ε���С��k������ÿ�αȸ����ռ�������ֵҪСʱ��ȥ�������ռ��е����ֵ���������ֵ
	 * ������input���ɵõ���
	 * ��ָoffer��ʹ�õ�stl�е����ṹ������������飬ÿ�μ����ʹ�ÿ��Ž����������︨���������һ�����־������ֵ
	 */
	public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
		ArrayList<Integer> result=new ArrayList<>();
		if(input.length<k||k==0)//����������ܿӵ���������
			return result;
		int[] tmp=new int[k];
		for(int i=0;i<k;i++){//��ֵ���ֵ
			tmp[i]=Integer.MAX_VALUE;
		}
		for(int i=0;i<input.length;i++){//ÿ�α��������ֱ�k�����ռ�����ҪС���Ͱ����ĸ�ֵ�ɵ�ǰ����
			if(input[i]<tmp[k-1]){
				tmp[k-1]=input[i];
			    java.util.Arrays.sort(tmp);//Ȼ��������ȷ��ÿ�μ�������ռ����һ�����������
			}
		}
		for(int i=0;i<k;i++)
			result.add(tmp[i]);
		return result;
	}
}