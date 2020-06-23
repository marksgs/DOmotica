from pyfcm import FCMNotification

def notificaciones(token, titulo, cuerpo):
	push_service=FCMNotification(api_key="AAAA4kmRuRk:APA91bFOa5zU5ZmB-dWBThXrBEc0OHBXosn5b4A78OdJdEAqoY8w8izjFVsoCRjrQ8cyBQSor44ODISI3lVyI4jCHagKpiVj8Z60O-godjQYYSlM1FbZw2M4belqJAMyCS_jwK1lo8ks")
	registration_id=token
	message_title=titulo
	message_body=cuerpo
	result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)
	print(result)
