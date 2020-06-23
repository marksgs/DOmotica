package com.example.marco.domotica;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.security.auth.callback.CallbackHandler;

public class Control_luces extends AppCompatActivity {
    ProgressBar intensidad;
    TextView Titulo_habitacion;
    TextView porcentaje;
    ImageButton mas, menos;
    int intensidad_porcentaje=0;
    HashMap hm=new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_luces);
        Titulo_habitacion=findViewById(R.id.habitacion_nombre);
        intensidad=findViewById(R.id.Intensidad_foco);
        porcentaje=findViewById(R.id.Int_porcentaje);
        mas=findViewById(R.id.max_int);
        menos=findViewById(R.id.min_int);

        final String hab_nom=getIntent().getStringExtra("Habitacion_elegida");
        Titulo_habitacion.setText(hab_nom);
        hm.put("habitacion",hab_nom);
        JSONObject jo=new JSONObject(hm);
        JsonObjectRequest peticion=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip) + "/getIntensidad", jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            intensidad_porcentaje=Integer.parseInt(response.getString("intensidad"));
                            porcentaje.setText(intensidad_porcentaje+"%");
                            intensidad.setProgress(intensidad_porcentaje);
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
        queue.add(peticion);

        //switch_luz.setChecked(true);
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intensidad_porcentaje!=100){
                    intensidad_porcentaje+=25;
                    intensidad.setProgress(intensidad_porcentaje);
                    porcentaje.setText(intensidad_porcentaje+"%");
                    hm.clear();
                    hm.put("Intensidad",intensidad_porcentaje);
                    hm.put("HabNom", hab_nom);
                    JSONObject modifint=new JSONObject(hm);
                    JsonObjectRequest peticion_intensidad=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip) + "/EstablecerIntensidad", modifint,
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
                    cola.add(peticion_intensidad);
                }
            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intensidad_porcentaje!=0){
                    intensidad_porcentaje-=25;
                    intensidad.setProgress(intensidad_porcentaje);
                    porcentaje.setText(intensidad_porcentaje+"%");
                    hm.clear();
                    hm.put("Intensidad",intensidad_porcentaje);
                    hm.put("HabNom", hab_nom);
                    JSONObject modifint=new JSONObject(hm);
                    JsonObjectRequest peticion_intensidad=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip) + "/EstablecerIntensidad", modifint,
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
                    cola.add(peticion_intensidad);
                }
            }
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
    }



}
