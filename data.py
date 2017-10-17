import pandas as pd
import numpy as np

data=pd.read_csv('train_2016_v2.csv')
property=pd.read_csv('properties_2016.csv')
data=data.sample(len(data))
data_6=data.iloc[0:int(0.6*len(data)),:]
data_3=data.iloc[int(0.6*len(data)):int(0.9*len(data)),:]
data_1=data.iloc[int(0.9*len(data)):len(data),:]

def normalization(property):
    result=property.copy()

    return result
    pass


# tmp=(property-property.mean())/(property.max()-property.min())
# print(tmp)
X = np.array(property)
tmp = np.matrix(X - np.mean(X, axis=0)) / np.matrix(np.max(X, 0) - np.min(X, 0))
print(tmp)
#print(property)
#property.apply(lambda x:(x-np.average(x))/(np.max(x)-np.min(x)))
#print(normalization(property))
