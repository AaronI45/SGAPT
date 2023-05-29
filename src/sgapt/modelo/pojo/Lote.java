package sgapt.modelo.pojo;

public class Lote {
    private int idLote;
    private String numeroDeLote;
    private String nombre;
    private String tipoProducto;
    private String fechaDeCaducidad;
    private int cantidad;
    private double precioLote;
    private int cantidadLotes;

    public Lote() {
    }

    public Lote(int idLote, String numeroDeLote, String nombre, String tipoProducto, String fechaDeCaducidad, int cantidad, double precioLote, int cantidadLotes) {
        this.idLote = idLote;
        this.numeroDeLote = numeroDeLote;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.fechaDeCaducidad = fechaDeCaducidad;
        this.cantidad = cantidad;
        this.precioLote = precioLote;
        this.cantidadLotes = cantidadLotes;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
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

    public int getCantidadLotes() {
        return cantidadLotes;
    }

    public void setCantidadLotes(int cantidadLotes) {
        this.cantidadLotes = cantidadLotes;
    }

    @Override
    public String toString() {
        return "Lote{" + "idLote=" + idLote + ", numeroDeLote=" + numeroDeLote + ", nombre=" + nombre + ", tipoProducto=" + tipoProducto + ", fechaDeCaducidad=" + fechaDeCaducidad + ", cantidad=" + cantidad + ", precioLote=" + precioLote + ", cantidadLotes=" + cantidadLotes + '}';
    }

}
