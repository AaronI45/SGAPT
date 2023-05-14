package sgapt.modelo.pojo;

public class Pedido {
    
    private int idPedido;
    private String nombreProveedor;
    private String direccionEntrega;
    private String fechaPedido;
    private String fechaEnvio;
    private String fechaEntrega;
    private String estadoRastreo;
    private double montoTotal;

    public Pedido() {
    }

    public Pedido(int idPedido, String nombreProveedor, String direccionEntrega, String fechaPedido, String fechaEnvio, String fechaEntrega, String estadoRastreo, double montoTotal) {
        this.idPedido = idPedido;
        this.nombreProveedor = nombreProveedor;
        this.direccionEntrega = direccionEntrega;
        this.fechaPedido = fechaPedido;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntrega = fechaEntrega;
        this.estadoRastreo = estadoRastreo;
        this.montoTotal = montoTotal;
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

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }    

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", nombreProveedor=" + nombreProveedor + ", direccionEntrega=" + direccionEntrega + ", fechaPedido=" + fechaPedido + ", fechaEnvio=" + fechaEnvio + ", fechaEntrega=" + fechaEntrega + ", estadoRastreo=" + estadoRastreo + ", montoTotal=" + montoTotal + '}';
    }    
    
}
