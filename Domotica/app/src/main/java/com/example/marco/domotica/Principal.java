package com.example.marco.domotica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Principal extends AppCompatActivity {
    ImageButton luces, cortina, acceso, camara, varios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        luces=findViewById(R.id.Luces);
        cortina=findViewById(R.id.Cortinas);
        acceso=findViewById(R.id.Accesos);
        camara=findViewById(R.id.Camara);
        varios=findViewById(R.id.Varios);

        luces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Principal.this, Pantalla_luces.class);
                startActivity(intent);
            }
        });

        cortina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Principal.this, Pantalla_cortinas.class);
                startActivity(intent);
            }
        });

        acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Principal.this, Accesos_info.class);
                startActivity(intent);
            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Principal.this, Presencia_info.class);
                startActivity(intent);
            }
        });

        varios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Principal.this, Varios.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
