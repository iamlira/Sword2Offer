import pandas as pd
import numpy as np
from sklearn import preprocessing as prep
from sklearn.preprocessing import MinMaxScaler

def return_data():
    data=pd.read_csv('D:\\train_2016_v2.csv')
    property=pd.read_csv('D:\\properties_2016.csv')
    data=data.sample(len(data))
    #data_6=data.iloc[0:int(0.6*len(data)),:]
    #data_3=data.iloc[int(0.6*len(data)):int(0.9*len(data)),:]
    #data_1=data.iloc[int(0.9*len(data)):len(data),:]

    X=data.drop('logerror',1)
    y=data['logerror']

    property.fillna(0,inplace=True)
    property.replace('Y',1,inplace=True)
    property.replace('N',0,inplace=True)

    tlist=property.dtypes[lambda x:x=='object']
    tlist=tlist.index.tolist()
    property.drop(tlist,axis=1,inplace=True)
    id_tmp=property['parcelid']
    property.drop('parcelid',axis=1,inplace=True)
    block_tmp=property.drop('censustractandblock',axis=1,inplace=True)

    scaler=MinMaxScaler((-1,1))
    scaler.fit(property)
    result=scaler.transform(property)
    result=[id_tmp,result]

    return result,X,y

result,X,y=return_data()
print(X)