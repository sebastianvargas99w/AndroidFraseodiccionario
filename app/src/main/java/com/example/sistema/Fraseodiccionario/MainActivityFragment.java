package com.example.sistema.Fraseodiccionario;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing prueba1 simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        TextView botonInformacion = root.findViewById(R.id.btn_informacion);
        TextView botonPregunta = root.findViewById(R.id.btn_pregunta);
        TextView botonSaludo = root.findViewById(R.id.btn_saludo);
        TextView botonDespedida = root.findViewById(R.id.btn_despedida);
        TextView botonAgradecimiento = root.findViewById(R.id.btn_agradecimiento);
        TextView botonFisico = root.findViewById(R.id.btn_fisico);
        TextView botonEmocion = root.findViewById(R.id.btn_emocion);
        TextView botonMomento = root.findViewById(R.id.btn_momento);
        TextView botonAccion = root.findViewById(R.id.btn_accion);
        TextView botonSolicitud = root.findViewById(R.id.btn_solicitud);
        TextView botonPermiso = root.findViewById(R.id.btn_permiso);
        TextView botonActividad = root.findViewById(R.id.btn_actividad);
        TextView botonGusto = root.findViewById(R.id.btn_gusto);
        TextView botonPregunta2 = root.findViewById(R.id.btn_pregunta2);
        TextView botonExpresion = root.findViewById(R.id.btn_expresion);

        // Se determina qué acción se debe llevar a cabo al presionar cada boton del menú principal
        botonInformacion.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,1); });
        botonPregunta.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,2); });
        botonSaludo.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,3); });
        botonDespedida.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,4); });
        botonAgradecimiento.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,5); });
        botonFisico.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,6); });
        botonEmocion.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,7); });
        botonMomento.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,8); });
        botonAccion.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,9); });
        botonSolicitud.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,10); });
        botonPermiso.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,11); });
        botonActividad.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,12); });
        botonGusto.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,13); });
        botonPregunta2.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,14); });
        botonExpresion.setOnClickListener(v -> { llamarVista(MostradorDeFrases.class,15); });

        return root;
    }

    /**
     * Llama casoLetra1 una nueva vista sin destruir la vista actual, de manera que el usuario pueda regresar
     * al menu principal con el boton de retroceder del teléfono.
     * @param clase La clase casoLetra1 llamar. No tiene que ser una instancia.
     * @param categoria El número de categoria al cual dirige el boton
     */
    public void llamarVista(Class clase, int categoria)
    {
        Intent intent = new Intent(this.getContext(),clase);
        intent.putExtra("CATEGORIA", "" + categoria);
        startActivity(intent);
    }

}
