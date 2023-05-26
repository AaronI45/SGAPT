package sgapt.modelo.pojo;

public class Proveedor {
    private int idProveedor;
    private String estado;
    private String ciudad;
    private String direccion;
    private String nombre;

    public Proveedor() {
    }

    public Proveedor(int idProveedor, String estado, String ciudad, String direccion, String nombre) {
        this.idProveedor = idProveedor;
        this.estado = estado;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.nombre = nombre;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

}