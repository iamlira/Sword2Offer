package com;

import java.util.*;

public class Main {
    /*
    题目描述:
    小易邀请你玩一个数字游戏，小易给你一系列的整数。你们俩使用这些整数玩游戏。
    每次小易会任意说一个数字出来，
    然后你需要从这一系列数字中选取一部分出来让它们的和等于小易所说的数字。 例
    如： 如果{2,1,2,7}是你有的一系列数，小易说的数字是11.你可以得到方案2+2+7 = 11.
    如果顽皮的小易想坑你，他说的数字是6，那么你没有办法拼凑出和为6 现在小易给你n个数，
    让你找出无法从n个数中选取部分求和的数字中的最小数（从1开始）。
    输入描述:

    输入第一行为数字个数n (n ≤ 20)
    第二行为n个数xi (1 ≤ xi ≤ 100000)

    输出描述:

    输出最小不能由n个数选取求和组成的数

    示例1
    输入

    3
    5 1 2

    输出

    4

    思路：
    先对输入无序的数组进行排序，然后遍历输入数组，用sum表示当前的总和，用result记录sum+1，表示当前无法找到的最小值，
    这里的关键判断为，当前的总和与result不等，并且当前遍历的值大于result，说明正在遍历的数与result不等，
    并且result落在上一个遍历值的总和和当前遍历总和之间，且最小值为上一个遍历总和+1，即result
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int n=scanner.nextInt();
            int[] input=new int[n];
            for(int i=0;i<n;i++){
                input[i]=scanner.nextInt();
            }
            Arrays.sort(input);
            int sum=0,result=sum+1;
            for(int i=0;i<input.length;i++){
                sum+=input[i];
                if(input[i]>result&&sum!=result)
                    break;
                result=sum+1;
            }
            System.out.println(result);
        }

    }


}