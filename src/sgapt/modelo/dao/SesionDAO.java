
package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Empleado;
import sgapt.util.Constantes;
import sgapt.util.ShaConversor;

public class SesionDAO 
{
    public static Empleado verificarUsuarioSesion(String usuario, String password)
    {
        Empleado usuarioVerificado = new Empleado();
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null)
        {
            try
            {
                String consulta = "SELECT * FROM usuario WHERE username = ? AND password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, ShaConversor.sha256(password));
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) 
                {
                    usuarioVerificado.setIdEmpleado(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombres"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setTipoEmpleado(resultado.getString("tipoUsuario"));
                    usuarioVerificado.setCorreo(resultado.getString("correoElectronico"));
                    usuarioVerificado.setFoto(resultado.getBytes("fotografia"));
                    usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                }
                conexion.close();
            } catch (SQLException ex) {
                usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            usuarioVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return usuarioVerificado;
    }
}