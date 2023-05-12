package sgapt.modelo.pojo;

public class Lote {
    
    private String numeroLote;
    private String nombreProducto;
    private String tipoProducto;
    private int cantidad;
    private double precio;

    public Lote() {
    }

    public Lote(String numeroLote, String nombreProducto, String tipoProducto, int cantidad, double precio) {
        this.numeroLote = numeroLote;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Lote{" + "numeroLote=" + numeroLote + ", nombreProducto=" + nombreProducto + ", tipoProducto=" + tipoProducto + ", cantidad=" + cantidad + ", precio=" + precio + '}';
    }
    
}
