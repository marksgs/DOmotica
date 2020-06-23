import RPi.GPIO as GPIO
import time


class Gas():
	def __init__(self):
		GPIO.setmode(GPIO.BCM)
		GPIO.setwarnings(False)
		GPIO.setup(40, GPIO.OUT)
		GPIO.output(40, GPIO.HIGH)

	def cambiarValvula(self, estado):
		if estado==0:
			GPIO.output(40, GPIO.LOW)
		else:
			GPIO.output(40,GPIO.HIGH)
