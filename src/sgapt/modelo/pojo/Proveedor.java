package sgapt.modelo.pojo;

public class Proveedor {
    private String nombre;
    private int telefono;
    private int correo;
    private int direccion;
    private int idInventario;

    public Proveedor() {
    }

    public Proveedor(String nombre, int telefono, int correo, int direccion, int idInventario) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.idInventario = idInventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getCorreo() {
        return correo;
    }

    public void setCorreo(int correo) {
        this.correo = correo;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "nombre=" + nombre + ", telefono=" + telefono + ", correo=" + correo + ", direccion=" + direccion + ", idInventario=" + idInventario + '}';
    }
    
    
}