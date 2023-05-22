package sgapt.modelo.pojo;

public class Pedido {
    
    private int idPedido;
    private String nombreProveedor;
    private String direccionEntrega;
    private String fechaPedido;
    private String fechaEnvio;
    private String fechaEntrega;
    private String estadoRastreo;
    private String numPedido;
    private double precioProductos;
    private double precioEnvio;
    private double montoTotal;
    private int idProveedor;
    private int idAlmacen;

    public Pedido() {
    }

    public Pedido(int idPedido, String nombreProveedor, String direccionEntrega, String fechaPedido, String fechaEnvio, String fechaEntrega, String estadoRastreo, String numPedido, double precioProductos, double precioEnvio, double montoTotal, int idProveedor, int idAlmacen) {
        this.idPedido = idPedido;
        this.nombreProveedor = nombreProveedor;
        this.direccionEntrega = direccionEntrega;
        this.fechaPedido = fechaPedido;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntrega = fechaEntrega;
        this.estadoRastreo = estadoRastreo;
        this.numPedido = numPedido;
        this.precioProductos = precioProductos;
        this.precioEnvio = precioEnvio;
        this.montoTotal = montoTotal;
        this.idProveedor = idProveedor;
        this.idAlmacen = idAlmacen;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstadoRastreo() {
        return estadoRastreo;
    }

    public void setEstadoRastreo(String estadoRastreo) {
        this.estadoRastreo = estadoRastreo;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public double getPrecioProductos() {
        return precioProductos;
    }

    public void setPrecioProductos(double precioProductos) {
        this.precioProductos = precioProductos;
    }

    public double getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(double precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", nombreProveedor=" + nombreProveedor + ", direccionEntrega=" + direccionEntrega + ", fechaPedido=" + fechaPedido + ", fechaEnvio=" + fechaEnvio + ", fechaEntrega=" + fechaEntrega + ", estadoRastreo=" + estadoRastreo + ", numPedido=" + numPedido + ", precioProductos=" + precioProductos + ", precioEnvio=" + precioEnvio + ", montoTotal=" + montoTotal + ", idProveedor=" + idProveedor + ", idAlmacen=" + idAlmacen + '}';
    }

}
