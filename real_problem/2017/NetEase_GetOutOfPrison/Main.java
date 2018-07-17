package com;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    /*
    题目描述：给定一个 n 行 m 列的地牢，其中 '.' 表示可以通行的位置，
    'X' 表示不可通行的障碍，牛牛从 (x0 , y0 ) 位置出发，遍历这个地牢，
    和一般的游戏所不同的是，他每一步只能按照一些指定的步长遍历地牢，
    要求每一步都不可以超过地牢的边界，也不能到达障碍上。
    地牢的出口可能在任意某个可以通行的位置上。牛牛想知道最坏情况下，
    他需要多少步才可以离开这个地牢。

    输入描述:

    每个输入包含 1 个测试用例。每个测试用例的第一行包含两个整数 n 和 m（1 <= n, m <= 50），
    表示地牢的长和宽。接下来的 n 行，每行 m 个字符，描述地牢，地牢将至少包含两个 '.'。
    接下来的一行，包含两个整数 x0, y0，
    表示牛牛的出发位置（0 <= x0 < n, 0 <= y0 < m，左上角的坐标为 （0, 0），
    出发位置一定是 '.'）。之后的一行包含一个整数 k（0 < k <= 50）表示牛牛合法的步长数，
    接下来的 k 行，每行两个整数 dx, dy 表示每次可选择移动的行和列步长
    （-50 <= dx, dy <= 50）

    输出描述:

    输出一行一个数字表示最坏情况下需要多少次移动可以离开地牢，
    如果永远无法离开，输出 -1。以下测试用例中，牛牛可以上下左右移动，
    在所有可通行的位置.上，地牢出口如果被设置在右下角，牛牛想离开需要移动的次数最多，为3次。

    题意 - -。。。题目没有解释清楚：
        首先需要明确 题目中说“每一步只能按照一些指定的步长遍历地牢”，
        是指主人公行走的方式可以从给定的几种方式中任意选取一种，
        而且相同的行走方式可以被重复选取。

        其次 题目中说“最坏情况下，他需要多少步才可以离开这个地牢。” 是指，
        在给定地牢的形状后，出口允许设置在任意一个“.”所在的位置，
        主人公在知道了出口点后，会试图以最短的路径走向出口，现在需要求，
        如果把出口设在了一个最难以到达的位置， 需要多少步才能到达出口点。

     思路：
     将每个点封装成Point类，类内有xy位置，当前到达布数step，是否访问，是否可达
     然后得到图之后，用queue做广度优先遍历，每次遍历的时候check一下点是否合理
     在图上的移动则是需要根据输入所给的移动类型进行移动的
     */
    static class Point{
        int x,y;
        int step;
        boolean visited=false;
        boolean reachable=false;
        public Point(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
    public static boolean check(Point[][] p,int x,int y,int width,int height){
        return x>=0&&x<width&&y>=0&&y<height&&!p[x][y].visited&&p[x][y].reachable;
    }
    public static void main(String[] args){
        int width,height;
        int start_x,start_y;
        int stepTypes;
        int[] dx,dy;
        Point[][] map;
        //输入部分处理  开始
        Scanner scan=new Scanner(System.in);
        while(scan.hasNext()){
            width=scan.nextInt();
            height=scan.nextInt();
            map = new Point[width][height];
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
                    map[i][j]=new Point(i,j);
                }
            }
            for(int i=0;i<width;i++){
                String line=scan.next();
                line.replace(" ","");
                for(int j=0;j<height;j++){
                    if (line.charAt(j)=='.')
                        map[i][j].reachable=true;
                    else
                        map[i][j].reachable=false;

                }
            }
            start_x=scan.nextInt();start_y=scan.nextInt();
            map[start_x][start_y].visited=true;
            stepTypes=scan.nextInt();
            dx=new int[stepTypes];dy=new int[stepTypes];
            for(int i=0;i<stepTypes;i++){
                dx[i]=scan.nextInt();
                dy[i]=scan.nextInt();
            }
            //输入部分处理  结束

            //bfs部分 开始
            Queue<Point> queue=new LinkedList<>();
            queue.add(map[start_x][start_y]);
            int maxStep=0;


            while(!queue.isEmpty()){
                Point p=queue.poll();
                maxStep=Math.max(maxStep,p.step);//？？
                for(int i=0;i<stepTypes;i++){
                    int x=p.x+dx[i];
                    int y=p.y+dy[i];
                    if(check(map,x,y,width,height)){
                        map[x][y].visited=true;
                        map[x][y].step=maxStep+1;
                        queue.add(map[x][y]);
                    }
                }
            }
            //bfs部分  结束
            maxStep=0;
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
                    if(map[i][j].reachable==true&&map[i][j].visited!=true) {
                        System.out.println(-1);
                        return;
                    }
                    maxStep=maxStep>map[i][j].step?maxStep:map[i][j].step;
                }
            }
            System.out.println(maxStep);

        }
    }
}
