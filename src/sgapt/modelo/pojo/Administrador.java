package sgapt.modelo.pojo;

import java.util.Date;

public class Administrador extends Persona {

    public Administrador() {
    }

    public Administrador(String nombre, String apellidoPaterno, String apellidoMaterno, int idPersona, Date fechaNacimiento, String correo, String direccion, byte[] foto) {
        super(nombre, apellidoPaterno, apellidoMaterno, idPersona, fechaNacimiento, correo, direccion, foto);
    }

    @Override
    public String toString() {
        return "Administrador{" + super.toString();
    }
    
}
