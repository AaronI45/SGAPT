package sgapt.modelo.pojo;

public class Sucursal {
    private String nombre;
    private String ciudad;
    private int idInventario;

    public Sucursal() {
    }

    public Sucursal(String nombre, String ciudad, int idInventario) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.idInventario = idInventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    @Override
    public String toString() {
        return "Sucursal{" + "nombre=" + nombre + ", ciudad=" + ciudad + ", idInventario=" + idInventario + '}';
    }
    
    
}
