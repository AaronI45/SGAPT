package sgapt.modelo.pojo;

import java.util.Date;

public class Producto {
    
    private int idProducto;
    private String nombre;
    private String tipoProducto;
    private String disponibilidad;
    private int cantidad;
    private Sucursal sucursal;
    private boolean requiereReceta;
    private int precio;
    private int numeroLote;
    private Date fechaCaducidad;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String tipoProducto, String disponibilidad, int cantidad, Sucursal sucursal, boolean requiereReceta, int precio, int numeroLote, Date fechaCaducidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.disponibilidad = disponibilidad;
        this.cantidad = cantidad;
        this.sucursal = sucursal;
        this.requiereReceta = requiereReceta;
        this.precio = precio;
        this.numeroLote = numeroLote;
        this.fechaCaducidad = fechaCaducidad;
    }
    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public boolean isRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(int numeroLote) {
        this.numeroLote = numeroLote;
    }
    
    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", tipoProducto=" + tipoProducto + ", disponibilidad=" + disponibilidad + ", cantidad=" + cantidad + ", sucursal=" + sucursal + ", requiereReceta=" + requiereReceta + ", fechaCaducidad=" + fechaCaducidad + '}';
    }

    
}
