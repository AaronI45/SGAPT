package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Usuario;
import sgapt.util.Constantes;

public class SesionDAO 
{
    public static Usuario verificarUsuarioSesion(String usuario, String password)
    {
        Usuario usuarioVerificado = new Usuario();
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null)
        {
            try
            {
                String consulta = "SELECT * FROM usuario WHERE username = ? AND password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                usuarioVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) 
                {
                    usuarioVerificado.setIdUsuario(resultado.getInt("idUsuario"));
                    usuarioVerificado.setNombre(resultado.getString("nombre"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setUsername(resultado.getString("username"));
                    usuarioVerificado.setPassword(resultado.getString("password"));
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