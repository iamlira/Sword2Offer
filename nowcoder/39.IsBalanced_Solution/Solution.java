class Solution {
    /*
    题目描述：输入一棵二叉树，判断该二叉树是否是平衡二叉树。
    思路：与搜索树的深度的思路一直，使用递归，但是传入一个boolean变量引用，每次递归遍历时
    判断左右子树深度相差是否超过1,如果是，则把变量引用改为false，否则是true
    因为递归肯定会回到根节点，左右子树深度也可以的到，所以必然会得到解
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        boolean[] flag={true};
        search(root,flag);
        return flag[0];
    }
    public int search(TreeNode root,boolean[] flag){
        if(root==null)
            return 0;
        int left=search(root.left,flag);
        int right=search(root.right,flag);
        flag[0]=Math.abs(left-right)>1?false:true;//判断是否是平衡
        return left>right?left+1:right+1;
    }
}
