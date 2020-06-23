#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Oct  7 19:53:29 2018

@author: marco
"""

#import pickle
from sklearn.tree import DecisionTreeClassifier
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

ds=pd.read_csv("entrenamiento.csv")
y=ds['intensidad']
x=ds.drop('intensidad', axis=1)
x_train,x_test, y_train,y_test=train_test_split(x,y,test_size=0.3, random_state=100)
arbol=DecisionTreeClassifier()
arbol.fit(x_train,y_train)
y_predict=arbol.predict(x_test)
print("Acertado un "+str(accuracy_score(y_test,y_predict)*100)+"%")