import RPi.GPIO as GPIO
import time


class Luz():
	def __init__(self):
		self.intensidad=0
		GPIO.setmode(GPIO.BOARD)
		GPIO.setwarnings(False)
		GPIO.setup(7, GPIO.OUT)
		self.foco=GPIO.PWM(7,100)
		self.foco.start(self.intensidad)

	def cambiarIntensidad(self, intensidad):
		self.intensidad=intensidad
		print("Intensidad que llega={}".format(self.intensidad))
		#self.foco.ChangeDutyCycle(self.intensidad)
		self.foco.stop()
		self.foco=GPIO.PWM(7,100)
		self.foco.start(self.intensidad)
