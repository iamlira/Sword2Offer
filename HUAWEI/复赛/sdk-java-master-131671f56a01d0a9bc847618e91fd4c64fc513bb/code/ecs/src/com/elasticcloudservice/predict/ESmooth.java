package com.elasticcloudservice.predict;

/**
 * Created by lira on 2018/3/13.
 */
public class ESmooth {//指数平滑类
    public double alpha;
    public DataTransform dataTransform=new DataTransform();

    public ESmooth(double alpha) {
        this.alpha = alpha;
    }

    public double[] Single_ES(int[][] data) {
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            double S = 0;//上一期的预测值
            S = (data[i][0] + data[i][1] + data[i][2]) / 3;
            for (int j = 0; j < data[0].length; j++) {
                S = alpha * data[i][j] + (1 - alpha) * S;
            }
            result[i] = S;
        }
        return result;
    }

    public double[][] Single_ES_With_alpha_double(int[][] data, float set_alpha) {
        double[][] result = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            double S = 0;//上一期的预测值
            S = (data[i][0] + data[i][1] + data[i][2]) / 3;
            for (int j = 0; j < data[0].length; j++) {
                S = set_alpha * data[i][j] + (1 - set_alpha) * S;
                result[i][j] = S;
            }
        }
        return result;
    }
    public int[][] Single_ES_With_alpha_int(int[][] data, float set_alpha) {
        double[][] result = new double[data.length][data[0].length];
        int[][] result_int = new int[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            double S = 0;//上一期的预测值
            S = (data[i][0] + data[i][1] + data[i][2]) / 3;
            for (int j = 0; j < data[0].length; j++) {
                S = set_alpha * data[i][j] + (1 - set_alpha) * S;
                result[i][j] = S;
            }

        }
        result_int=dataTransform.Double2Int(result);
        return result_int;
    }

    public double[] Second_ES(int[][] data) {//二次指数平滑，及其直线模型
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            double S2_t = 0, S2_t_1 = 0, S1_t = 0, S1_t_1 = 0;//上一期的预测值
            if (data[i].length > 2)
                S1_t_1 = (data[i][0] + data[i][1] + data[i][2]) / 3;
            else
                S1_t_1 = data[i][0];
            S1_t = alpha * data[i][0] + (1 - alpha) * S1_t_1;
            S2_t_1 = S1_t;
            S2_t = alpha * S1_t + (1 - alpha) * S2_t_1;
            for (int j = 1; j < data[0].length; j++) {
                S1_t = alpha * data[i][j] + (1 - alpha) * S1_t_1;
                S1_t_1 = S1_t;
                S2_t = alpha * S1_t + (1 - alpha) * S2_t;
            }
            double a1 = 2 * S1_t - S2_t;
            double b1 = (alpha / (1 - alpha)) * (S1_t - S2_t);
            result[i] = a1 + b1 * 1;
            //result[i] = S2_t;
            result[i]=result[i]>0?result[i]:0;
        }
        return result;
    }
    public double[] Second_ES(double[][] data) {//二次指数平滑，及其直线模型
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            double S2_t = 0, S2_t_1 = 0, S1_t = 0, S1_t_1 = 0;//上一期的预测值
            if (data[i].length > 2)
                S1_t_1 = (data[i][0] + data[i][1] + data[i][2]) / 3;
            else
                S1_t_1 = data[i][0];
            S1_t = alpha * data[i][0] + (1 - alpha) * S1_t_1;
            S2_t_1 = S1_t;
            S2_t = alpha * S1_t + (1 - alpha) * S2_t_1;
            for (int j = 1; j < data[0].length; j++) {
                S1_t = alpha * data[i][j] + (1 - alpha) * S1_t_1;
                S1_t_1 = S1_t;
                S2_t = alpha * S1_t + (1 - alpha) * S2_t;
            }
            double a1 = 2 * S1_t - S2_t;
            double b1 = (alpha / (1 - alpha)) * (S1_t - S2_t);
            result[i] = a1 + b1 * 1;
            //result[i] = S2_t;
            result[i]=result[i]>0?result[i]:0;
        }
        return result;
    }

    public double[] Second_ES_With_a1_and_a2(double[][] data,double alpha1,double alpha2,double times) {//二次指数平滑，及其直线模型
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            double S2_t = 0, S2_t_1 = 0, S1_t = 0, S1_t_1 = 0;//上一期的预测值
            if (data[i].length > 2)
                S1_t_1 = (data[i][0] + data[i][1] + data[i][2]) / 3;
            else
                S1_t_1 = data[i][0];
            S1_t = alpha1 * data[i][0] + (1 - alpha1) * S1_t_1;
            S2_t_1 = S1_t;
            S2_t = alpha2 * S1_t + (1 - alpha2) * S2_t_1;
            for (int j = 1; j < data[0].length; j++) {
                S1_t = alpha1 * data[i][j] + (1 - alpha1) * S1_t_1;
                S1_t_1 = S1_t;
                S2_t = alpha2 * S1_t + (1 - alpha2) * S2_t;
            }
            double a1 = 2 * S1_t - S2_t;
            double b1 = (alpha2 / (1 - alpha2)) * (S1_t - S2_t);
            result[i] = a1 + b1 * times;
            //result[i] = S2_t;
            result[i]=result[i]>0?result[i]:0;
        }
        return result;
    }
    public double[] Second_ES_With_a1_and_a2(int[][] data,double alpha1,double alpha2) {//二次指数平滑，及其直线模型
        double[] result = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            double S2_t = 0, S2_t_1 = 0, S1_t = 0, S1_t_1 = 0;//上一期的预测值
            if (data[i].length > 2)
                S1_t_1 = (data[i][0] + data[i][1] + data[i][2]) / 3;
            else
                S1_t_1 = data[i][0];
            S1_t = alpha1 * data[i][0] + (1 - alpha1) * S1_t_1;
            S2_t_1 = S1_t;
            S2_t = alpha2 * S1_t + (1 - alpha2) * S2_t_1;
            for (int j = 1; j < data[0].length; j++) {
                S1_t = alpha1 * data[i][j] + (1 - alpha1) * S1_t_1;
                S1_t_1 = S1_t;
                S2_t = alpha2 * S1_t + (1 - alpha2) * S2_t;
            }
            double a1 = 2 * S1_t - S2_t;
            double b1 = (alpha2 / (1 - alpha2)) * (S1_t - S2_t);
            result[i] = a1 + b1 * 1;
            //result[i] = S2_t;
            result[i]=result[i]>0?result[i]:0;
        }
        return result;
    }

    //优化失败。。
    public double[] Second_ES_With_Pre(int[][] data, int[][] target_num_ed, int pre_day) {//二次指数平滑，及其直线模型,2018-3-16对预测日期进行优化
        double[] result = new double[data.length];
        double[][] target_num_pre_day = new double[data.length][pre_day];
        for (int i = 0; i < data.length; i++) {
            double S2_t = 0, S2_t_1 = 0, S1_t = 0, S1_t_1 = 0;//上一期的预测值
            if (data[i].length > 2)
                S1_t_1 = (data[i][0] + data[i][1] + data[i][2]) / 3;
            else
                S1_t_1 = data[i][0];
            S1_t = alpha * data[i][0] + (1 - alpha) * S1_t_1;
            S2_t_1 = S1_t;
            S2_t = alpha * S1_t + (1 - alpha) * S2_t_1;
            for (int j = 1; j < data[0].length; j++) {
                S1_t = alpha * data[i][j] + (1 - alpha) * S1_t_1;
                S1_t_1 = S1_t;
                S2_t = alpha * S1_t + (1 - alpha) * S2_t;
            }
            double a1 = 2 * S1_t - S2_t;
            double b1 = (alpha / (1 - alpha)) * (S1_t - S2_t);
            result[i] = a1 + b1 * 1;

            target_num_pre_day[i][0] = result[i] - (data[i][data[i].length - 1] - target_num_ed[i][target_num_ed[i].length +1 - pre_day]);
            for (int k = 1; k < pre_day - 1; k++) {//使用预测数据继续预测
                double result_tmp=result[i];
                S1_t = alpha * result[i] + (1 - alpha) * S1_t_1;
                S1_t_1 = S1_t;
                S2_t = alpha * S1_t + (1 - alpha) * S2_t;
                a1 = 2 * S1_t - S2_t;
                b1 = (alpha / (1 - alpha)) * (S1_t - S2_t);
                result[i] = a1 + b1 * 0;

                target_num_pre_day[i][k] = result[i] - (result_tmp - target_num_ed[i][target_num_ed[i].length + 1 - pre_day +k]);
            }
            result[i] = 0;
            for (int j = 0; j < target_num_pre_day[0].length; j++) {
                result[i] += target_num_pre_day[i][j];
            }
            result[i]=result[i]>0?result[i]:0;
            //result[i] = S2_t;
        }
        return result;
    }

    public double[] Second_ES_With_alpha(int[][] data, float set_alpha) {
        double[] result = new double[data.length];
        return result;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getAlph() {
        return alpha;
    }
}
