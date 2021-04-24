/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        travel(root, result);
        return result;
    }

    public void travel(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        travel(node.left, list);
        list.add(node.val);
        travel(node.right, list);
    }
}
