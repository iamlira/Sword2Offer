public class Solution {
	/*
	 * ��Ŀ����:����һ�Ŷ�������һ����������ӡ���������н��ֵ�ĺ�Ϊ��������������·����
	 * ·������Ϊ�����ĸ���㿪ʼ����һֱ��Ҷ����������Ľ���γ�һ��·����
	 * ˼·:�ݹ��������ÿ�δ洢·���͵�ǰ�ڵ��·���ܺ�ֵ������ǰ�ڵ���Ҷ�ӽڵ���·������targetһ�£����뼯����
	 */
	public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
		ArrayList<ArrayList<Integer>> result=new ArrayList<>();
		ArrayList<Integer> path=new ArrayList<>();
		int sum=0;
		search(root,target,path,sum,result);
		return result;
	}
	public void search(TreeNode root,int target,ArrayList<Integer> path,int sum,ArrayList<ArrayList<Integer>> result){
		if(root==null)//��rootΪ�գ��򷵻�
			return ;
		path.add(root.val);//ע��return���������ϣ��������ж�Ҷ�ӽڵ������£���̫�ô���
		sum+=root.val;
		if(root.left==null&&root.right==null&&sum==target){
			ArrayList<Integer> tmp=new ArrayList<>(path);
			result.add(tmp);
		}
		search(root.left, target, path, sum, result);//������ǰ�ڵ�� ������
		search(root.right,target,path,sum,result);//������ǰ�ڵ��������
		path.remove(path.size()-1);//ÿ�η������ϲ����ʱ���ѵ�ǰ�Ľڵ��·����ȥ��sumҲҪ������Ӧ��ֵ
		sum-=root.val;
	}
}