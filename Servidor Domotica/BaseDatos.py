import mysql.connector

class BaseDatos():
	def __init__(self):
		self.mydb=mysql.connector.connect(
			host="localhost",
			user="rpi",
			passwd="rpi",
			database="domotica")
		self.mycursor=self.mydb.cursor()
		self.arreglo=[]
		self.resultado=0

	def getDato(self,query):
		self.mycursor.execute(query)
		self.resultado=self.mycursor.fetchone()
		print("Resultado de base de datos: "+str(self.resultado[0]))
		return self.resultado[0]
	
	def actualizar(self, query, value):
		val=(value,)
		self.mycursor.execute(query, val)
		self.mydb.commit()
