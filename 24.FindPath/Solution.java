public class Solution {
	/*
	 * 题目描述:输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
	 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
	 * 思路:递归遍历树，每次存储路径和当前节点的路径总和值，若当前节点是叶子节点且路径和与target一致，加入集合中
	 */
	public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
		ArrayList<ArrayList<Integer>> result=new ArrayList<>();
		ArrayList<Integer> path=new ArrayList<>();
		int sum=0;
		search(root,target,path,sum,result);
		return result;
	}
	public void search(TreeNode root,int target,ArrayList<Integer> path,int sum,ArrayList<ArrayList<Integer>> result){
		if(root==null)//若root为空，则返回
			return ;
		path.add(root.val);//注意return语句必须在上，若放在判断叶子节点条件下，不太好处理
		sum+=root.val;
		if(root.left==null&&root.right==null&&sum==target){
			ArrayList<Integer> tmp=new ArrayList<>(path);
			result.add(tmp);
		}
		search(root.left, target, path, sum, result);//搜索当前节点的 左子树
		search(root.right,target,path,sum,result);//搜索当前节点的右子树
		path.remove(path.size()-1);//每次返回至上层调用时，把当前的节点从路径除去，sum也要减掉相应的值
		sum-=root.val;
	}
}