//num1,num2分别为长度为1的数组。传出参数
//将num1[0],num2[0]设置为返回结果
public class Solution {
    /*
    题目描述：一个整型数组里除了两个数字之外，其他的数字都出现了两次。
    请写程序找出这两个只出现一次的数字。
    思路：解法与剑指offer不同，先对数组进行排序，然后思路是若数组中一个数字与前后数字都不同，则该数字就是出现一次
    边界条件是最前和最后索引，并使用count计数表示当前寻找第几个只出现一次的数字
    先处理最前数字，若不同则赋值num1[0],count++,如果到达数组最后了则直接赋值num2[0]即可
     */
    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        java.util.Arrays.sort(array);
        int count=0;
        if(array[0]!=array[1]) {//先处理索引0的数字
            num1[0] = array[0];
            count=1;
        }
        for(int i=1;i<array.length;i++){
            if(i==array.length-1&&count<2||array[i-1]!=array[i]&&array[i]!=array[i+1]){
                if(count==0) {
                    num1[0]=array[i];
                    count++;
                }else if(count==1){//如果找到第二个直接break
                    num2[0]=array[i];
                    count++;
                    break;
                }
            }
        }
    }

}
