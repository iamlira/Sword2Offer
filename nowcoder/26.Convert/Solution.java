public class Solution {
	/*
	 * ��Ŀ����:����һ�ö��������������ö���������ת����һ�������˫������
	 * Ҫ���ܴ����κ��µĽ�㣬ֻ�ܵ������н��ָ���ָ��
	 * ˼·:�ο���ָoffer����Ϊ�������������������ȸ��ڵ�С�����������ȸ��ڵ������Ҫ������������
	 * ����Ҫ��������������ڵ��left��Ҫָ�������������ֵ��right��Ҫָ������������Сֵ�������Ǹ��ݹ���̡�
	 * ÿ�η���ʱ������������Ҫ��һ��������������������Ӻ󣬵�ǰ�ڵ�����������һ���������������ݹ����ӡ�
	 */
	public TreeNode Convert(TreeNode pRootOfTree) {
		TreeNode[] NodeInList=new TreeNode[1];//��һ��TreeNode���飬�ڴ���ʱ���Ը������ã����ֻ��TreeNode������ֵ���ݣ�����ı�Node���ã�����Զ��null
		NodeInList[0]=null;//��ֵnull
		Convert(pRootOfTree,NodeInList);
		while(NodeInList[0]!=null&&NodeInList[0].left!=null){
			NodeInList[0]=NodeInList[0].left;
		}
		return NodeInList[0];
	}
	public void Convert(TreeNode pRootOfTree,TreeNode[] LastNodeInList){
		if(pRootOfTree==null)
			return ;
		if(pRootOfTree.left!=null)//����������ȱ���������
			Convert(pRootOfTree.left,LastNodeInList);
		pRootOfTree.left=LastNodeInList[0];//������ĩ�ڵ㸳ֵ����ǰ�ڵ��left
		if(LastNodeInList[0]!=null)
			LastNodeInList[0].right=pRootOfTree;//�ѵ�ǰ�ڵ㸳ֵ������ĩ�ڵ��right
		LastNodeInList[0]=pRootOfTree;//��ǰ�ڵ���Ϊ����ĩ�ڵ�
		if(pRootOfTree.right!=null)
			Convert(pRootOfTree.right,LastNodeInList);//������������������ĩ�ڵ�
	}
}