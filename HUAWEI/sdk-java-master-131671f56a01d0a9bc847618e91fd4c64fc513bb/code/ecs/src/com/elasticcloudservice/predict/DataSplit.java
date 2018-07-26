package com.elasticcloudservice.predict;

public class DataSplit {//数据划分类
    public int[][] SplitDataWithT(int[][] data,int T){//根据周期来划分训练集，T为周期，以天为单位,滑窗方法
        //if(data[0].length-T+1>1){
        return SplitDataWithTBG0(data,T);
        //}
        //else{
        //  return SplitDataWithTLS0(data);
        //}
    }
    public int[][] SplitDataWithTBG0(int[][] data,int T){//根据周期来划分训练集，T为周期，以天为单位,预测周期大于训练周期
        int[][] return_data=new int[data.length][data[0].length-T+1];
        for(int i=0;i<data.length;i++){
            int end = data[i].length - 1;
            int start = data[i].length - T;
            for(int j=return_data[i].length-1;start >= 0&&j>=0;start--,end--,j--){
                int sum=0,index=end;
                while(index >= start){
                    sum += data[i][index];
                    index --;
                }
                return_data[i][j]=sum;
            }
        }
        return return_data;
    }

    public int[][] SplitDataWithTLS0(int[][] data){//根据周期来划分训练集，T为周期，以天为单位,预测周期小于训练周期
        int[][] return_data=new int[data.length][1];
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data[0].length;j++){
                return_data[i][0]+=data[i][j];
            }
        }
        return return_data;
    }
    public int[][] SplitDataBlockWithT(int[][] data,int T){//根据周期来划分训练集，T为周期，以天为单位,分块区间
        if(data[0].length/T>1){
            return SplitDataBlockWithTBG0(data,T);
        }else{
            return SplitDataBlockWithTLS0(data);
        }
    }
    public int[][] SplitDataBlockWithTBG0(int[][] data,int T){
        int[][] return_data=new int[data.length][data[0].length/T];
        for(int i=0;i<data.length;i++){
            int end = data[i].length - 1;
            int start = end - T + 1;
            for(int j=return_data[i].length-1;start >= 0&&j>=0;end=start-1,start=end-T+1,j--){
                int sum=0,index=end;
                while(index >= start){
                    sum += data[i][index];
                    index --;
                }
                return_data[i][j]=sum;
            }
        }
        return return_data;
    }
    public double[][] SplitDataBlock_double(double[][] data,int T){
        double[][] return_data=new double[data.length][data[0].length/T];
        for(int i=0;i<data.length;i++){
            int end = data[i].length - 1;
            int start = end - T + 1;
            for(int j=return_data[i].length-1;start >= 0&&j>=0;end=start-1,start=end-T+1,j--){
                int sum=0,index=end;
                while(index >= start){
                    sum += data[i][index];
                    index --;
                }
                return_data[i][j]=sum;
            }
        }
        return return_data;
    }
    public int[][] SplitDataBlockWithTLS0(int[][] data){
        int[][] return_data=new int[data.length][1];
        for(int i=0;i<data.length;i++){
            for(int j=0;j<data[0].length;j++){
                return_data[i][0]+=data[i][j];
            }
        }
        return return_data;
    }
    public double[][] GetLinearTrainData(int[][] data,int T){//无论奇偶，都是返回到data的data.length/2位置
        double[][] result= new double[data.length][data[0].length/2];
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[0].length;j++)
                result[i][j]=data[i][j];
        }
        return result;
    }
    public double[][] GetLinearTrainDataWithBias(int[][] data,int T){//无论奇偶，都是返回到data的data.length/2位置,带偏置项
        double[][] result= new double[data.length][data[0].length/2+1];
        for(int i=0;i<result.length;i++){
            result[i][0] =1;
            for(int j=1;j<result[0].length;j++)
                result[i][j]=data[i][j];
        }
        return result;
    }
    public double[] GetLinearLabelData(int[][] data,int T){//无论奇偶，返回的都是data[0].length/2位置的数
        double[] result=new double[data.length];
        for(int i=0;i<result.length;i++){
            result[i]=data[i][data[0].length/2];
        }
        return result;
    }
    public double[][] GetLinearTestData(int[][] data,int T){//奇偶分开处理
        if(data[0].length%2==1)
            return GetLinearTestDataIfOdd(data,T);
        else
            return GetLinearTestDataIfEven(data,T);
    }
    public double[][] GetLinearTestDataWithBias(int[][] data,int T){//奇偶分开处理
        if(data[0].length%2==1)
            return GetLinearTestDataIfOddWithBias(data,T);
        else
            return GetLinearTestDataIfEvenWithBias(data,T);
    }
    public double[][] GetLinearTestDataIfOdd(int[][] data,int T){
        double[][] result=new double[data.length][data[0].length/2];
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[0].length;j++){
                result[i][j]=data[i][data[0].length/2+j+1];
            }
        }
        return result;
    }
    public double[][] GetLinearTestDataIfOddWithBias(int[][] data,int T){//带偏置项
        double[][] result=new double[data.length][data[0].length/2+1];
        for(int i=0;i<result.length;i++){
            result[i][0]=1;
            for(int j=1;j<result[0].length;j++){
                result[i][j]=data[i][data[0].length/2+j];
            }
        }
        return result;
    }
    public double[][] GetLinearTestDataIfEven(int[][] data,int T){//带偏置项
        double[][] result=new double[data.length][data[0].length/2];
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[0].length;j++){
                result[i][j]=data[i][data[0].length/2+j];
            }
        }
        return result;
    }
    public double[][] GetLinearTestDataIfEvenWithBias(int[][] data,int T){
        double[][] result=new double[data.length][data[0].length/2+1];
        for(int i=0;i<result.length;i++){
            result[i][0]=1;
            for(int j=1;j<result[0].length;j++){
                result[i][j]=data[i][data[0].length/2+j-1];
            }
        }
        return result;
    }
}
