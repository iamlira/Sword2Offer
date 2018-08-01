/*
 字符迷阵是一种经典的智力游戏。玩家需要在给定的矩形的字符迷阵中寻找特定的单词。
在这题的规则中，单词是如下规定的：
1. 在字符迷阵中选取一个字符作为单词的开头；
2. 选取右方、下方、或右下45度方向作为单词的延伸方向；
3. 以开头的字符，以选定的延伸方向，把连续得到的若干字符拼接在一起，则称为一个单词。 

输入描述:

输入的第一行为一个正整数T，表示测试数据组数。 接下来有T组数据。每组数据的第一行包括两个整数m和n，表示字符迷阵的行数和列数。接下来有m行，每一行为一个长度为n的字符串，按顺序表示每一行之中的字符。再接下来还有一行包括一个字符串，表示要寻找的单词。  数据范围： 对于所有数据，都满足1<=T<=9，且输入的所有位于字符迷阵和单词中的字符都为大写字母。要寻找的单词最短为2个字符，最长为9个字符。字符迷阵和行列数，最小为1，最多为99。 对于其中50%的数据文件，字符迷阵的行列数更限制为最多为20。



输出描述:

对于每一组数据，输出一行，包含一个整数，为在给定的字符迷阵中找到给定的单词的合法方案数。


输入例子1:

3
10 10
AAAAAADROW
WORDBBBBBB
OCCCWCCCCC
RFFFFOFFFF
DHHHHHRHHH
ZWZVVVVDID
ZOZVXXDKIR
ZRZVXRXKIO
ZDZVOXXKIW
ZZZWXXXKIK
WORD
3 3
AAA
AAA
AAA
AA
5 8
WORDSWOR
ORDSWORD
RDSWORDS
DSWORDSW
SWORDSWO
SWORD


输出例子1:

4
16
5
*/

import java.util.Scanner;
 
public class Main {
 
    private static int find(char[][] chars, char[] target, int x, int y) {
        int sum = 0;
 
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (target[0] != chars[i][j]) continue;
 
                int flag = 1;
                // 右
                for (int k = 1; k < target.length; k++) {
                    if ((j + k) < y && target[k] == chars[i][j + k]) {
                        flag ++;
                    } else break;
                }
                if (flag == target.length) sum ++;
 
                flag = 1;
                // 下
                for (int k = 1; k < target.length; k++) {
                    if ((i + k) < x && target[k] == chars[i + k][j]) {
                        flag ++;
                    } else break;
                }
                if (flag == target.length) sum ++;
 
                flag = 1;
                // 斜
                for (int k = 1; k < target.length; k++) {
                    if (((i + k) < x && (j + k) < y) && target[k] == chars[i + k][j + k]) {
                        flag ++;
                    } else break;
                }
                if (flag == target.length) sum ++;
            }
        }
 
        return sum;
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int loop = scanner.nextInt();
 
        for (int i = 0; i < loop; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            char[][] chars = new char[x][y];
            for (int j = 0; j < x; j++)
                chars[j] = scanner.next().toCharArray();
            char[] target = scanner.next().toCharArray();
            System.out.println(find(chars, target, x, y));
        }
    }
}
