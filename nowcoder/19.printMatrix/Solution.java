public class Solution {
	/*
	 * 题目描述:顺时针打印一个二维数组
	 * 本体思路不难，难的是实现细节
	 * 用step变量表示当前打印的圈数，如第一圈是最外圈，第二圈是次外圈...如此循环
	 * 循环条件是step*2<(m<n?m:n)
	 * 若数组是偶数长度的矩形，如上操作无问题，
	 * 特殊情况为最后内部只剩下一行，或者一列，这种情况矩阵长宽至少有一个是奇数
	 * 所以在每次从左向右打印一行，从上向下打印一列时，都判断if(step+1>(m<n?m:n)/2),若是，则break
	 * 若不是，则继续打印
	 */
	public ArrayList<Integer> printMatrix(int [][] matrix) {
		ArrayList<Integer> result=new ArrayList<>();
		if(matrix.length==0||matrix[0].length==0)
			return result;
		int m=matrix.length,n=matrix[0].length,step=0;
		while(step*2<(m<n?m:n)){
			int start_i=step,start_j=step;//每次开始索引都是[step][step]
			for(int j=start_j;j<n-step;j++)//每次画圈打印注意索引即可
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