package com.example.marco.domotica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Varios extends AppCompatActivity {
    Button seguridad, valvula, ml;
    Button cerrar_sesion;
    HashMap hm=new HashMap();
    int val,seg, mlv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varios);
        JSONObject jo=new JSONObject(hm);
        seguridad=findViewById(R.id.boton_actdesact);
        valvula=findViewById(R.id.botonGas);
        ml=findViewById(R.id.switch_activacionML);
        cerrar_sesion=findViewById(R.id.cerrarSesion);
        JsonObjectRequest jsonrequest=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip) + "/getVarios", jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray datos=response.getJSONArray("estados");

                            seg=Integer.parseInt(datos.getString(1));
                            val=Integer.parseInt(datos.getString(2));
                            mlv=Integer.parseInt(datos.getString(0));
                            seguridad.setText((seg==0)? "OFF":"ON");
                            valvula.setText((val==0)?"OFF":"ON");
                            ml.setText((mlv==0)?"OFF":"ON");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue cola=Volley.newRequestQueue(getApplicationContext());
        cola.add(jsonrequest);
        seguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seg==1){
                    seg=0;
                    seguridad.setText("OFF");
                }
                else{
                    seg=1;
                    seguridad.setText("ON");
                }

                actualizar(seg,"/PresenciaSeguridad");
            }
        });

        valvula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val==1){
                    val=0;
                    valvula.setText("OFF");
                }else{
                    val=1;
                    valvula.setText("ON");
                }

                actualizar(val,"/ValvulaGas");
            }
        });

        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mlv==1){
                    mlv=0;
                    ml.setText("OFF");
                }else{
                    mlv=1;
                    ml.setText("ON");
                }

                actualizar(mlv,"/activarML");
            }
        });

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor=(SharedPreferences.Editor)getSharedPreferences("Usuario_logeado", MODE_PRIVATE).edit();
                editor.remove("usuario");
                editor.commit();
                Intent intent=new Intent(Varios.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void actualizar(int valor, String ruta){
        HashMap actualizarValor=new HashMap();
        actualizarValor.put("estado",valor);
        JSONObject jo=new JSONObject(actualizarValor);
        JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip) + ruta, jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue cola=Volley.newRequestQueue(getApplicationContext());
        cola.add(jsonRequest);
    }
}
