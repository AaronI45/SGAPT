package sgapt.modelo.pojo;

public class Empleado {
    
    private int idEmpleado;
    private int idFarmacia;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String username;
    private String password;
    private String correo;
    private String numeroTelefonico;
    private String direccion;
    private byte[] foto;
    private String tipoEmpleado;
    private int codigoRespuesta;
    
    public Empleado() {
        
    }

    public Empleado(int idEmpleado, int idFarmacia, String nombre, String apellidoPaterno, String apellidoMaterno, String username, String password, String correo, String numeroTelefonico, String direccion, byte[] foto, String tipoEmpleado, int codigoRespuesta) {
        this.idEmpleado = idEmpleado;
        this.idFarmacia = idFarmacia;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.numeroTelefonico = numeroTelefonico;
        this.direccion = direccion;
        this.foto = foto;
        this.tipoEmpleado = tipoEmpleado;
        this.codigoRespuesta = codigoRespuesta;
    }
    
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    @Override
    public String toString() {
        return nombre+" "+apellidoPaterno+" "+apellidoMaterno;
    }


    @Override
    public boolean equals(Object obj) {
        boolean sonIguales = false;
        if (this == obj){
            sonIguales = true;
        }
        if (obj != null & obj instanceof Empleado){
            Empleado otro = (Empleado) obj;
            sonIguales = this.getIdEmpleado() == otro.getIdEmpleado();
        }
        return sonIguales;
    }
    
}