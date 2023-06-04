package sgapt.modelo.pojo;

import java.util.Date;
import javafx.scene.image.ImageView;
import javax.management.modelmbean.RequiredModelMBean;

public class Producto {
    
    public static enum TipoDeProducto{
        SALUD("articulo para salud"),
        HIGIENE("articulo para higiene"),
        MEDICAMENTO("medicamento");
        
        private TipoDeProducto(String tipo){
            this.tipo = tipo;
        }
                
        private String tipo;

        public static TipoDeProducto t(String valor){
            if(valor.equals("articulo para salud")){
                return SALUD;
            }else if(valor.equals("articulo para higiene")){
                return HIGIENE;
            }else if(valor.equals("medicamento")){
                return MEDICAMENTO;
            }else{
                return null;
            }
        }
        
        @Override
        public String toString() {
            return tipo;
        }
    }
    
    public static enum RequiereReceta{
        SI(true),
        NO(false);
        
        private RequiereReceta(boolean requiereRec){
            this.requiereRec = requiereRec;
        }
        
        private boolean requiereRec;
        
        public static boolean getRequiere (RequiereReceta req){
            return req.requiereRec;
        }
        
        public static RequiereReceta requiere(boolean r){
            if (r){
                return SI;
            }else{
                return NO;
            }
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

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String tipoProducto, String disponibilidad, int cantidad, Sucursal sucursal, boolean requiereReceta, double precio, int idLote, String numeroLote, Date fechaCaducidad, byte[] foto) {
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
