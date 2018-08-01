/*
输入例子1:

2
19:90:23
23:59:59


输出例子1:

19:00:23
23:59:59
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n =in.nextInt();
        in.nextLine();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(in.nextLine());
        }
 
        for (String s:list) {
            StringBuilder stringBuilder = new StringBuilder(s);
            if (stringBuilder.charAt(0)>'2'){
                stringBuilder.replace(0,1,"0");
            }else {
                if (stringBuilder.charAt(0)=='2'&&stringBuilder.charAt(1)>='4'){
                    stringBuilder.replace(0,1,"0");
                }
            }
            if (stringBuilder.charAt(3)>'5'){
                stringBuilder.replace(3,4,"0");
            }
            if (stringBuilder.charAt(6)>'5'){
                stringBuilder.replace(6,7,"0");
            }
 
            System.out.println(stringBuilder.toString());
        }
    }
}
