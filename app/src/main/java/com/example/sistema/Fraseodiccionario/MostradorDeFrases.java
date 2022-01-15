package com.example.sistema.Fraseodiccionario;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.chrisbanes.photoview.PhotoView;

/**
 * Despliega una interfaz desde la cual se pueden recorrer todas las palabras de todas las categorias.
 * Cada vez que se cambie de palabra, se actualiza el audio y la imagen a desplegar
 *
 * Notación:
 *
 * Audios:
 *      c[número de categoria]s[número de palabra][genero]<versión de Panama>
 *      donde:
 *      Genero h = voz masculina
 *      Genero f = voz femenina
 *
 *      Versión de panama corresponde a la letra p. Si la tiene agregada, entonces
 *      dicho audio corresponde a un audio de Panama. Esto según el cambio indicado.
 *      Audios pendientes
 *
 * Ilustraciones:
 *      c[número de categoria]s[número de palabra]<versión de Pais>
 *      Versión de Panamá corresponde a la letra pn
 *      Versión de Costa Rica corresponde a la letra cr
*
 * Traducciones:
 *
 *  Importante: Los números de categoria del 1 al 9 NO empiezan con 0.
 *  Se empieza a contar a partir del uno.
 */



public class MostradorDeFrases extends AppCompatActivity {

    private final String HOMBRE = "h";
    private final String MUJER = "m";
    private final String PANAMA = "pn";
    private final String COSTARICA = "cr";
    private final int PRIMERA_CATEGORIA = 1;
    private final int cantidad_categorias = 15;

    private int categoriaIntent = 1; //categoria a la que se ingresó desde el menú principal


    private int indice_frase = 0; //Indical cual es la frase de la categoria actual.
    private int id_audio = 0; //Indica cual es el audio que debe sonar
    private int id_imagen = 0; //Indica cual es la imagen que se debe mostrar
    private int categoria = 0; //Indica la categoria actual
    private String genero = MUJER;

    private int cantidad_elementos[]; //Indica la cantidad de elemmentos por categoria
    private String nombresCategorias[]; //Almacena el nombre de las categorias

    private String traduccion = "";  //Almacena la traducción de la palabra actual

    private ImageView imagenFrase; // Contiene la imagen para representar cada frase
    private ImageView imageViewPais;
    private ImageView imageViewGenero;
    private ImageButton botonTraducir;
    private ImageButton botonReproducirAudio;
    private Button botonSiguiente;
    private Button botonAtras;

    private LinearLayout linearLayoutPais;
    private LinearLayout linearLayoutGenero;
    private View raya;
    private EditText ingresoDePalabra;
    private TextView textoMaximoPalabra;
    private TextView textoPais1;
    private TextView textoPais2;
    private String NOMBRE_APLICACION = "Fraseodiccionario";
    private String tituloCategoria = ""; //título de la categoría que se muestra en la pantalla
    private final int POSICION_PRIMERA_FRASE = 0;

