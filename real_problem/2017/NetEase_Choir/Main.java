package com;

import java.util.*;

public class Main {
    /*
    题目描述
    有 n 个学生站成一排，每个学生有一个能力值，牛牛想从这 n 个学生中按照顺序选取 k 名学生，
    要求相邻两个学生的位置编号的差不超过 d，使得这 k 个学生的能力值的乘积最大，
    你能返回最大的乘积吗？
    输入描述:

    每个输入包含 1 个测试用例。每个测试数据的第一行包含一个整数 n (1 <= n <= 50)，表示学生的个数，接下来的一行，包含 n 个整数，按顺序表示每个学生的能力值 ai（-50 <= ai <= 50）。接下来的一行包含两个整数，k 和 d (1 <= k <= 10, 1 <= d <= 50)。

    输出描述:

    输出一行表示最大的乘积。

    示例1
    输入

    3
    7 4 7
    2 50

    输出

    49

    思路：
    使用动态规划思想，用max[][],和min[][]两个二维数组记录，max记录的是用第i个数作为第j+1个数时
    目前得到的最大乘积，每次填写一个数时，都要在max矩阵中找到在他前d个数字中，
    找到前d个数字作为第j个数字与该数乘积的最大值填写进max[i][j]
    这里由于数字可能是负的，负负得正可能得到更大的数，所以每次找最大值时，还需要和min矩阵相乘找，并且还要填写min矩阵的值
    即转换公式为
    Max[i][j]=max(Max[i][j],max(Max[i][j]*a[i],Min[i][j]*a[i]))
    Min[i][j]=min(Min[i][j],min(Max[i][j]*a[i],Min[i][j]*a[i]))
    最后在Max[][k-1]中遍历得到最大值即可求解
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int t= scanner.nextInt(),k,d;
        long[] team=new long[t];
        for(int i=0;i<t;i++){
            team[i]=scanner.nextInt();
        }
        k=scanner.nextInt();
        d=scanner.nextInt();
        long[][] max=new long[t][k],min=new long[t][k];
        for(int i=0;i<t;i++){
            max[i][0]=team[i];
            min[i][0]=team[i];
        }
        long result=Integer.MIN_VALUE;
        for(int i=0;i<t;i++){
            for(int j=1;j<k;j++){//注意初始和循环条件
                for(int z=i-1;z>=Math.max(0,i-d);z--){
                    max[i][j]=Math.max(max[i][j],max[z][j-1]*team[i]);
                    max[i][j]=Math.max(max[i][j],min[z][j-1]*team[i]);
                    min[i][j]=Math.min(min[i][j],min[z][j-1]*team[i]);
                    min[i][j]=Math.min(min[i][j],max[z][j-1]*team[i]);
                }
            }
            result=Math.max(result,max[i][k-1]);
        }
        System.out.println(result);
    }


}