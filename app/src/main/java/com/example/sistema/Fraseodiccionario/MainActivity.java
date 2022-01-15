package com.example.sistema.Fraseodiccionario;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    /**
     * Este es el método que se ejecuta cuando inicia la actividad, en general todas las actividad tienen
     * un método onCreate que se ejecuta cuando se inicia la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); /* Este método tiene que estar en todos los método "onCreate"
        super significa que ejecuta la implementación de Android para el onCreate. */
        setContentView(R.layout.activity_main); /* Despliega la vista que entra como parámetro,
        en este caso es la vista en res/layout/activity_main.xml
        La parte "R.layout" son objetos que genera Android para trabajar con las vistas
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);// Habilita la barra de navegación, la que tiene el menú secundario
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button
        int id = item.getItemId();

        if (id == R.id.action_credits) {
            /* Los intents son acciones o tareas que puede realizar una aplicación,
            * este intent está indicando que se tiene que iniciar la actividad de créditos (CreditosActivity).
            * Este intent se podría ejecutar desde otra aplicación.
            * Los intents tienen que estar registrados en el archivo manifests/AndroidManifest.xml
            */
            Intent intent = new Intent(this,CreditosActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_aclaration) {
            Intent intent = new Intent(this,AclaracionActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_help) {
            Intent intent = new Intent(this,AyudaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Genera un mensaje Toast que desaparece rapidamente.
     * @param mensaje
     */
    public void showMessage(String mensaje)
    {
        Context contexto = getApplicationContext();
        Toast toast = Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT);
        toast.show();

    }
}
