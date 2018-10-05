public class Solution {
	/*
	 * �����Ϊ�������裬���������Ϊ�ݹ����
	 * 1.root1ÿ���ڵ㶼������Ϊһ��������root2���жԱȣ�root1ÿ���¸��ڵ��������Ϊ���ڵ���бȽϣ����Կ������ݹ�
	 * 2.���������ĸ��ڵ㿪ʼ�Ƚ��Ƿ�һ�£���һ��������Ƚ����ǵ����������Ƿ�һ�£�����ҲΪ�ݹ������ע����ֹ��������
	 */
	public boolean HasSubtree(TreeNode root1,TreeNode root2) {
		boolean result=false;
		if(root1==null||root2==null)//������������һ��Ϊ�ն�����false
			return result;
		result=CheckTree(root1, root2);//��������ǰ���ڵ㿪ʼ�ж�
		if(!result)
			result=CheckTree(root1.left, root2);//�����һ���Ƚ�ʧ�ܣ���Ƚ�root1��������
		if(!result)
			result=CheckTree(root1.right, root2);//�����������ʧ�ܣ���Ƚ�root1��������
		return result;
	}
	public boolean CheckTree(TreeNode root1,TreeNode root2){
		if(root1!=null&&root2!=null&&root1.val!=root2.val)
			return false;
		if(root2==null)//�жϵ���Ҷ�ӽڵ㣬����true
			return true;
		if(root1==null)//��root2���գ�root1�գ���false
			return false;
		return CheckTree(root1.left,root2.left)&&CheckTree(root1.right, root2.right);//����������Ҫͬʱ�жϳɹ�
	}
}