
package sgapt.modelo.pojo;

public class Sucursal {
    private String estado;
    private String ciudad;
    private String direccion;
    private int idInventario;

    public Sucursal() {
    }

    public Sucursal(String estado, String ciudad, String direccion, int idInventario) {
        this.estado = estado;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.idInventario = idInventario;
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

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    @Override
    public String toString() {
        return estado + " " + ciudad;
    }
    
    
}