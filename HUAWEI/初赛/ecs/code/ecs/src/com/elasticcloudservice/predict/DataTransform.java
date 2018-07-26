package com.elasticcloudservice.predict;

/**
 * Created by lira on 2018/3/20.
 */
public class DataTransform {
    int[][] Double2Int(double[][] data){
        int[][] result=new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for(int j=0;j<data[0].length;j++) {
                result[i][j]=(int)Math.round(data[i][j]);
//                String str_double = String.valueOf(data[i][j]);
//                str_double = str_double.substring(0, str_double.indexOf("."));
//                result[i][j] = Integer.parseInt(str_double);
            }
        }
        return result;
    }
    int[] Double2Int(double[] data){
        int[] result=new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i]=(int)Math.round(data[i]);
//            String str_double = String.valueOf(data[i]);
//            str_double = str_double.substring(0, str_double.indexOf("."));
//            result[i] = Integer.parseInt(str_double);
        }
        return result;
    }
    int Double2Int(double data){
        int result;
        String str_double = String.valueOf(data);
        str_double = str_double.substring(0, str_double.indexOf("."));
        result = Integer.parseInt(str_double);
        return result;
    }
}
