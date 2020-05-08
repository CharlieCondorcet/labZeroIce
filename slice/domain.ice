// Custom package mapping
["java:package:cl.ucn.disc.pdis.lab.zeroice"]
module model
{
    // The API
    interface Engine
    {
        /**
        *
        */
        string getDate();

        /**
        * Verificacion de un RUT, se permite el uso de guion.
        * @return the digito verificador del rut, vacio si el rut es incorrecto.
        */
        string getDigitoVerificador(string rut);
    }

}
