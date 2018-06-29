public class Solution {
	/*
	 * 可以使用常量的辅助控件，
	 * 第一次遍历，如果某个地方是0，那么我们将该行最左端置0，最上端置0，
	 * 第二次遍历到一个位置，根据这两位置的最上端和最左端是否同时为0来确定这个位置是否为置0
	 * 还需要两个布尔量来记录最左端和最上端的0是我们后来置的 还是数组原本就有的
	 * 若原本就有，则这一变量置为true，最后根据这两个变量确定第0行和第0列是否全部置为0
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
		for(int i=1;i<matrix.length;i++){//注意，索引是从1,1开始，0,0是需要最后行列处理，不然可能会全部置0
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