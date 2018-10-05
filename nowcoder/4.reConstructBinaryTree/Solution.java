public class Solution {
	/*ʹ�õݹ�
	 * pre�����е�һ������Ϊ���ڵ㣬��in�������ҵ������������ߵĶ��Ǹýڵ�����������ұߵĶ��Ǹýڵ��������
	 * ÿ�εݹ�ֻ��Ҫ�ҵ�����������������in�е���ʼ��������ֹ�������Լ��ҵ�pre�е���һ�����ڵ�
	 */
	public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
		TreeNode result=rebuild(pre,in,0,in.length,0);
		return result;
	}
	public TreeNode rebuild(int[] pre,int [] in,int s,int e,int cur){//�����������飬s��e�ֱ����������in����ʼ����ֹ������cur����pre�е�ǰ���ڵ�
		if(s==e)//��s��e�����˵���ϴδ��ݽ�������Ҷ�ӽڵ�
			return null;
		TreeNode root=new TreeNode(pre[cur]);
		for(int i=s;i<e;i++){
			if(pre[cur]==in[i]){
				root.left=rebuild(pre,in,s,i,cur+1);//�ָ��in�е�������������pre������pre[cur+1]����һ�����������ڵ�
				root.right=rebuild(pre,in,i+1,e,cur+(i-s+1));//�ָ��in�е�������������pre��������һ���������ڵ���pre����cur������������in�еĳ��ȣ���cur+(i-s+1)
			}
		}
		return root;
	}
}