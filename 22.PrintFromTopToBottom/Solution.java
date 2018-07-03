public class Solution {
	/*
	 * ��Ŀ����:���ϵ���һ��һ��ı�����
	 * ˼·:ʹ�ö��У�ÿ�α���һ����ͰѸ��ڵ�������ĩ��Ȼ���ж����������Ƿ�Ϊ�գ���Ϊ�ռ������
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