package com;

public class No101 {
    public boolean isSymmetric(TreeNode root) {
        return isSymmetricSub(root.left, root.right);
    }

    public boolean isSymmetricSub(TreeNode left, TreeNode right){
        if(left == null && right == null){
            return true;
        }
        if((left == null && right != null) || (left != null && right == null)){
            return false;
        }
        return left.val == right.val && isSymmetricSub(left.right, right.left)
                && isSymmetricSub(left.left, right.right);
    }

    public static void main(String[] args) {
        No101 no101 = new No101();
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(4);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(2, node2, node1);
        TreeNode node6 = new TreeNode(2, node4, node3);
        TreeNode node7 = new TreeNode(1, node5, node6);
        System.out.println(null == null);
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
