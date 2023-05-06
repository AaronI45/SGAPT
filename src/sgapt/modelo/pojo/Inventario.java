package sgapt.modelo.pojo;

public class Inventario {
    private int idInventario;
    private int cantidadProductos;

    public Inventario() {
    }

    public Inventario(int idInventario, int cantidadProductos) {
        this.idInventario = idInventario;
        this.cantidadProductos = cantidadProductos;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    @Override
    public String toString() {
        return "Inventario{" + "idInventario=" + idInventario + ", cantidadProductos=" + cantidadProductos + '}';
    }
    
    
}
