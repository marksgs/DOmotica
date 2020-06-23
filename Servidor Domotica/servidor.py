from pyfcm import FCMNotification
from flask import Flask, request, jsonify, Response
from camera import VideoCamera
import mysql.connector
import json
import notif
#from flask_bg import PrimeraTask, activacionML#, modifLuz

app=Flask(__name__)
mydb=mysql.connector.connect(
	host="localhost",
	user="marco",
	passwd="Pa55word+",
	database="domotica")
mycursor=mydb.cursor()

#PrimeraTask.delay()

@app.route('/login', methods=['GET', 'POST'])
def login():
	print("login")
	data_login=request.get_json(force=True)
	usuario=data_login.get('usr')
	psw=data_login.get('pass')
	val=(usuario,)
	sql="Select password from Registrados where usuario=%s"
	mycursor.execute(sql, val)
	resultado=mycursor.fetchone()
	if psw==resultado[0]:
		return jsonify({'respuesta':'Aceptado'})
	else:
		return jsonify({'respuesta':'Invalido'})

def gen(camara):
	#camara.__init__()
	while True:
		frame=camara.get_frame()
		yield (b'--frame\r\n'
			b'Content-Type: image/jpeg\r\n\r\n'+frame+ b'\r\n\r\n')

@app.route('/video_feed')
def video_feed():
	print("Iniciando Stream")
	return Response(gen(VideoCamera()),
		mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/registrar', methods=['POST'])
def registrar():
	data_registrar=request.get_json(force=True)
	print(data_registrar.get('usr'))
	print(data_registrar.get('mail'))
	print(data_registrar.get('pass'))
	print(data_registrar.get('token'))
	usuario=data_registrar.get('usr')
	mail=data_registrar.get('mail')
	psw=data_registrar.get('pass')
	token=data_registrar.get('token')
	
	sql="Insert into Registrados (usuario, mail, password, token) values (%s,%s,%s,%s)"
	val=(usuario, mail, psw, token)
	mycursor.execute(sql, val)
	mydb.commit()
	return jsonify({'respuesta':'Registrado'})

@app.route('/ListaHabitaciones', methods=['POST'])
def Lista():
	pedido=request.get_json(force=True)
	dato=pedido.get('Peticion')
	tabla=pedido.get('Tabla')
	#query="Select Habitacion from Habitaciones"
	#val=(dato,tabla)
	mycursor.execute("Select Habitacion from Habitaciones")
	resultado=mycursor.fetchall()
	data={"habitaciones": [item[0] for item in resultado]}
	json_data=json.dumps(data)
	return json_data

@app.route('/getIntensidad', methods=['Post'])
def getIntensidd():
	pedido=request.get_json(force=True)
	nombre=pedido.get('habitacion')
	sql="Select intensidad from Luz where Habitacion=%s"
	val=(nombre,)
	mycursor.execute(sql,val)
	resultado=mycursor.fetchone()
	return jsonify({"intensidad":resultado[0]})

@app.route('/EstablecerIntensidad', methods=['Post'])
def EstablecerIntensidad():
	pedido=request.get_json(force=True)
	intensidad=pedido.get('Intensidad')
	hab=pedido.get('HabNom')
	sql="Update Luz set intensidad=%s where Habitacion=%s"
	val=(intensidad, hab)
	mycursor.execute(sql, val)
	mydb.commit()
	print("Intensidad establecida al {}%".format(intensidad))
	#	modifLuz.delay(intensidad)
	return jsonify({"respuesta":"hecho"})

@app.route('/getVarios', methods=['Post'])
def getVarios():
	mycursor.execute("Select estado from varios")
	resultado=mycursor.fetchall()
	data={"estados":[item[0] for item in resultado]}
	json_data=json.dumps(data)
	return json_data

@app.route('/PresenciaSeguridad', methods=['Post'])
def PresenciaSeguridad():
	pedido=request.get_json(force=True)
	seguridad=pedido.get('estado')
	sql="Update varios set estado=%s where elemento='PresenciaSeg'"
	val=(seguridad,)
	mycursor.execute(sql,val)
	mydb.commit()
	if seguridad==1:
		print("Se activo el modo de detección por seguridad de los sensores de presencia")
	else:
		print("Se desactivo el modo de detección por seguridad de los sensores de presencia")
	return jsonify({"respuesta":"hecho"})

@app.route('/ValvulaGas', methods=['Post'])
def ValvulaGas():
	pedido=request.get_json(force=True)
	valv=pedido.get('estado')
	sql="Update varios set estado=%s where elemento='ValvulaGas'"
	val=(valv,)
	mycursor.execute(sql,val)
	mydb.commit()
	if int(valv)==1:
		print("Se abrio la valvula")
		activacionValvula.delay(int(valv))
	else:
		print("Se cerro la valvula")
		activacionValvula.delay(int(valv))
	return jsonify({"respuesta":"hecho"})

@app.route('/activarML', methods=['POST'])
def activarML():
	pedido=request.get_json(force=True)
	actml=pedido.get('estado')
	sql="Update varios set estado=%s where elemento='MachineLearning'"
	val=(actml,)
	mycursor.execute(sql,val)
	mydb.commit()
	if actml==1:
		#activacionML.delay(True)
		return jsonify({"respuesta":"activado"})
	else:
		#activacionML.delay(False)
		return jsonify({"respuesta":"desactivado"})

if __name__ == '__main__':
	app.run()
	print("Servidor")