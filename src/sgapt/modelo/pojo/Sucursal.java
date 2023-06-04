package sgapt.modelo.pojo;

public class Sucursal {
    
    public static enum Estado{
        MICHOACAN("Michoacán"),
        VERACRUZ("Veracruz"),
        JALISCO("Jalisco");
        
        private String nombreEstado;
        
        private Estado(String nombreEstado){
            this.nombreEstado = nombreEstado;
        }

        public static Estado obtenerEstado(String e){
            if(e.equals("Michoacán")){
                return MICHOACAN;
            }else if(e.equals("Veracruz")){
                return VERACRUZ;
            }else if(e.equals("Jalisco")){
                return JALISCO;
            }else{
                return null;
            }
        }
        
        @Override
        public String toString(){
            return nombreEstado;
        }
    }
    
    private int idSucursal;
    private String estado;
    private String ciudad;
    private String direccion;

    public Sucursal() {
    }

    public Sucursal(int idSucursal, String estado, String ciudad, String direccion) {
        this.idSucursal = idSucursal;
        this.estado = estado;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    @Override
    public String toString() {
        return estado;
    }
        
}
