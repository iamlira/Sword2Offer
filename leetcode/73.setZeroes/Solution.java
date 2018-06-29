public class Solution {
	/*
	 * ����ʹ�ó����ĸ����ؼ���
	 * ��һ�α��������ĳ���ط���0����ô���ǽ������������0�����϶���0��
	 * �ڶ��α�����һ��λ�ã���������λ�õ����϶˺�������Ƿ�ͬʱΪ0��ȷ�����λ���Ƿ�Ϊ��0
	 * ����Ҫ��������������¼����˺����϶˵�0�����Ǻ����õ� ��������ԭ�����е�
	 * ��ԭ�����У�����һ������Ϊtrue������������������ȷ����0�к͵�0���Ƿ�ȫ����Ϊ0
	 */
	public void setZeroes(int[][] matrix) {
		if(matrix==null||matrix.length==0||matrix[0].length==0)
			return ;
		boolean row2zero=false,col2zero=false;
		for(int i=0;i<matrix.length;i++)
			if(matrix[i][0]==0){
				col2zero=true;
				break;
			}
		for(int i=0;i<matrix[0].length;i++)
			if(matrix[0][i]==0){
				row2zero=true;
				break;
			}
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				if(matrix[i][j]==0){
					matrix[0][j]=0;
					matrix[i][0]=0;
				}
			}
		}
		for(int i=1;i<matrix.length;i++){//ע�⣬�����Ǵ�1,1��ʼ��0,0����Ҫ������д�����Ȼ���ܻ�ȫ����0
			for(int j=1;j<matrix[0].length;j++){
				if(matrix[i][0]==0||matrix[0][j]==0)
					matrix[i][j]=0;
			}
		}
		if(row2zero)
			for(int i=0;i<matrix[0].length;i++)
				matrix[0][i]=0;
		if(col2zero)
			for(int i=0;i<matrix.length;i++)
				matrix[i][0]=0;
	}
}