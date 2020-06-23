package com.example.marco.domotica;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Accesos_info extends AppCompatActivity {
    LinearLayout lli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesos_info);
        ArrayList<String> acceso=new ArrayList<>();
        ArrayList<String> estado=new ArrayList<>();
        lli=findViewById(R.id.Layout_accesos);
        acceso.add("Puerta principal");
        acceso.add("Puerta trasera");
        acceso.add("Puerta habitacion");
        acceso.add("Ventana principal");
        acceso.add("Ventana trasera");
        acceso.add("Ventana baño");

        estado.add("Cerrado");
        estado.add("Cerrado");
        estado.add("Abierto");
        estado.add("Abierto");
        estado.add("Abierto");
        estado.add("Abierto");

        for (int i=0; i<acceso.size();i++){
            TextView Info=new TextView(this);
            Info.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            Info.setId(i);
            Info.setTextColor(Color.WHITE);
            Info.setTextSize(2, 25);
            Info.setText(acceso.get(i)+"\tEstado:\t"+estado.get(i)+"\n");
            lli.addView(Info);
        }


    }
}
