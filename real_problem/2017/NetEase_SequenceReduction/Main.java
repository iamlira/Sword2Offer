package com;

import java.util.Scanner;

public class Main {
    /*
    题目描述：牛牛的作业薄上有一个长度为 n 的排列 A，
    这个排列包含了从1到n的n个数，但是因为一些原因，
    其中有一些位置（不超过 10 个）看不清了，但是牛牛记得这个数列顺序对的数量是 k，
    顺序对是指满足 i < j 且 A[i] < A[j] 的对数，请帮助牛牛计算出，
    符合这个要求的合法排列的数目。

    输入描述:

    每个输入包含一个测试用例。每
    个测试用例的第一行包含两个整数 n 和 k（1 <= n <= 100, 0 <= k <= 1000000000），
    接下来的 1 行，包含 n 个数字表示排列 A，其中等于0的项表示看不清的位置（不超过 10 个）。

    输出描述:

    输出一行表示合法的排列数目。

    示例1
    输入

    5 5
    4 0 0 2 0

    输出

    2

    思路：采用递归回溯深搜思路，先得到两个数组，一个是现在的数组array，一个是按给定n的数组elements，但是把能清楚显示的扣掉，赋值成0
    然后回溯每次把elements的非0元素塞到原来数组中，进行计算顺序对，若array塞满，并且顺序对也对上，则答案+1
    其中，原本数组的顺序对不会因为现在加的数字改变，所以加了数字之后的顺序对是原来的顺序对和用现在的数字去计算顺序对的和
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n=scanner.nextInt();
            int k=scanner.nextInt();
            int[] result={0},elements=new int[n+1];
            int[] array=new int[n+1];
            for(int i=1;i<=n;i++){
                array[i]=scanner.nextInt();
                elements[i]=i;
            }
            for(int i=1;i<=n;i++){//把array出现的数字在elements中删除
                elements[array[i]]=0;
            }
            int sum=0,index=1;
            for(int i=1;i<n;i++){//计算现有的顺序对
                for(int j=i+1;j<=n;j++){
                    if(array[j]>array[i]&&array[i]!=0)
                        sum++;
                }
            }
            for(;index<array.length&&array[index]!=0;index++);//找到array中第一个填赛位置
            dfs(array,elements,index,result,sum,k);
            System.out.println(result[0]);

        }
    }
    static void dfs(int[] array,int[] elements,int index,int[] result,int sum,int target){
        if(sum==target&&index==array.length){//如果顺序对和目标值一直且array填到最后，则答案+1
            result[0]++;
            return;
        }
        if(sum>target||index>array.length)//如果超出则剪枝
            return;
        for(int i=1;i<elements.length;i++){
            if(elements[i]!=0){
                array[index]=elements[i];//填赛
                elements[i]=0;//填赛的值赋值0
                int sum2=calc(array,index);//计算现在数字在数组中的顺序对
                int index_dup=index;
                for(;index_dup<array.length&&array[index_dup]!=0;index_dup++);//拿到下个array中的填充索引
                dfs(array,elements,index_dup,result,sum+sum2,target);//dfs递归调用
                elements[i]=array[index];//回溯
                array[index]=0;
            }
        }
    }
    static int calc(int[] array,int index){
        int sum2=0;
        for(int i=1;i<index;i++){
            if(array[i]<array[index])
                sum2++;
        }
        for(int i=index;i<array.length;i++){
            if(array[i]>array[index])
                sum2++;
        }
        return sum2;
    }
}