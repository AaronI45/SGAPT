package sgapt.modelo.pojo;

public class Empleado {

    private int idEmpleado;
    private int Farmacia_idFarmacia;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronico;
    private int esAdministrador;
    private String username;
    private String password;
    private int codigoRespuesta;

    public Empleado() {
    }

    public Empleado(int idEmpleado, int Farmacia_idFarmacia, String nombres, String apellidoPaterno, String apellidoMaterno, String correoElectronico, int esAdministrador, String username, String password, int codigoRespuesta) {
        this.idEmpleado = idEmpleado;
        this.Farmacia_idFarmacia = Farmacia_idFarmacia;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronico = correoElectronico;
        this.esAdministrador = esAdministrador;
        this.username = username;
        this.password = password;
        this.codigoRespuesta = codigoRespuesta;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getFarmacia_idFarmacia() {
        return Farmacia_idFarmacia;
    }

    public void setFarmacia_idFarmacia(int Farmacia_idFarmacia) {
        this.Farmacia_idFarmacia = Farmacia_idFarmacia;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(int esAdministrador) {
        this.esAdministrador = esAdministrador;
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

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    
    
    @Override
    public String toString() {
        return nombres+" "+apellidoPaterno+" "+apellidoMaterno;
    }
    
}
