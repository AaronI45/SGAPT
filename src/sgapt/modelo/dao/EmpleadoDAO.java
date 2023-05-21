/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Empleado;
import sgapt.modelo.pojo.EmpleadoRespuesta;
import sgapt.modelo.pojo.Pedido;
import sgapt.modelo.pojo.PedidoRespuesta;
import sgapt.util.Constantes;

/**
 *
 * @author zS21022065
 */
public class EmpleadoDAO {
     public static EmpleadoRespuesta obtenerInformacionEmpleado() {
        EmpleadoRespuesta respuesta = new EmpleadoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEmpleado, idFarmacia, "
                        + "nombres, ApellidoPaterno, ApellidoMaterno,correo,foto " + 
                        "FROM Usuario " +  
                        "INNER JOIN Empleado " + 
                        "ON Usuario.Empleado_idEmpleado = Empleado.idEmpleado";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Empleado> empleadosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Empleado empleado = new Empleado();
                    empleado.setIdEmpleado(resultado.getInt("empleado"));
                    empleado.setIdFarmacia(resultado.getInt("farmacia"));
                    empleado.setNombre(resultado.getString("nombre"));
                    empleado.setApellidoPaterno(resultado.getString("apellido paterno"));
                    empleado.setApellidoPaterno(resultado.getString("apellido materno"));
                    //  empleado.setUsername(resultado.getString("username"));
                    //   empleado.setPassword(resultado.getString("password"));
                    //empleado.setTipoEmpleado(resultado.getString("tipo de empleado"));
                    empleado.setCorreo(resultado.getString("correo electronico"));
                    empleado.setFoto(resultado.getBytes("fotografia"));
                    empleadosConsulta.add(empleado);
                }
                respuesta.setEmpleados(empleadosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
