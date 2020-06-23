
from sklearn.tree import DecisionTreeClassifier
import pandas as pd
import pickle

class arbolDecision:
    
    def __init__(self):
        self.arbol=None
        self.activado=False
        self.arbol_entrenado=None
        self.v=0
            
    def generarModelo(self):
        self.ds=pd.read_csv("entrenamiento.csv")
        self.label=self.ds['intensidad']
        self.features=self.ds.drop('intensidad', axis=1)
        self.arbol=DecisionTreeClassifier()
        self.arbol.fit(self.features,self.label)
        pickle.dump(self.arbol, open("modelo.pkl", "wb"))

    
    def predecir(self,hora,presencia):
        self.arbol_entrenado=pickle.load(open("modelo.pkl", "rb"))
        self.hora=hora
        self.presencia=presencia
        self.v=self.arbol_entrenado.predict([[self.hora,self.presencia]])
        return int(self.v)
        
    def entrenar(self):
        self.arbol_entrenado=pickle.load(open("modelo.pkl", "rb"))

    def guardarEntrenamiento(self, hora, presencia, intensidad):
        print("Guardando")
        self.archivo=open("entrenamiento.csv", "a")
        self.archivo.write(str(hora)+","+str(presencia)+","+str(intensidad)+"\n")
        self.archivo.close()

    def setActivacion(self, tf):
    	self.activado=tf

    def getActivacion(self):
    	return self.activado