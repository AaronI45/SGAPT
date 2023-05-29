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
                String consulta = "SELECT `usuario`.*, `empleado`.*, `empleados`.*, `farmacia`.*\n" +
                        "FROM `usuario` \n" +
                        "LEFT JOIN `empleado` ON `empleado`.`Usuario_idUsuario` = `usuario`.`idUsuario` \n" +
                        "LEFT JOIN `empleados` ON `empleado`.`Empleados_idEmpleados` = `empleados`.`idEmpleados` \n" +
                        "LEFT JOIN `farmacia` ON `farmacia`.`Empleados_idEmpleados` = `empleados`.`idEmpleados`";
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
}
