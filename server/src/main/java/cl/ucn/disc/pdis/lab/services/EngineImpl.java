/*
 * Copyright (c) 2020 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.lab.services;

import cl.ucn.disc.pdis.lab.zeroice.model.Engine;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Logger;
import com.zeroc.IceInternal.Ex;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The implementation of {@link cl.ucn.disc.pdis.lab.zeroice.model.Engine}.
 *
 * @author Diego Urrutia-Astorga.
 */
public final class EngineImpl implements Engine {

    /**
     * @see Engine#getDate(Current)
     */
    @Override
    /**
     * Fecha actual durante la consulta del cliente.
     *
     * @return fecha actual.
     */
    public String getDate(Current current) {
        return ZonedDateTime
                .now()
                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }



    @Override
    /**
     * Se devuelve el Digito Verificador del Rut ingresado, en caso de no ser valido
     * el Rut, se devuelve un espacio vacio.
     *
     * @return Numero Verificador de un Rut.
     */
    public String getDigitoVerificador(String rut, Current current) {

        /*
         * posibles casos
         * ["10001112", "1000111-2", "1.000.1112", "1.000.111-2"]
         * ["110001112", "11000111-2", "11.000.1112", "11.000.111-2"]
         *
         * otros casos especiales podrian contemplar simbologias incompletas
         * o fuera de formato, las ue se validaran.
         *
         * tamaños desde 8 hasta 12 caracteres por rut.
         */

        int largoRut = rut.length();
        String rutFormat = "";
        char ultimoChar;

        // deja el rut sin caracteres no decimales, excepto posiblemente el DV, para analizar
        //que este correcto el formato.
        for (int i = 0; i < largoRut; i++) {
            ultimoChar = rut.charAt(i);

            //se limpian los caracteres del cuerpo del rut.
            if (i < largoRut - 1) {

                //se va creando un nuevo rut sin caracteres adicionales como "." o "-".
                if (ultimoChar != '.' && ultimoChar != '-')
                    rutFormat += String.valueOf(ultimoChar);

                //en caso de llegar al numero verificador.
            } else {

                //se pruebea una conversion numerica, puesto que sin el DV solo
                // debe haber numeros.
                try {

                    int validarCuerpo = Integer.parseInt(rutFormat);

                    //de haber un error en el cuerpo del rut se le reconoce como
                    //un rut no valido.
                } catch (Exception e) {

                    return " ";
                }

                //digito verificador.
                String dv = String.valueOf(ultimoChar);

                //validado el cuerpo (se recibio un rut posiblemente valido), se verifica que
                // el DV sea valido.
                for (int j = 0; j < 10; j++) {
                    if (dv.equals(String.valueOf(j)))
                        return dv;
                }

                //de no ser numerico, el DV solo podria ser una K.
                if (dv.equalsIgnoreCase("k")) {
                    return "K";

                    //si el DV no es K ni es numerico, entonces el rut se considera invalido.
                } else {

                    return " ";
                }
            }
        }

        return " ";
    }

}
