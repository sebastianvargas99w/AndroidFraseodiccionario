package com.example.sistema.Fraseodiccionario;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.Objects;

/**
 * Esta actividad se encarga de mostrar los créditos cuando de ingresa a la opción de créditos
 * en el menú secundario de la aplicación.
 */

public class CreditosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        ActionBar actionBar = getSupportActionBar();

        // Flecha de navegación hacia atrás
        configurarBarraDeHerramientas();

        // Set the Drawable displayed
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable bitmap = getResources().getDrawable(R.drawable.creditos);
        //creditosImage.setImageDrawable(bitmap);

        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
       // new PhotoViewAttacher(creditosImage);
    }

    /**
     * Configura la barra con la flecha de navegación
     */
    private void configurarBarraDeHerramientas() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // flecha de navegación
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // se maneja la flecha de navegación
        int idItemSeleccionado = item.getItemId();
        if (idItemSeleccionado == android.R.id.home) {
            finish(); // se cierra la actividad para volver a la anterior
        }
        return super.onOptionsItemSelected(item);
    }

}