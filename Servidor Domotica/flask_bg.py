from celery import Celery
from arbolDecision import arbolDecision
import datetime
from celery.task.schedules import crontab
from notif import notificaciones
from BaseDatos import BaseDatos
from Sensores import Sensores
from Luz import Luz
from Gas import Gas

app=Celery('bgtasks', broker='amqp://guest@localhost//', backend='rpc://')
app.conf.enable_utc = False

app.conf.beat_schedule={
	'Renovar_ML':{
		'task':'flask_bg.RenovarML',
		'schedule':crontab(minute='0', hour='0', day_of_month='1')
	},
	'Predecir_ML':{
		'task':'flask_bg.predecirML',
		'schedule':crontab(minute='*/1')
	},
	'ChecarGas':{
		'task':'flask_bg.anomaliasGas',
		'schedule':1.0
	},
	'ChecarSeguridad':{
		'task':'flask_bg.checarSeguridad',
		'schedule': 30.0
	}
}

a=arbolDecision()

s=Sensores()
pwm=Luz()
g=Gas()
@app.task
def PrimeraTask():
	
	print("Modelo Entrenado")
	
	a.generarModelo()
	#a.entrenar()
	print("Conectada la base de datos")
	
	print("Sensores iniciados")
	
	print("PWM iniciado")

@app.task
def predecirML():
	b=BaseDatos()
	hora=datetime.datetime.now().hour
	minuto=datetime.datetime.now().minute
	hr=float(str(hora)+"."+str(minuto))
	presencia=s.checarPir()
	print("Presencia detectada: "+str(presencia))
	v=a.predecir(hr, presencia)
	if (b.getDato("Select estado from varios where elemento='MachineLearning'")== 1):
		print("Machine learning activado")
		pwm.cambiarIntensidad(v)
		a.guardarEntrenamiento(hr, presencia, v)
		b.actualizar("Update Luz set intensidad = %s where Habitacion='Sala Principal'", v)
	else:
		print("desactivado")
		v=b.getDato("Select intensidad from Luz where Habitacion='Sala Principal'")
		pwm.cambiarIntensidad(v)
		a.guardarEntrenamiento(hr, presencia, v)
	print ("Resultado: "+str(v))
	return v

@app.task
def activacionML(b):
	a.setActivacion(b)
	if a.getActivacion():
		print('Machine learning activado')
	else:
		print("Machine learning desactivado")

@app.task
def anomaliasGas():
	
	if s.checarGas()==1:
		b=BaseDatos()
		b.actualizar("Update varios set estado=0 where elemento='ValvulaGas'",0)
		g.cambiarValvula(0)
		print("Gas detectado. Mandando notificacion")
		token=b.getDato("Select token from Registrados")
		notificaciones(token, "Alerta de Gas", "Se cerro la valvula del Gas")
	else:
		pass
		#print("No se detecto gas")


@app.task()
def modifLuz(intensidad):
	pwm.cambiarIntensidad(intensidad)
	hora=datetime.datetime.now().hour
	minuto=datetime.datetime.now().minute
	a.guardarEntrenamiento(float(str(hora)+"."+str(minuto)), s.checarPir(), intensidad)

@app.task()
def RenovarML():
	a.generarModelo()
	#a.entrenar()

@app.task()
def activacionValvula(estado):
	g.cambiarValvula(estado)

@app.task()
def checarSeguridad():
	b=BaseDatos()
	if (b.getDato("Select estado from varios where elemento='PresenciaSeg'")== 1):
		if s.checarPir()==1:
			token=b.getDato("Select token from Registrados")
			notificaciones(token, "Intruso detectado", "Se ha detectado presencia en la casa")
		if s.checarMagnetico()==1:
			token=b.getDato("Select token from Registrados")
			notificaciones(token, "Acceso abierto", "Se ha detectado un acceso que no fue cerrado")