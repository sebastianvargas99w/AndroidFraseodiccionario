package com.example.sistema.Fraseodiccionario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Esta es la primera actividad que se ejecuta al iniciar la aplicación. Se encarga de mostrar la
 * imagen inicial
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView slash = (ImageView) findViewById(R.id.imageViewSplash);
        slash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMenu();
            }
        });
    }

    public void irMenu()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}