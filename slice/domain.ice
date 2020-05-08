// Custom package mapping
["java:package:cl.ucn.disc.pdis.lab.zeroice"]
module model
{
    // The API
    interface Engine
    {
        /**
        * Se le devuelve la fecha actual al Cliente.
        *
        * @return la fecha actual al momento del request.
        */
        string getDate();

        /**
        * Verificacion de un RUT, se permite el uso de guion y puntos.
        *
        * @return el digito verificador del Rut, vacio si el rut es incorrecto.
        */
        string getDigitoVerificador(string rut);
    }

}
