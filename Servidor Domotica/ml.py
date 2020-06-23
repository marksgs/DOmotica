from arbolDecision import arbolDecision
from celery.task.schedules import crontab
from celery import Celery

a=arbolDecision()
a.entrenar()

app=Celery('bgtasks', broker='amqp://guest@localhost//', backend='rpc://')
app.conf.enable_utc = False
app.conf.beat_schedule={
	#'Renovar_ML':{
	#	'task':'ml.RenovarML',
	#	'schedule':crontab(minute='0', hour='0', day_of_month='1')
	#},
	'Predecir_ML':{
		'task':'ml.predecirML',
		'schedule': 59.0
	}#,
	#'ChecarGas':{
	#	'task':'flask_bg.anomaliasGas',
	#	'schedule':crontab(minute='*/1')
	#}
}


@app.task
def predecirML():
	print("prediciendo")
	v=a.predecir(11.50,1)
	if not (a.getActivacion()):#b.getDato("Select estado from varios where elemento='MachineLearning'") == 1):
		print("Variable falsa")
		#pwm.cambiarIntensidad(v)
		a.guardarEntrenamiento(7.22, 1, v)
	else:
		print("variable verdadera")
		a.guardarEntrenamiento(7.22, 1, b.getDato("Select intensidad from Luz where Habitacion='Sala Principal'"))
	print ("Resultado: "+str(v))
	return v

@app.task
def RenovarML():
	a.generarModelo()
	return 'ok'

@app.task
def activacionML(b):
	if b:
		print("true")
	a.setActivacion(b);