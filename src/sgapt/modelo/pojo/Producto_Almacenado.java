package sgapt.modelo.pojo;

public class Producto_Almacenado {
    private int producto_idProducto;
    private int almacen_idAlmacen;
    private int cantidad;
    private int codigoRespuesta;

    public Producto_Almacenado() {
    }

    public Producto_Almacenado(int producto_idProducto, int almacen_idAlmacen, int cantidad, int codigoRespuesta) {
        this.producto_idProducto = producto_idProducto;
        this.almacen_idAlmacen = almacen_idAlmacen;
        this.cantidad = cantidad;
        this.codigoRespuesta = codigoRespuesta;
    }

    public int getProducto_idProducto() {
        return producto_idProducto;
    }

    public void setProducto_idProducto(int producto_idProducto) {
        this.producto_idProducto = producto_idProducto;
    }

    public int getAlmacen_idAlmacen() {
        return almacen_idAlmacen;
    }

    public void setAlmacen_idAlmacen(int almacen_idAlmacen) {
        this.almacen_idAlmacen = almacen_idAlmacen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    @Override
    public String toString() {
        return "Producto_almacenado{" + "producto_idProducto=" + producto_idProducto + ", almacen_idAlmacen=" + almacen_idAlmacen + ", cantidad=" + cantidad + '}';
    }
    
}
