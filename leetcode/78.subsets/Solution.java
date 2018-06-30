public class Solution {
	/*
	 * 分为两步：1.给出子集的长度 2.在规定子集长度里使用回溯去求得所有子集
	 */
	public List<List<Integer>> subsets(int[] nums) {
		if(nums==null)
			return null;
		List<List<Integer>> g_result=new ArrayList<>();
		for(int i=0;i<=nums.length;i++){//给出所有子集长度
			List<Integer> result=new ArrayList<>();
			k_gram(g_result,result,nums,i,0);
			result.clear();
		}
		return g_result;
	}
	/*
	 * 给定规定子集长度进行回溯求解
	 */
	public void k_gram(List<List<Integer>> g_result,List<Integer> result,int[] num,int k,int index){
		if(result.size()==k){//如果传递进来的list长度等于子集长度，则添加该子集
			List<Integer> result_t=new ArrayList<>(result);
			g_result.add(result_t);
			return;
		}
		for(int i=index;i<num.length;i++){
			result.add(num[i]);
			k_gram(g_result,result,num,k,i+1);
			result.remove(result.size()-1);//回溯
		}
	}
}