    private String version_pais = COSTARICA; //país actual del audio

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrador_frases);
        inicializarCategorias();

        imagenFrase = (ImageView) findViewById(R.id.imageViewIlustracion);
        imageViewPais = (ImageView) findViewById(R.id.imageViewPanama);
        imageViewGenero =(ImageView) findViewById(R.id.imageViewGenero);
        linearLayoutPais = (LinearLayout) findViewById(R.id.linearLayoutPais);
        linearLayoutGenero = (LinearLayout) findViewById(R.id.linearLayoutGenero);
        raya = (View) findViewById(R.id.view1);

        textoMaximoPalabra = (TextView) findViewById(R.id.textoMaximoPalabra);
        textoPais1 = (TextView) findViewById(R.id.txt_CR);
        textoPais2 = (TextView) findViewById(R.id.txt_Panama);
        botonTraducir = (ImageButton) findViewById(R.id.botonTraducir);
        botonReproducirAudio = (ImageButton) findViewById(R.id.botonReproducirAudio);
        botonSiguiente = (Button) findViewById(R.id.btn_siguiente);
        botonAtras = (Button) findViewById(R.id.btn_atras);

        ingresoDePalabra = (EditText) findViewById(R.id.ingresoDePalabra);

        nombresCategorias =  getResources().getStringArray(R.array.categorias);

        this.confugirarBarraDeHerramientas();
        this.establecerEscuchadores();

        categoria = Integer.parseInt(getIntent().getStringExtra("CATEGORIA"));
        categoriaIntent = categoria;
        indice_frase = 0; //Accede a la primera palabra por default
        getSupportActionBar().setSubtitle(this.NOMBRE_APLICACION);
        setearRecursos();

        // Ajusta la interfaz si el tamaño de la pantalla es lo suficientemente grande (si se usa scroll)
        if(this.seUsaScroll())
        {
            this.ajustarIntefaz();
        }

    }

    /**
     * Verifica si el dispositivo habilita el scroll para mostrar todas la interfaz
     * fuente
     * https://stackoverflow.com/questions/4743116/get-screen-width-and-height-in-android?rq=1
     * */
    private boolean seUsaScroll()
    {
        boolean usandoScroll = false;
        final int ALTURA_PEQUENA_PANTALLA = 1785;
        final int ALTURA_PANTALLA = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        if (ALTURA_PANTALLA <= ALTURA_PEQUENA_PANTALLA)
        {
            usandoScroll = true;
        }

        return usandoScroll;
    }

    /**
     * Elimina los margenes de algunas de las imagenes de la interfaz y reduce el tamaño de algunos
     * elementos, para que se se ve mejor en pantallas pequeñas
     * */
    private void ajustarIntefaz()
    {
        LinearLayout layoutBotones = (LinearLayout) findViewById(R.id.layoutBotones);
        LinearLayout layoutAcciones = (LinearLayout) findViewById(R.id.linearLayoutAcciones);
        cambiarMargen(layoutBotones,0,0,0,0);
        cambiarMargen(layoutAcciones,0,0,0,0);

        //remueve rayas en la interfaz cuyo objetivo es estético
        LinearLayout layoutPadre = new LinearLayout(getApplicationContext());
        layoutPadre.removeView(findViewById(R.id.view1));

        int dimensionMinimaCheckbox = getResources().getDimensionPixelSize(R.dimen.dimension_minima_checkbox);

        ViewGroup.LayoutParams dimensionesPais = imageViewPais.getLayoutParams();
        dimensionesPais.height = dimensionMinimaCheckbox;
        dimensionesPais.width = dimensionMinimaCheckbox;
        ViewGroup.LayoutParams dimensionesGenero = imageViewGenero.getLayoutParams();
        dimensionesGenero.height = dimensionMinimaCheckbox;
        dimensionesGenero.width = dimensionMinimaCheckbox;

    }

    //fuente https://stackoverflow.com/questions/4472429/change-the-right-margin-of-a-view-programmatically
    /**
     * Cambi
     * */
    public void cambiarMargen (View objetoVista, int izquierda, int arriba, int derecha, int abajo) {
        if (objetoVista.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams margenLayout = (ViewGroup.MarginLayoutParams) objetoVista.getLayoutParams();
            margenLayout.setMargins(izquierda, arriba, derecha, abajo);
            objetoVista.requestLayout();
        }
    }

    /**
     * Sobreescribe el botón de atrás para que retroceda la imagen y vuelva al menú principal cuando se está
     * en la imagen inicial de la categoría
     */
    @Override
    public void onBackPressed() {
        if(indice_frase == POSICION_PRIMERA_FRASE && (categoria == PRIMERA_CATEGORIA || categoriaIntent == categoria ))
        {
            super.onBackPressed();
        }
        else
        {
            this.retroceder();
        }
    }

    /**
     * Actualiza el título de la barra de navegación
     */
    private void actualizarTitulo()
    {
        getSupportActionBar().setTitle(this.tituloCategoria);
    }

    /**
     * Configura la barra de herramientas o de navegación
     */
    private void confugirarBarraDeHerramientas()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//Habilita la barra de navegación, la que tiene el menú secundario
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.actualizarTitulo();
    }

    /**
     * Inicializa el menú en la barra de herramientas
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Ejecuta las acciones de las opciones seleccionadas en el menú de la barra de herramientas
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        int idItemSeleccionado = item.getItemId();
        if (idItemSeleccionado == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        if (idItemSeleccionado == R.id.action_credits) {
            /* Los intents son acciones o tareas que puede realizar una aplicación,
             * este intent está indicando que se tiene que iniciar la actividad de créditos (CreditosActivity).
             * Este intent se podría ejecutar desde otra aplicación.
             * Los intents tienen que estar registrados en el archivo manifests/AndroidManifest.xml
             */
            Intent intent = new Intent(this,CreditosActivity.class);
            startActivity(intent);
            return true;
        }

        if (idItemSeleccionado == R.id.action_aclaration) {
            Intent intent = new Intent(this,AclaracionActivity.class);
            startActivity(intent);
            return true;
        }

        if (idItemSeleccionado == R.id.action_help) {
            Intent intent = new Intent(this,AyudaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Establece los esuchadores de los elementos de la interfaz
     */
    private void establecerEscuchadores()
    {
        botonTraducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTraduccion();
            }
        });
        
        botonReproducirAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sonarAudio();
            }
        });

        imagenFrase.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sonarAudio();
            }
        });

        this.establecerEscuchadoresBotones();
        imagenFrase.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                mostrarTraduccion();
                return true;
            }
        });
    }

    private void establecerEscuchadoresBotones(){
        botonSiguiente.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                avanzar();
            }
        });

        botonAtras.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                retroceder();
            }
        });
    }

    /**
     * El método onKeyUp se encarga de ejecutar las diferentes acciones después de que se presiona alguna tecla.
     * En este caso se utiliza para cambiar la categoría cuando se cambia el índice de la frase.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        boolean retorno;
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                int nuevoIndicePalabra = 0;
                try {
                    nuevoIndicePalabra = Integer.parseInt(ingresoDePalabra.getText().toString());
                    if(ValidadorDeEntradas.validarIndicePalabra(nuevoIndicePalabra,cantidad_elementos[categoria-1]))
                    {
                        if(indice_frase != nuevoIndicePalabra) {
                            moverse(nuevoIndicePalabra);
                        }
                    }
                    else
                    {
                        showMessage("El número ingresado no corresponde a ninguna frase");
                    }
                }
                catch(Exception e){
                    showMessage("El valor ingresado no es un número válido");
                }

                retorno = true;
                break;
            default:
                retorno = super.onKeyUp(keyCode, event);
        }
                return retorno;
    }


    /**
     * Método intermediario. Cuando se presiona por largo tiempo el boton se encarga de desplegar
     * el mensaje de la traduccion
     */
    public void mostrarTraduccion()
    {
        showMessage(traduccion);
    }

    /**
     * Inicializa el arreglo cantidad de elementos con la cantidad de frases que tiene cada categoría.
     * Ejemplo:
     * En este caso hay 14 categorías y la primera categoría tiene índice 0 en el arreglo y contiene 13 frases.
     */
    public void inicializarCategorias()
    {
        cantidad_elementos = new int[cantidad_categorias];

        cantidad_elementos[0] = 13;
        cantidad_elementos[1] = 2;
        cantidad_elementos[2] = 29;
        cantidad_elementos[3] = 8;
        cantidad_elementos[4] = 1;
        cantidad_elementos[5] = 8;
        cantidad_elementos[6] = 5;
        cantidad_elementos[7] = 10;
        cantidad_elementos[8] = 31;
        cantidad_elementos[9] = 10;
        cantidad_elementos[10] = 1;
        cantidad_elementos[11] = 7;
        cantidad_elementos[12] = 3;
        cantidad_elementos[13] = 6;
        cantidad_elementos[14] = 6;
    }

    /**
     * Oculta algunos elementos de la interfaz, se llama cuando esta en la primera imagen de una seccion
     */
    private void ocultarElementos()
    {
        ingresoDePalabra.setVisibility(View.GONE);
        textoMaximoPalabra.setVisibility(View.GONE);

        botonReproducirAudio.setVisibility(View.GONE);
        botonTraducir.setVisibility(View.GONE);

        imageViewPais.setVisibility(View.GONE);
        imageViewGenero.setVisibility(View.GONE);

        linearLayoutPais.setVisibility(View.GONE);
        linearLayoutGenero.setVisibility(View.GONE);
        try{
            raya.setVisibility(View.GONE);
        }
        catch (Exception e) {
        }

    }

    /**
     * Muestra elementos, se llama cuando se cambia la primera imagen de la seccion
     */
    private void mostrarElementos()
    {
        ingresoDePalabra.setVisibility(View.VISIBLE);
        textoMaximoPalabra.setVisibility(View.VISIBLE);

        botonReproducirAudio.setVisibility(View.VISIBLE);
        botonTraducir.setVisibility(View.VISIBLE);

        imageViewPais.setVisibility(View.VISIBLE);
        imageViewGenero.setVisibility(View.VISIBLE);

        linearLayoutPais.setVisibility(View.VISIBLE);
        linearLayoutGenero.setVisibility(View.VISIBLE);
        textoPais1.setVisibility(View.VISIBLE);
        textoPais2.setVisibility(View.VISIBLE);

        try{
            raya.setVisibility(View.VISIBLE);
        }
        catch (Exception e) {
        }
    }

    /**
     * Actualiza el nombre de la categoria
     */
    public void setearCategoria()
    {
        textoMaximoPalabra.setText(" / "+ String.valueOf(cantidad_elementos[categoria - 1]));
        tituloCategoria = nombresCategorias[categoria - 1];
        actualizarTitulo();
    }

    /**
     * Oculta el switch de pronuciación en la pantalla del mostrador de frases
     */
    private void ocultarPais()
    {
        imageViewPais.setVisibility(View.GONE);
        textoPais1.setVisibility(View.GONE);
        textoPais2.setVisibility(View.GONE);
    }

    /**
     * Actualiza la imagen.
     */
    public void setearImagen()
    {
        try {
            String nombre_imagen = "c" + categoria + "s" + indice_frase + version_pais;

            if (indice_frase == 0)  //Si es la sección
            {
                nombre_imagen = "c" + categoria + "s" + indice_frase;

                this.ocultarElementos();
            } else {
                this.mostrarElementos();
            }

            id_imagen = getResources().getIdentifier(nombre_imagen, "drawable", getPackageName());

            // Controla el caso donde no se tiene una versión específica de país
            // Se oculta el switch de país
            if(id_imagen == 0){
                this.ocultarPais();
                nombre_imagen = "c" + categoria + "s" + indice_frase;
                id_imagen = getResources().getIdentifier(nombre_imagen,"drawable",getPackageName());
            }

            /**
             * Controla el caso en el que una palabra no tiene versión de Panama
             */
            if (id_imagen == 0 && version_pais.equals(PANAMA)) {
                showMessage("Esta palabra no cuenta con versión de panama");
                nombre_imagen = "c" + categoria + "s" + indice_frase + COSTARICA;
                id_imagen = getResources().getIdentifier(nombre_imagen, "drawable", getPackageName());
            }

            // Zoom functionality
            PhotoView photoView = (PhotoView) findViewById(R.id.imageViewIlustracion);
            photoView.setImageResource(id_imagen);
            //limpia el cache utilizado para cargar la imagen, no quita la imagen de la pantalla
            photoView.destroyDrawingCache();
        }
        catch(Throwable t) { //normalmente si entra aquí es porque el dispositivo se quedó sin memoria
            Runtime.getRuntime().gc();//llama al recolector de basura
            Log.e("Avanzar", "Se intento cargar la imagen pero no se logró");
        }
    }

    /**
     * Actualiza el audio
     * Despliega un mensaje de error en caso de que el audio no exista, pero no tira la aplicación..
     */
    public void setearAudio()
    {
        String nombre_audio = "c" + categoria + "s" + indice_frase + genero + version_pais;
        id_audio = getResources().getIdentifier(nombre_audio,"raw",getPackageName());

        if(id_audio == 0)
        {
            //Si no existe el audio en versión de Panamá, intenta reproducir el audio de la versión
            //de costa rica
            if(id_audio == 0 && version_pais.equals(PANAMA))
            {
                String viejo_nombre = nombre_audio;
                nombre_audio = "c" + categoria + "s" + indice_frase + genero + COSTARICA;
                id_audio = getResources().getIdentifier(nombre_audio,"raw",getPackageName());
                if(id_audio == 0)
                    showMessage("Error: El audio " + viejo_nombre + " no existe");
                else
                {
                    showMessage("Se ha cargado el audio de Costa Rica");
                }
            }

        }
    }

    /**
     * Cambia el audio que se reproduce
     * */
    public void setearTraduccion()
    {
        if(indice_frase != 0)  //No traduce las secciones
        {
            int id_array = getResources().getIdentifier("categoria" + categoria, "array", getPackageName());

            if (id_array != 0) {
                String[] array = getResources().getStringArray(id_array);

                if (array.length > (indice_frase - 1))
                    traduccion = array[indice_frase - 1];
                else
                    traduccion = "No se encuentra disponible la traducción de esta palabra";
            } else
                traduccion = "No se encuentra disponible la traducción de esta categoria";
        }
    }

    /**
     * Asigna la imagen y audio basandose en la palabra y categoría actual
     */
    public void setearRecursos()
    {
        setearCategoria();
        setearAudio();
        setearImagen();
        setearTraduccion();
    }

    /**
     * Cambia el genero de la voz
     * @param switchElement el switch cuyo cambio determina el genero de la voz del audio.
     */
    public void cambioGenero(View switchElement)
    {
        ImageView switch_image = (ImageView) switchElement;
        if(genero.equals(HOMBRE))
        {
            genero = MUJER;
            switch_image.setImageResource(R.drawable.switchm);
        }
        else {
            genero = HOMBRE;
            switch_image.setImageResource(R.drawable.switchh);
        }

        setearAudio();
    }

    /**
     * Alterna de versión de Costa Rica a Panama y viceversa
     * @param switchElement el switch cuyo cambio determina la versión
     */
    public void cambioVersion(View switchElement)
    {
        ImageView switch_image = (ImageView) switchElement;

        if(version_pais.equals(PANAMA))
        {
            version_pais = COSTARICA;
            switch_image.setImageResource(R.drawable.switchcr);
        }
        else
        {
            version_pais = PANAMA;
            switch_image.setImageResource(R.drawable.switchpn);
        }

        setearAudio();
        setearImagen();
    }


    /**
     * Se encarga de hacer sonar el audio de la palabra.
     * Si el audio no existe, no realiza ninguna acción
     */
    public void sonarAudio()
    {
        if(id_audio != 0)
        {
            ReproductorAudio ra = new ReproductorAudio();
            ra.reproducirAudio(getApplicationContext(), id_audio);
        }
    }

    /**
     * Cambia la frase a la que tiene un índice menor, también incluye cambiar: la imagen,
     * el audio y el resto de la configuración de la pantalla.
     */
    public void retroceder()
    {
        //Solamente no retrocede en la palabra 1 de la categoria 1
        if(categoria > 1 || indice_frase > 0)
        {
            indice_frase--;

            //Si se intenta retroceder desde la primer palabra de una categoria
            if(indice_frase < 0) { //
                categoria--;
                //Empieza desde el ultimo elemento de la categoria anterior.
                indice_frase = cantidad_elementos[categoria-1];

            }

            setearRecursos();
            ingresoDePalabra.setHint(String.valueOf(indice_frase));
            ingresoDePalabra.setText(String.valueOf(indice_frase));
        }
        else
        {
            showMessage("Esta es la primer palabra. No se puede retroceder.");
        }
    }

    /**
     * Pone la siguiente frase
     * */
    public void avanzar()
    {
        //Avanza solo si no es la última palabra de la última categoria
        if(indice_frase != cantidad_elementos[categoria-1] || categoria != cantidad_categorias ) {
            indice_frase++;

            //Si avanza desde la ultima palabra de la categoria actual
            if (indice_frase == cantidad_elementos[categoria - 1] + 1) {
                //Se posiciona en la primera palabra de la proxima categoria
                categoria++;
                indice_frase = 0; //La primer palabra sería la sección
            }

            setearRecursos();
            ingresoDePalabra.setHint(String.valueOf(indice_frase));
            ingresoDePalabra.setText(String.valueOf(indice_frase));
        }
        else
        {
            showMessage("Esta es la última palabra del diccionario.");
        }
    }

    /**
     * Se mueve dentro de una categoría la cantidad de palabras que se le ingresa por parámetro
     * @param parametroPosiciones la cantidad de posiciones que se va a mover
     */
    public void moverse(Integer... parametroPosiciones)
    {
        int posicion;
        if(parametroPosiciones == null)
        {
            posicion = 1;
        }
        else
        {
            posicion = parametroPosiciones[0];
        }
        //Avanza solo si no es la última palabra de la última categoria
        if(indice_frase != cantidad_elementos[categoria-1] || categoria != cantidad_categorias )
        {
            indice_frase = posicion;

            //Si avanza desde la ultima palabra de la categoria actual
            if (indice_frase == cantidad_elementos[categoria - 1] + 1) {
                //Se posiciona en la primera palabra de la proxima categoria
                categoria++;
                indice_frase = 0; //La primer palabra sería la sección
            }
            setearRecursos();
            ingresoDePalabra.setHint(String.valueOf(indice_frase));
            ingresoDePalabra.setText(String.valueOf(indice_frase));
        }
        else
        {
            showMessage("Esta es la última palabra del diccionario.");
        }
    }


    /**
     * Despliega un mensaje que se desvanece
     * @param mensaje
     */
    public void showMessage(String mensaje)
    {
        Context contexto = getApplicationContext();
        Toast toast = Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT);
        toast.show();

    }

}
