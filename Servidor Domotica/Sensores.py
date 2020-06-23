import RPi.GPIO as GPIO
import time

class Sensores():
	def __init__(self):
		GPIO.setmode(GPIO.BOARD)
		self.pir_sensor=11
		self.gas_sensor=13
		self.mag_sensor=15
		GPIO.setup(self.pir_sensor,GPIO.IN)
		GPIO.setup(self.gas_sensor,GPIO.IN)
		GPIO.setup(self.mag_sensor,GPIO.IN)

	def checarPir(self):
		return GPIO.input(self.pir_sensor)

	def checarGas(self):
		return GPIO.input(self.gas_sensor)

	def checarMagnetico(self):
		return GPIO.input(self.mag_sensor)