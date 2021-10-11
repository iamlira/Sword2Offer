class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if(matrix.length == 0){
            return false;
        }
        int height = 0;
        int len = matrix[0].length - 1;
        for(int i = len; height < matrix.length && i >= 0;){
            if(matrix[height][i] == target){
                return true;
            }
            if(matrix[height][i] > target){
                i--;
                continue;
            }
            if(matrix[height][i] < target){
                height++;
                continue;
            }
        }
        return false;
    }
}
