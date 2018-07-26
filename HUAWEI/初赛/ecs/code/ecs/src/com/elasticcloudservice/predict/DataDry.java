package com.elasticcloudservice.predict;

import java.util.Arrays;
import java.util.HashMap;

public class DataDry {//去燥类
    public DataDry(){
        init_c_constant();
    }
    public HashMap<Integer,Double> c_constant;
    public int[] Chauvenet(int[] data){
        int[] result=new int[data.length];
        int n=data.length;
        double ave=getAve(data);
        double stdDev=getStdDev(data);
        double Xmin=0,Xmax=0;
        //获取肖维勒系数
        int tmp=n;
        while(c_constant.get(tmp)==null&&tmp>=3){
            tmp--;
        }
        double c_const=c_constant.get(tmp);
        if(data.length < 5)
            System.out.println("数据少于5个，请检查");
        else{
            Xmin = ave - (c_const*stdDev);
            Xmax = ave + (c_const*stdDev);
        }
        for(int i=0;i<data.length;i++){
            if(data[i]>Xmin && data[i]<Xmax){
                result[i]=data[i];
            }else{
                result[i]=(int)ave;
            }
        }

        return result;
    }
    public double[] Chauvenet_double(int[] data){
        double[] result=new double[data.length];
        int n=data.length;
        double ave=getAve(data);
        double stdDev=getStdDev(data);
        double Xmin=0,Xmax=0;
        //获取肖维勒系数
        int tmp=n;
        while(c_constant.get(tmp)==null&&tmp>=3){
            tmp--;
        }
        double c_const=c_constant.get(tmp);
        if(data.length < 5)
            System.out.println("数据少于5个，请检查");
        else{
            Xmin = ave - (c_const*stdDev);
            Xmax = ave + (c_const*stdDev);
        }
        for(int i=0;i<data.length;i++){
            if(data[i]>Xmin && data[i]<Xmax){
                result[i]=data[i];
            }else{
                result[i]=ave;
            }
        }

        return result;
    }
    public int[][] AllChauvenet(int[][] data){
        int[][] result=new int[data.length][data[0].length];
        for(int i=0;i<data.length;i++){
            result[i]=Chauvenet(data[i]);
        }
        return result;
    }
    public double[][] AllChauvenet_double(int[][] data){
        double[][] result=new double[data.length][data[0].length];
        for(int i=0;i<data.length;i++){
            result[i]=Chauvenet_double(data[i]);
        }
        return result;
    }

    public double[][] StudentStandardDeviation(int[][] data){
        double[][] result=new double[data.length][data[0].length];
        //每个虚拟机按天划分   dayData[i].numInEachDay[j] 就是第i个虚拟机第j天的数量
        for(int i = 0; i < data.length; i++)
        {
            double sumOfX = 0;
            double averageX = 0;     //均值
            double[] offsetX =new double[data[0].length]; //残差
            double sumOfVi2 = 0;           //残差的平方累加
            double labStand = 0;           //实验标准差

		/*计算均值*/
            averageX=getAve(data[i]);

		/*计算残差*/
            for(int j = 0; j < data[i].length; j++)
            {
                offsetX[j] = data[i][j] - averageX;
                sumOfVi2 += Math.pow(offsetX[j],2);
            }

		/*计算实验标准差S*/
            if(data[i].length > 1)
            {
                int n = data[i].length - 1;
                labStand = Math.sqrt(sumOfVi2/n);
            }
            else
            {
                labStand = 60000;
            }

		/*计算实验学生化标准差*/
            double yi = 0;
            double maxData = averageX + labStand * 2;
            double minData = averageX - labStand * 2;
            if(minData < 0)
                minData = 0;

            for(int j = 0; j < data[i].length; j++)
            {
                if(data[i][j] == 0)
                    continue;
                yi = offsetX[j]/labStand;
			/*if(yi >2 || yi < -2)
			{
				dayData[i].numInEachDay[j] = averageX;
			}*/
                if(yi > 2)
                {
                    result[i][j]=maxData;
                    //dayData[i].numInEachDay[j] = maxData;
                }
                else if(yi < -2)
                {
                    result[i][j]=minData;
                    //dayData[i].numInEachDay[j] = minData;
                }else{
                    result[i][j]=data[i][j];
                }
            }
        }
        return result;
    }

