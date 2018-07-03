public class Solution {
	/*
	 * 题目描述:从上到下一层一层的遍历树
	 * 思路:使用队列，每次遍历一个点就把根节点加入队列末，然后判断左右子树是否为空，不为空加入队列
	 */
	public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
		ArrayList<Integer> result=new ArrayList<>();
		if(root==null)
			return result;
		Queue<TreeNode> tmp=new LinkedList<>();
		tmp.offer(root);
		while(!tmp.isEmpty()){
			TreeNode node=tmp.poll();
			result.add(node.val);
			if(node.left!=null)
				tmp.offer(node.left);
			if(node.right!=null)
				tmp.offer(node.right);
		}
		return result;
	}
}