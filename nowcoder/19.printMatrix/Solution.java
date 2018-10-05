public class Solution {
	/*
	 * ��Ŀ����:˳ʱ���ӡһ����ά����
	 * ����˼·���ѣ��ѵ���ʵ��ϸ��
	 * ��step������ʾ��ǰ��ӡ��Ȧ�������һȦ������Ȧ���ڶ�Ȧ�Ǵ���Ȧ...���ѭ��
	 * ѭ��������step*2<(m<n?m:n)
	 * ��������ż�����ȵľ��Σ����ϲ��������⣬
	 * �������Ϊ����ڲ�ֻʣ��һ�У�����һ�У�����������󳤿�������һ��������
	 * ������ÿ�δ������Ҵ�ӡһ�У��������´�ӡһ��ʱ�����ж�if(step+1>(m<n?m:n)/2),���ǣ���break
	 * �����ǣ��������ӡ
	 */
	public ArrayList<Integer> printMatrix(int [][] matrix) {
		ArrayList<Integer> result=new ArrayList<>();
		if(matrix.length==0||matrix[0].length==0)
			return result;
		int m=matrix.length,n=matrix[0].length,step=0;
		while(step*2<(m<n?m:n)){
			int start_i=step,start_j=step;//ÿ�ο�ʼ��������[step][step]
			for(int j=start_j;j<n-step;j++)//ÿ�λ�Ȧ��ӡע����������
				result.add(matrix[start_i][j]);
			for(int i=start_i+1;i<m-step;i++)
				result.add(matrix[i][n-step-1]);
			if(step+1>(m<n?m:n)/2)
				break;
			for(int j=n-1-step-1;j>=step;j--)
				result.add(matrix[m-1-step][j]);
			for(int i=m-1-step-1;i>step;i--)
				result.add(matrix[i][step]);
			step++;
		}
		return result;
	}
}