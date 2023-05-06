package sgapt.modelo.pojo;

public class Producto_Inventario {
    private int idInventario;
    private int idProducto;
    private int cantidad;

    public Producto_Inventario() {
    }

    public Producto_Inventario(int idInventario, int idProducto, int cantidad) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto_Inventario{" + "idInventario=" + idInventario + ", idProducto=" + idProducto + ", cantidad=" + cantidad + '}';
    }
    
}
