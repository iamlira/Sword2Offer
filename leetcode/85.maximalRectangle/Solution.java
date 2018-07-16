public class Solution {
    /*
    题目描述：https://leetcode.com/problems/largest-rectangle-in-histogram/description/
    思路：此题关键在于找到当前遍历矩形的左右边界，如果用传统做法肯定会超时，参考网上思路
    对于一个升续的数组来说，例如：1,2,3,4,5，能得到最大的矩形面积一定是max(height[i]*(size-i))
    这是因为对于每个矩形来说他的右边界已知，肯定是数组的末尾，左边界也已知，就是他自己，
    所以对于不规则的数组，需要构造出一个升续的数组，而且在构造过程中能够求出左边界，并求出面积
    这里使用栈进行实现，当前遍历的矩阵若大于等于栈顶，则符合升续数组，入栈即可
    若当前遍历的矩阵小于栈顶，说明不是升续的，又从另外一个角度说明当前索引是栈顶元素的右边界，
    此时则弹出栈顶元素，并计算面积：栈顶元素×（当前索引-栈顶元素索引）
    如果当前矩阵一直小于栈顶元素，则重复上述操作，计算结束后把原来的位置用当前值填补！意味着之前的位置当前矩阵都可以拓展到！
    在升续的栈中，左边界是他自己，右边界是遍历到需要进行出栈的矩阵索引，所以可以得到所有矩阵在数组中的面积
     */
    public int largestRectangleArea(int[] heights) {
        int result=0,count=0;
        Stack<Integer> stack=new Stack<>();
        for(int i=0;i<heights.length;i++){
            count=0;//是用count来计算弹出元素数量多少
            if(stack.isEmpty()||heights[i]>=stack.peek()){//如果栈空或者比栈顶元素大则入栈
                stack.push(heights[i]);
            }else if(heights[i]<stack.peek()){
                while(!stack.isEmpty()&&heights[i]<stack.peek()){
                    count++;
                    int tmp=stack.peek();
                    result=tmp*(i-stack.size()+1)>result?tmp*(i-stack.size()+1):result;//计算面积：栈顶元素×（当前索引-栈顶元素索引）
                    stack.pop();
                }
                while(count>0){
                    stack.push(heights[i]);
                    count--;
                }
                stack.push(heights[i]);
            }
        }
        for(int i=stack.size(),size=stack.size();i>0;i--){//计算升续数组的最大值
            result=stack.peek()*(size-i+1)>result?stack.peek()*(size-i+1):result;
            stack.pop();
        }
        return result;
    }
    /*
    题目描述：Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

    Example:

    Input:
    [
        ["1","0","1","0","0"],
        ["1","0","1","1","1"],
        ["1","1","1","1","1"],
        ["1","0","0","1","0"]
    ]
    Output: 6

    思路：每一行竖值方向统计落地的1的连续个数，
    然后又对每一行调用largestRectangleArea函数，是84题的函数，逐行调用到最后一行即可求解
     */
    public int maximalRectangle(char[][] matrix) {
        int result=0;
        if(matrix==null||matrix.length==0||matrix[0].length==0)
            return result;
        int[] histogram=new int[matrix[0].length];
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                histogram[j]=matrix[i][j]=='1'?histogram[j]+1:0;//若是1递增，若是0直接赋值0
            }
            int tmp=largestRectangleArea(histogram);
            result=result>tmp?result:tmp;
        }
        return result;
    }
}
