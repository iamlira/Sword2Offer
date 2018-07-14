public class Solution {
    /*
    题目表述：输入一棵二叉树，求该树的深度。
    从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。
    思路：递归遍历即可。每次调用时检查根节点是否为空，为空则返回深度为0,否则每次遍历左右子树深度
    返回较大值，然后加1,加1是因为遍历该节点，所以加1
     */
    public int TreeDepth(TreeNode root) {
        return search(root);
    }
    public int search(TreeNode root){
        if(root==null)
            return 0;
        int left=search(root.left);
        int right=search(root.right);
        return left>right?left+1:right+1;//返回较大值+1
    }

}
