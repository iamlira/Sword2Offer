public class Solution {
	/*使用递归
	 * pre数组中第一个数即为根节点，在in数组中找到这个数，则左边的都是该节点的左子树，右边的都是该节点的右子树
	 * 每次递归只需要找到左子树和右子树在in中的起始索引和终止索引，以及找到pre中的下一个根节点
	 */
	public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
		TreeNode result=rebuild(pre,in,0,in.length,0);
		return result;
	}
	public TreeNode rebuild(int[] pre,int [] in,int s,int e,int cur){//读入两个数组，s和e分别代表子树在in中起始与终止索引，cur代表pre中当前根节点
		if(s==e)//若s与e相等则说明上次传递进来的是叶子节点
			return null;
		TreeNode root=new TreeNode(pre[cur]);
		for(int i=s;i<e;i++){
			if(pre[cur]==in[i]){
				root.left=rebuild(pre,in,s,i,cur+1);//分割出in中的左子树，由于pre是先序，pre[cur+1]是下一个左子树根节点
				root.right=rebuild(pre,in,i+1,e,cur+(i-s+1));//分割出in中的右子树，由于pre是先序，下一个右子树节点在pre中是cur加上左子树在in中的长度，故cur+(i-s+1)
			}
		}
		return root;
	}
}