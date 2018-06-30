public class Solution {
	/*
	 * 本题分为两个步骤，两个步骤均为递归操作
	 * 1.root1每个节点都可以作为一个子树与root2进行对比，root1每到下个节点均可以作为根节点进行比较，所以可以作递归
	 * 2.从两个树的根节点开始比较是否一致，若一致则继续比较他们的左右子树是否一致，所以也为递归操作，注意终止条件即可
	 */
	public boolean HasSubtree(TreeNode root1,TreeNode root2) {
		boolean result=false;
		if(root1==null||root2==null)//若两个树中有一个为空都返回false
			return result;
		result=CheckTree(root1, root2);//从两个当前根节点开始判断
		if(!result)
			result=CheckTree(root1.left, root2);//如果上一步比较失败，则比较root1的左子树
		if(!result)
			result=CheckTree(root1.right, root2);//如果上两步都失败，则比较root1的右子树
		return result;
	}
	public boolean CheckTree(TreeNode root1,TreeNode root2){
		if(root1!=null&&root2!=null&&root1.val!=root2.val)
			return false;
		if(root2==null)//判断到达叶子节点，返回true
			return true;
		if(root1==null)//若root2不空，root1空，则false
			return false;
		return CheckTree(root1.left,root2.left)&&CheckTree(root1.right, root2.right);//两个子树需要同时判断成功
	}
}