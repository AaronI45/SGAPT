package sgapt.modelo.pojo;

import java.util.Date;

public class Empleado extends Persona {

    public Empleado() {
    }

    public Empleado(String nombre, String apellidoPaterno, String apellidoMaterno, int idPersona, Date fechaNacimiento, String correo, String direccion, byte[] foto) {
        super(nombre, apellidoPaterno, apellidoMaterno, idPersona, fechaNacimiento, correo, direccion, foto);
    }

    @Override
    public String toString() {
        return "Empleado{" + super.toString();
    }
    
}
