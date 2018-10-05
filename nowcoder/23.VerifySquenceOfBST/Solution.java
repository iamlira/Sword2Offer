public class Solution {
	/*
	 * 题目描述:输入一个序列，判断是否为一颗二叉树的后序遍历
	 * 思路:后序遍历中，序列的最后一个值必然是树的根节点，左子树都比根节点小，右子树都比根节点大
	 * 所以在根节点之前的序列中找到左右子树的分界点，且右子树的结束节点必然等于根节点的索引减1
	 * 采用递归方式，若传入的左右子树的索引相等，则是叶子节点，返回true
	 * 若找到的右子树的结束节点小于根节点的索引减1，则返回false
	 * 注意细节即可
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
	public boolean Verify(int[] sequence,int start,int end){//end是根节点的索引
		if(start==end)
			return true;
		int root_index=end,left_s=start,left_e=start,right_s=start,right_e=start;
		while(left_e<end&&sequence[left_e]<sequence[root_index]){//找到左子树的结束索引
			if(sequence[left_e+1]<sequence[root_index])
				left_e++;
			else
				break;
		}
		for(right_s=left_e+1,right_e=right_s;sequence[right_s]>sequence[root_index]&&right_e<sequence.length;){
			if(sequence[right_e+1]>sequence[root_index])//找到右子树结束索引
				right_e++;
			else
				break;
		}
		//判断右子树结束索引是否符合条件
		return right_e<root_index-1?false:Verify(sequence,left_s,left_e)&&Verify(sequence,right_s,right_e);
	}
}