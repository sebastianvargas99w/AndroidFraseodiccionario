package com.example.sistema.Fraseodiccionario;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * Esta actividad se encarga de mostrar las aclaraciones cuando de ingresa a la opción de aclaraciones
 * en el menú secundario de la aplicación.
 */
public class AclaracionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aclaracion);
        configurarBarraDeHerramientas();
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