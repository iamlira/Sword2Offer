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
