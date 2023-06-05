package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Empleado;
import sgapt.modelo.pojo.EmpleadoRespuesta;
import sgapt.util.Constantes;

public class EmpleadoDAO {
     public static EmpleadoRespuesta obtenerInformacionEmpleado() {
        EmpleadoRespuesta respuesta = new EmpleadoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT `usuario`.*, `empleado`.*, `empleados`.*, `farmacia`.*\n" +
                        "FROM `usuario` \n" +
                        "LEFT JOIN `empleado` ON `empleado`.`Usuario_idUsuario` = `usuario`.`idUsuario` \n" +
                        "LEFT JOIN `empleados` ON `empleado`.`Empleados_idEmpleados` = `empleados`.`idEmpleados` \n" +
                        "LEFT JOIN `farmacia` ON `farmacia`.`Empleados_idEmpleados` = `empleados`.`idEmpleados` " + 
                        "WHERE usuario.idUsuario = empleado.Usuario_idUsuario";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Empleado> empleadosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Empleado empleado = new Empleado();
                    empleado.setIdEmpleado(resultado.getInt("idUsuario"));
                    empleado.setIdFarmacia(resultado.getInt("idFarmacia"));
                    empleado.setNombre(resultado.getString("nombres"));
                    empleado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    empleado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    empleado.setCorreo(resultado.getString("correoElectronico"));
                    empleado.setDireccion(resultado.getString("estado"));
                    empleado.setTipoEmpleado(resultado.getString("tipoUsuario"));
                    empleado.setNumeroTelefonico(resultado.getString("numeroTelefonico"));
                    empleado.setFoto(resultado.getBytes("foto"));
                    empleadosConsulta.add(empleado);
                }
                respuesta.setEmpleados(empleadosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
     
    public static EmpleadoRespuesta agregarEmpleado(Empleado nuevoEmpleado){
        EmpleadoRespuesta empleadoRespuesta = new EmpleadoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "INSERT INTO usuario (nombres, apellidoPaterno, apellidoMaterno, "
                        + "correoElectronico, numeroTelefonico, tipoUsuario, foto) "
                        + "VALUES (?,?,?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta, 
                        Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setString(1, nuevoEmpleado.getNombre());
                prepararSentencia.setString(2, nuevoEmpleado.getApellidoPaterno());
                prepararSentencia.setString(3, nuevoEmpleado.getApellidoMaterno());
                prepararSentencia.setString(4, nuevoEmpleado.getCorreo());
                prepararSentencia.setString(5, nuevoEmpleado.getNumeroTelefonico());
                prepararSentencia.setString(6, nuevoEmpleado.getTipoEmpleado());
                prepararSentencia.setBytes(7, nuevoEmpleado.getFoto());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas == 1) {
                    empleadoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                    ResultSet resultadoLlave = prepararSentencia.getGeneratedKeys();
                    if (resultadoLlave.next()) {
                        empleadoRespuesta.setIdEmpleado(resultadoLlave.getInt(1));
                    }
                }
                else
                    empleadoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }catch(SQLException e){
                empleadoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            empleadoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return empleadoRespuesta;
    }
    
    public static int modificarEmpleado(Empleado empleadoEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE usuario SET nombres = ?, apellidoPaterno = ?, "+
                        "apellidoMaterno = ?, correoElectronico = ?, "+
                        "numeroTelefonico = ?, tipoUsuario = ?, foto = ? WHERE idUsuario = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, empleadoEdicion.getNombre());
                prepararSentencia.setString(2, empleadoEdicion.getApellidoPaterno());
                prepararSentencia.setString(3, empleadoEdicion.getApellidoMaterno());
                prepararSentencia.setString(4, empleadoEdicion.getCorreo());
                prepararSentencia.setString(5, empleadoEdicion.getNumeroTelefonico());
                prepararSentencia.setString(6, empleadoEdicion.getTipoEmpleado());
                prepararSentencia.setBytes(7, empleadoEdicion.getFoto());
                prepararSentencia.setInt(8, empleadoEdicion.getIdEmpleado());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException e){
                 respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
             respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
