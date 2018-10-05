public class Solution {
	/*
	 * ʹ�ö��ַ���s��e��������������mid�����м�����
	 */
	public int minNumberInRotateArray(int[] array) {
		int result = Integer.MAX_VALUE;
		if (array.length == 0)
			return 0;
		int s=0,e=array.length-1,mid=(s+e)/2;
		while(array[s]>=array[e]){//��ת������ֲ���ʱ������߱��ұߴ������ѭ��
			if(e-s<=1){//��s����e�ұߣ���array[s]>=array[e]����e����λ��Ϊ��Сֵ���˳�ѭ��
				mid=e;
				break;
			}
			if(array[s]==array[e]&&array[mid]==array[e]){//�������������{1,1,1,0,1}��������һ�·�֧
				while(array[s]==array[e]){
					s++;
					mid=(s+e)/2;
					if(array[mid]<result)
						result=array[mid];
				}
				return result;
			}else if(array[mid]>=array[s]){//��mid����ֵ��s����ֵ������Сֵ��mid��e�м�
				s=mid;
			}else if(array[mid]<=array[e]){//��mid����ֵ��e����ֵС������Сֵ��mid��s�м�
				e=mid;
				if(array[mid]<result)
					result=array[mid];
			}
			mid=(s+e)/2;
				
		}
		return array[mid];
	}
}