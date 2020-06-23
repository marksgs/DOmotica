package com.example.marco.domotica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    Button b_nueva_cuenta;
    Button iniciar;
    EditText ed_usr,ed_psw;
    HashMap data=new HashMap();
    String url="/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_nueva_cuenta=findViewById(R.id.crear_cuenta);
        iniciar=findViewById(R.id.iniciar_sesion);
        ed_usr=findViewById(R.id.usuario);
        ed_psw=findViewById(R.id.password);
        SharedPreferences log_usr=getSharedPreferences("Usuario_logeado",MODE_PRIVATE);
        String usuario=log_usr.getString("usuario", null);
        if (usuario!=null){
            Intent intent=new Intent(MainActivity.this, Principal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(ed_usr.getText().toString()) || !TextUtils.isEmpty(ed_psw.getText().toString())){
                    if(!RedDisponible()){
                        /*Intent intent=new Intent(MainActivity.this, Principal.class);
                        startActivity(intent);
                        MainActivity.this.finish();*/
                        Toast.makeText(getApplicationContext(),"Conectate a Internet", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(getApplicationContext(),"Llena todos los campos", Toast.LENGTH_SHORT).show();

                data.put("usr",ed_usr.getText().toString());
                data.put("pass",ed_psw.getText().toString());

                JSONObject jsonObject=new JSONObject(data);
                //Toast.makeText(getApplicationContext(),jsonObject.toString(),Toast.LENGTH_SHORT).show();
                JsonObjectRequest jasonRequest=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip)+url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("respuesta").toString().equals("Aceptado")){
                                Toast.makeText(getApplicationContext(), "Aceptado", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor edit=(SharedPreferences.Editor)getSharedPreferences("Usuario_logeado", MODE_PRIVATE).edit();
                                edit.putString("usuario",ed_usr.getText().toString());
                                edit.commit();
                                Intent intent=new Intent(MainActivity.this, Principal.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Usuario/Contraseña invalidos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(jasonRequest);


            }
        });

        b_nueva_cuenta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });

    }

    private boolean RedDisponible(){
        ConnectivityManager conectivity=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=conectivity.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
    }
}
