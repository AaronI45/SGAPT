package sgapt.modelo.pojo;

public class Lote {
    
    private String numeroDeLote;
    private String nombre;
    private String tipoProducto;
    private String fechaDeCaducidad;
    private int cantidad;
    private double precioLote;

    public Lote() {
    }

    public Lote(String numeroDeLote, String nombre, String tipoProducto, String fechaDeCaducidad, int cantidad, double precioLote) {
        this.numeroDeLote = numeroDeLote;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.cantidad = cantidad;
        this.precioLote = precioLote;
    }

    public String getNumeroDeLote() {
        return numeroDeLote;
    }

    public void setNumeroDeLote(String numeroDeLote) {
        this.numeroDeLote = numeroDeLote;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getFechaDeCaducidad() {
        return fechaDeCaducidad;
    }

    public void setFechaDeCaducidad(String fechaDeCaducidad) {
        this.fechaDeCaducidad = fechaDeCaducidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioLote() {
        return precioLote;
    }

    public void setPrecioLote(double precioLote) {
        this.precioLote = precioLote;
    }

    @Override
    public String toString() {
        return "Lote{" + "numeroDeLote=" + numeroDeLote + ", nombre=" + nombre + ", tipoProducto=" + tipoProducto + ", fechaDeCaducidad=" + fechaDeCaducidad + ", cantidad=" + cantidad + ", precioLote=" + precioLote + '}';
    }
    
}
