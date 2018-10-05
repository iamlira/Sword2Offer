public class Solution {
	/*
	 * ��Ŀ����:����һ�����У��ж��Ƿ�Ϊһ�Ŷ������ĺ������
	 * ˼·:��������У����е����һ��ֵ��Ȼ�����ĸ��ڵ㣬���������ȸ��ڵ�С�����������ȸ��ڵ��
	 * �����ڸ��ڵ�֮ǰ���������ҵ����������ķֽ�㣬���������Ľ����ڵ��Ȼ���ڸ��ڵ��������1
	 * ���õݹ鷽ʽ�������������������������ȣ�����Ҷ�ӽڵ㣬����true
	 * ���ҵ����������Ľ����ڵ�С�ڸ��ڵ��������1���򷵻�false
	 * ע��ϸ�ڼ���
	 */
	public boolean VerifySquenceOfBST(int [] sequence) {
		if(sequence==null||sequence.length==0)
			return false;
		return Verify(sequence, 0, sequence.length-1);
//		int root_index=sequence.length-1,left_s=0,left_e=0,right_s=0,right_e=0;
//		while(left_e<sequence.length&&sequence[left_e]<sequence[root_index]){
//			if(sequence[left_e+1]<sequence[root_index])
//				left_e++;
//			else
//				break;
//		}
//		for(right_s=left_e+1,right_e=right_s;right_e<sequence.length&&sequence[right_s]>sequence[root_index];){
//			if(sequence[right_e+1]>sequence[root_index])
//				right_e++;
//			else
//				break;
//		}
//		return right_e<root_index-1?false:Verify(sequence,left_s,left_e)&&Verify(sequence,right_s,right_e);
	}
	public boolean Verify(int[] sequence,int start,int end){//end�Ǹ��ڵ������
		if(start==end)
			return true;
		int root_index=end,left_s=start,left_e=start,right_s=start,right_e=start;
		while(left_e<end&&sequence[left_e]<sequence[root_index]){//�ҵ��������Ľ�������
			if(sequence[left_e+1]<sequence[root_index])
				left_e++;
			else
				break;
		}
		for(right_s=left_e+1,right_e=right_s;sequence[right_s]>sequence[root_index]&&right_e<sequence.length;){
			if(sequence[right_e+1]>sequence[root_index])//�ҵ���������������
				right_e++;
			else
				break;
		}
		//�ж����������������Ƿ��������
		return right_e<root_index-1?false:Verify(sequence,left_s,left_e)&&Verify(sequence,right_s,right_e);
	}
}