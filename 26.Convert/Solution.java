public class Solution {
	/*
	 * 题目描述:输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
	 * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
	 * 思路:参考剑指offer，因为二叉树左子树的数都比根节点小，右子树都比根节点大，所以要组成排序的链表，
	 * 必须要做中序遍历，根节点的left需要指向左子树的最大值，right需要指向右子树的最小值，所以是个递归过程。
	 * 每次返回时，左子树都需要是一个已排序的链表，做好链接后，当前节点是链表的最后一个，与右子树做递归链接。
	 */
	public TreeNode Convert(TreeNode pRootOfTree) {
		TreeNode[] NodeInList=new TreeNode[1];//做一个TreeNode数组，在传递时可以更改引用，如果只用TreeNode，发生值传递，不会改变Node引用，将永远是null
		NodeInList[0]=null;//赋值null
		Convert(pRootOfTree,NodeInList);
		while(NodeInList[0]!=null&&NodeInList[0].left!=null){
			NodeInList[0]=NodeInList[0].left;
		}
		return NodeInList[0];
	}
	public void Convert(TreeNode pRootOfTree,TreeNode[] LastNodeInList){
		if(pRootOfTree==null)
			return ;
		if(pRootOfTree.left!=null)//中序遍历首先遍历左子树
			Convert(pRootOfTree.left,LastNodeInList);
		pRootOfTree.left=LastNodeInList[0];//把链表末节点赋值给当前节点的left
		if(LastNodeInList[0]!=null)
			LastNodeInList[0].right=pRootOfTree;//把当前节点赋值给链表末节点的right
		LastNodeInList[0]=pRootOfTree;//当前节点作为链表末节点
		if(pRootOfTree.right!=null)
			Convert(pRootOfTree.right,LastNodeInList);//遍历右子树，并传递末节点
	}
}