    public void init_c_constant(){
        c_constant=new HashMap<Integer,Double>();
        c_constant.put(3,1.38);
        c_constant.put(4,1.54);
        c_constant.put(5,1.65);
        c_constant.put(6,1.73);
        c_constant.put(7,1.80);
        c_constant.put(8,1.86);
        c_constant.put(9,1.92);
        c_constant.put(10,1.96);
        c_constant.put(11,2.0);
        c_constant.put(12,2.03);
        c_constant.put(13,2.07);
        c_constant.put(14,2.10);
        c_constant.put(15,2.13);
        c_constant.put(16,2.15);
        c_constant.put(18,2.20);
        c_constant.put(20,2.24);
        c_constant.put(25,2.33);
        c_constant.put(30,2.39);
        c_constant.put(40,2.49);
        c_constant.put(50,2.58);
        c_constant.put(100,2.80);

    }
    public double getAve(int[] data){
        double result=0;
        for(int i=0;i<data.length;i++){
            result+=data[i];
        }
        return result/data.length;
    }
    public double getStdDev(int[] data){
        int m=data.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=data[i];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(data[i]-dAve)*(data[i]-dAve);
        }
        return Math.sqrt(dVar/m);
    }
    public int[][] Quartile_Sort(int[][] data){//将传入参数排序并四分位去除异常值，数据将被排序,返回一个新的排序的去除异常值的数据
        int[][] data_sort =new int[data.length][];
        for(int i=0;i<data.length;i++)
            data_sort[i]=data[i].clone();
        int[] Q1=new int[data.length],Q3=new int[data.length],mid_v=new int[data.length];
        double[] max_v=new double[data.length];
        for(int i=0;i<data.length;i++){
            Arrays.sort(data_sort[i]);
            int no_zero =0,end=data_sort[0].length;
            while(no_zero<data_sort[0].length&&data_sort[i][no_zero]==0){
                no_zero++;
            }
            Q1[i]=data_sort[i][no_zero+(int)((end-no_zero)*0.25)];
            Q3[i]=data_sort[i][no_zero+(int)((end-no_zero)*0.75)];
            max_v[i]= Q3[i] + 3 * (Q3[i] - Q1[i]);
            mid_v[i]=data_sort[i][no_zero+(int)((end-no_zero)*0.5)];
            for(int j=0;j<data_sort[0].length;j++){
                if(data_sort[i][j]>max_v[i]){
                    data_sort[i][j]=mid_v[i];
                }
            }
        }
        return data_sort;
    }
    public void Quartile(int[][] data){//将传入参数排序并四分位去除异常值，数据不被排序，处理后的数据在传入参数中，所以不返回
        int[][] data_sort =new int[data.length][];
        for(int i=0;i<data.length;i++)
            data_sort[i]=data[i].clone();
        int[] Q1=new int[data.length],Q3=new int[data.length],mid_v=new int[data.length];
        double[] max_v=new double[data.length];
        for(int i=0;i<data.length;i++){
            Arrays.sort(data_sort[i]);
            int no_zero =0,end=data_sort[0].length;
            while(no_zero<data_sort[0].length&&data_sort[i][no_zero]==0){
                no_zero++;
            }
            Q1[i]=data_sort[i][no_zero+(int)((end-no_zero)*0.25)];
            Q3[i]=data_sort[i][no_zero+(int)((end-no_zero)*0.75)];
            max_v[i]= Q3[i] + 3 * (Q3[i] - Q1[i]);
            mid_v[i]=data_sort[i][no_zero+(int)((end-no_zero)*0.5)];
            for(int j=0;j<data[0].length;j++){
                if(data[i][j]>max_v[i]){
                    data[i][j]=mid_v[i];
                }
            }
        }
    }

}
