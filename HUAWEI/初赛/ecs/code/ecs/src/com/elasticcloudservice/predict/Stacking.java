package com.elasticcloudservice.predict;

/**
 * Created by lira on 2018/4/8.
 */
public class Stacking {
    public double[] Weights;
    public LinearRegressionWithSGD model;
    public void init(double[][] train_data,double[] label_data, int numIterations,
                     double stepSize, int miniBatchFraction, int col, int row){
        Weights=new double[col];
        model = new LinearRegressionWithSGD(train_data, label_data,
                numIterations, stepSize, miniBatchFraction, Weights, stepSize, col, row);
    }
    public void train(){
        model.train();
    }
    public double[] predict(double[] test_data_1,double[] test_data_2){
        double[][] test_data=new double[test_data_1.length][2+1];//加1是因为偏置项;
        for(int i=0;i<test_data.length;i++){
            test_data[i][0]=1;
            test_data[i][1]=test_data_1[i];
            test_data[i][2]=test_data_2[i];
        }
        return model.predict(test_data);
    }
    public double[][] getStackingTrainData_ES(double[][] train_data_1,double[][] train_data_2,double model_1_a1,double model_1_a2,double model_2_a1,double model_2_a2) {
        double[][] model_1_train = new double[train_data_1.length][train_data_1[0].length - 1];
        double[][] model_2_train = new double[train_data_2.length][train_data_2[0].length - 1];
        for (int i = 0; i < model_1_train.length; i++) {
            for (int j = 0; j < model_1_train[i].length; j++) {
                model_1_train[i][j] = train_data_1[i][j];
            }
        }
        for (int i = 0; i < model_2_train.length; i++) {
            for (int j = 0; j < model_2_train[i].length; j++) {
                model_2_train[i][j] = train_data_2[i][j];
            }
        }
        ESmooth eSmooth = new ESmooth(0.85);
        double[] results_1 = eSmooth.Second_ES_With_a1_and_a2(model_1_train, model_1_a1, model_1_a2);
        double[] results_2 = eSmooth.Second_ES_With_a1_and_a2(model_2_train, model_2_a1, model_2_a2);
        for(int i=0;i<results_2.length;i++){
            results_2[i]=results_2[i]-model_2_train[i][model_2_train[i].length-1];
        }
        double[][] stacking_train=new double[results_1.length][2+1];//加1是因为偏置项
        for(int i=0;i<results_1.length;i++){
            stacking_train[i][0]=1;
            stacking_train[i][1]=results_1[i];
            stacking_train[i][2]=results_2[i];
        }
        return stacking_train;
    }
    public double[] getStackingLabelData_ES(double[][] label_data){
        double[] label=new double[label_data.length];
        for(int i=0;i<label.length;i++){
            label[i]=label_data[i][label_data[i].length-1];
        }
        return label;
    }
}
