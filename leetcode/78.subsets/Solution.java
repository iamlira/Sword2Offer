public class Solution {
	/*
	 * ��Ϊ������1.�����Ӽ��ĳ��� 2.�ڹ涨�Ӽ�������ʹ�û���ȥ��������Ӽ�
	 */
	public List<List<Integer>> subsets(int[] nums) {
		if(nums==null)
			return null;
		List<List<Integer>> g_result=new ArrayList<>();
		for(int i=0;i<=nums.length;i++){//���������Ӽ�����
			List<Integer> result=new ArrayList<>();
			k_gram(g_result,result,nums,i,0);
			result.clear();
		}
		return g_result;
	}
	/*
	 * �����涨�Ӽ����Ƚ��л������
	 */
	public void k_gram(List<List<Integer>> g_result,List<Integer> result,int[] num,int k,int index){
		if(result.size()==k){//������ݽ�����list���ȵ����Ӽ����ȣ�����Ӹ��Ӽ�
			List<Integer> result_t=new ArrayList<>(result);
			g_result.add(result_t);
			return;
		}
		for(int i=index;i<num.length;i++){
			result.add(num[i]);
			k_gram(g_result,result,num,k,i+1);
			result.remove(result.size()-1);//����
		}
	}
}