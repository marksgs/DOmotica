package com.example.marco.domotica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Pantalla_luces extends AppCompatActivity {
    ListView hab_luces;
    HashMap hm=new HashMap();
    JSONArray hab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_luces);
        hab_luces=findViewById(R.id.lista_habitaciones_luces);
        final ArrayList<String> habitaciones=new ArrayList<>();
        hm.put("Peticion","Habitacion");
        hm.put("Tabla","Habitaciones");
        JSONObject jo=new JSONObject(hm);
        JsonObjectRequest peticion=new JsonObjectRequest(Request.Method.POST, getString(R.string.ip) + "/ListaHabitaciones", jo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hab=response.getJSONArray("habitaciones");
                            for (int i=0; i<hab.length();i++){
                                habitaciones.add(hab.getString(i));

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
        RequestQueue cola= Volley.newRequestQueue(getApplicationContext());
        cola.add(peticion);


        ArrayAdapter<String> adaptador=new ArrayAdapter<>(this, R.layout.lista_habitaciones_luces, habitaciones);

        hab_luces.setAdapter(adaptador);

        hab_luces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), Control_luces.class);
                intent.putExtra("Habitacion_elegida", habitaciones.get(position));
                startActivity(intent);
            }
        });
    }
}
