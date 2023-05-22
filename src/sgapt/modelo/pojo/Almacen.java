package sgapt.modelo.pojo;

public class Almacen {
    private int idAlmacen;
    private int idFarmacia;
    private String estado;
    private String ciudad;
    private String direccion;

    public Almacen() {
    }

    public Almacen(int idAlmacen, int idFarmacia, String estado, String ciudad, String direccion) {
        this.idAlmacen = idAlmacen;
        this.idFarmacia = idFarmacia;
        this.estado = estado;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
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

    @Override
    public String toString() {
        return estado + "-" + ciudad;
    }
    
    
}
