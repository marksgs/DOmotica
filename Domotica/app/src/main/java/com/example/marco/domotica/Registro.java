package com.example.marco.domotica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText new_usr,new_mail,new_psw,conf_psw;
    Button btn_enviar;
    HashMap hm=new HashMap();
    String url="/registrar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        new_usr=findViewById(R.id.new_usr);
        new_mail=findViewById(R.id.new_mail);
        new_psw=findViewById(R.id.new_psw);
        conf_psw=findViewById(R.id.confirm_psw);

        btn_enviar=findViewById(R.id.Enviar_datos);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(new_usr.getText().toString().trim()) || TextUtils.isEmpty(new_mail.getText().toString().trim())
                        || TextUtils.isEmpty(new_psw.getText().toString().trim()) || TextUtils.isEmpty(conf_psw.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();

                }
                else if(!emailValidator(new_mail.getText().toString())){

                    Toast.makeText(getApplicationContext(),"Ingrese un e-mail valido", Toast.LENGTH_SHORT).show();

                }else if (!new_psw.getText().toString().equals(conf_psw.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Las contraseñas deben coincidir",
                            Toast.LENGTH_SHORT).show();
                }

                SharedPreferences prefs=getSharedPreferences("TokenID", MODE_PRIVATE);
                String tokenObtenido=prefs.getString("Token", "No hay token");
                if (tokenObtenido.equals("No hay token")){
                    Toast.makeText(getApplicationContext(),tokenObtenido,Toast.LENGTH_SHORT).show();
                }

                hm.put("usr",new_usr.getText().toString());
                hm.put("mail",new_psw.getText().toString());
                hm.put("pass",new_psw.getText().toString());
                hm.put("token",tokenObtenido);
                JSONObject json=new JSONObject(hm);
                JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip) + url, json,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.get("respuesta").toString().equals("Registrado")){
                                        Toast.makeText(getApplicationContext(),"Registro Exitoso", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Registro.this, MainActivity.class);
                                        startActivity(intent);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(jsonRequest);
            }
        });
    }

    boolean emailValidator(String mail){
        Pattern pattern= Patterns.EMAIL_ADDRESS;
        return pattern.matcher(mail).matches();
    }
}
