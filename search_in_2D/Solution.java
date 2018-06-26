public class Solution {
    public boolean Find(int target, int [][] array) {
		boolean result=false;
		if(array.length==0||array[0].length==0){
			return result;
		}
		int i=0,j=array[0].length-1;
		while(i>=0&&i<array.length&&j<array[0].length&&j>=0){
			if(array[i][j]>target){
				j--;
			}else if(array[i][j]<target){
				i++;
			}else if(target==array[i][j]){
				return true;
			}
		}
		return result;
    }
}