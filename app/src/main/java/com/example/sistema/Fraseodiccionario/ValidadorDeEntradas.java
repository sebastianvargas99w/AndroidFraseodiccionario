package com.example.sistema.Fraseodiccionario;
/**
 * Clase estática para validar entrada de datos
 */
public final class ValidadorDeEntradas {
    private  ValidadorDeEntradas(){}

    /**
     * Valida que la entrada sea un número entero menor a "maximoSeccion"
     */
    public static boolean validarIndicePalabra(int datoIngresado, int maximoPalabra) {
        boolean datoValido;
        if(datoIngresado >= 0 && datoIngresado <= maximoPalabra)
        {
            datoValido = true;
        }
        else {
            datoValido = false;
        }
        return datoValido;
    }
}
