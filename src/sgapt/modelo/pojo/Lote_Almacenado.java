package sgapt.modelo.pojo;

public class Lote_Almacenado {
    private int lote_idLote;
    private int almacen_idAlmacen;
    private int cantidad;
    private int codigoRespuesta;

    public Lote_Almacenado() {
    }

    public Lote_Almacenado(int producto_idProducto, int almacen_idAlmacen, int cantidad, int codigoRespuesta) {
        this.lote_idLote = producto_idProducto;
        this.almacen_idAlmacen = almacen_idAlmacen;
        this.cantidad = cantidad;
        this.codigoRespuesta = codigoRespuesta;
    }

    public int getLote_idLote() {
        return lote_idLote;
    }

    public void setLote_idLote(int lote_idLote) {
        this.lote_idLote = lote_idLote;
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
        return "Lote_almacenado{" + "lote_idLote=" + lote_idLote + ", almacen_idAlmacen=" + almacen_idAlmacen + ", cantidad=" + cantidad + '}';
    }
    
}
