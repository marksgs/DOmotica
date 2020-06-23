package com.example.marco.domotica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Control_cortinas extends AppCompatActivity {
    SeekBar cortinas_abiertas;
    TextView Titulo_habitacion_cortinas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_cortinas);
        Titulo_habitacion_cortinas=findViewById(R.id.habitacion_nombre_cortinas);
        cortinas_abiertas=findViewById(R.id.Cortinas_abiertas);
        String hab_nom=getIntent().getStringExtra("Habitacion_elegida");
        Titulo_habitacion_cortinas.setText(hab_nom);
        //cortinas_abiertas.setProgress(80);

        cortinas_abiertas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getApplicationContext(),"Barra "+progress,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
