package com;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    /*
    题目描述：航天飞行器是一项复杂而又精密的仪器，飞行器的损耗主要集中在发射和降落的过程，
    科学家根据实验数据估计，如果在发射过程中，产生了 x 程度的损耗，
    那么在降落的过程中就会产生 x2 程度的损耗，如果飞船的总损耗超过了它的耐久度，
    飞行器就会爆炸坠毁。问一艘耐久度为 h 的飞行器，假设在飞行过程中不产生损耗，
    那么为了保证其可以安全的到达目的地，只考虑整数解，至多发射过程中可以承受多少程度的损耗？
    输入描述:

    每个输入包含一个测试用例。每个测试用例包含一行一个整数 h （1 <= h <= 10^18）。

    输出描述:

    输出一行一个整数表示结果。

    示例1
    输入

    10

    输出

    2

    思路：本题模型是一个一元二次方程，使用一元二次方程求根公式即可
    注意的是本题输入的范围非常大，所以使用long存储输入
    每次求完解需要向下求整，这里使用DecimalFormat转换double和int

     */
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        long h=scan.nextLong();
        double res=Math.floor((Math.sqrt(1+4*h)-1)/2);
        DecimalFormat df = new DecimalFormat("0");
        System.out.println(df.format(res));
    }

}