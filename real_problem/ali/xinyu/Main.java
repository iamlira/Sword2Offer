package com;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int[][] map = new int[n][n];
            int[][] last_path=new int[n][n],dp=new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    map[i][j] = scanner.nextInt();
                }
            }
            for(int i=0;i<map.length;i++){
                last_path[i]=map[i].clone();
                dp[i]=map[i].clone();
            }
            for(int k=0;k<m-1;k++){
                for(int i=0;i<n;i++){
                    for(int j=0;j<n;j++){
                        int min=Integer.MAX_VALUE;
                        int[] tmp=new int[n];
                        for(int l=0;l<n;l++){
                            if(l!=i&&l!=j) {
                                min=min<last_path[i][l]+map[l][j]?min:last_path[i][l]+map[l][j];
                            }
                        }
                        dp[i][j]=min;
                    }
                }
                for(int i=0;i<n;i++){
                    last_path[i]=dp[i].clone();
                }
            }
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                    System.out.println(dp[i][j]);
        }
    }

}