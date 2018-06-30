public class Solution {
	/*
	 * 每个节点的左右子树交换和他的左右子树的左右子树交换是一样的操作
	 * 所以递归即可
	 */
	public void Mirror(TreeNode root) {
		if(root==null)
			return;
		TreeNode tmp;
		tmp=root.left;
		root.left=root.right;
		root.right=tmp;
		Mirror(root.left);
		Mirror(root.right);
	}
}