import pandas as pd
import numpy as np

data=pd.read_csv('D:\\train_2016_v2.csv')
property=pd.read_csv('D:\\properties_2016.csv')
data=data.sample(len(data))
data_6=data.iloc[0:int(0.6*len(data)),:]
data_3=data.iloc[int(0.6*len(data)):int(0.9*len(data)),:]
data_1=data.iloc[int(0.9*len(data)):len(data),:]

def normalization(property):
    result=property.copy()

    return result
    pass


print(property)
#property.apply(lambda x:(x-np.average(x))/(np.max(x)-np.min(x)))
#print(normalization(property))