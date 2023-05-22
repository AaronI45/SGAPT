package sgapt.modelo.pojo;

import java.util.Date;
import javafx.scene.image.ImageView;

public class Producto {
    
    public static enum TipoDeProducto{
        SALUD("artículo para salud"),
        HIGIENE("artículo para higiene"),
        MEDICAMENTO("medicamento");
        
        private TipoDeProducto(String tipo){
            this.tipo = tipo;
        }
                
        private String tipo;

        @Override
        public String toString() {
            return tipo;
        }
    }
    
    private int idProducto;
    private String nombre;
    private String tipoProducto;
    private String disponibilidad;
    private int cantidad;
    private Sucursal sucursal;
    private boolean requiereReceta;
    private double precio;
    private int idLote;
    private String numeroLote;
    private Date fechaCaducidad;
    private byte[] foto;
    private ImageView visualizacionFoto;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String tipoProducto, String disponibilidad, int cantidad, Sucursal sucursal, boolean requiereReceta, double precio, int idLote, String numeroLote, Date fechaCaducidad, byte[] foto, ImageView visualizacionFoto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
        this.disponibilidad = disponibilidad;
        this.cantidad = cantidad;
        this.sucursal = sucursal;
        this.requiereReceta = requiereReceta;
        this.precio = precio;
        this.idLote = idLote;
        this.numeroLote = numeroLote;
        this.fechaCaducidad = fechaCaducidad;
        this.foto = foto;
        this.visualizacionFoto = visualizacionFoto;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public ImageView getVisualizacionFoto() {
        return visualizacionFoto;
    }

    public void setVisualizacionFoto(ImageView visualizacionFoto) {
        this.visualizacionFoto = visualizacionFoto;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }
    
    @Override
    public String toString() {
        return nombre;
    }

    
}
