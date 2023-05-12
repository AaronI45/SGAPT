package sgapt.modelo.pojo;

public class Pedido {
    
    private int idPedido;
    private int proveedor_idProveedor;
    private String nombreProveedor;
    private int farmacia_idFarmacia;
    private String ciudadEntrega;
    private String fechaEntrega;

    public Pedido() {
    }

    public Pedido(int idPedido, int proveedor_idProveedor, String nombreProveedor, int farmacia_idFarmacia, String ciudadEntrega, String fechaEntrega) {
        this.idPedido = idPedido;
        this.proveedor_idProveedor = proveedor_idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.farmacia_idFarmacia = farmacia_idFarmacia;
        this.ciudadEntrega = ciudadEntrega;
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getProveedor_idProveedor() {
        return proveedor_idProveedor;
    }

    public void setProveedor_idProveedor(int proveedor_idProveedor) {
        this.proveedor_idProveedor = proveedor_idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getFarmacia_idFarmacia() {
        return farmacia_idFarmacia;
    }

    public void setFarmacia_idFarmacia(int farmacia_idFarmacia) {
        this.farmacia_idFarmacia = farmacia_idFarmacia;
    }

    public String getCiudadEntrega() {
        return ciudadEntrega;
    }

    public void setCiudadEntrega(String ciudadEntrega) {
        this.ciudadEntrega = ciudadEntrega;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", proveedor_idProveedor=" + proveedor_idProveedor + ", farmacia_idFarmacia=" + farmacia_idFarmacia + ", fechaEntrega=" + fechaEntrega + '}';
    }
    
}
