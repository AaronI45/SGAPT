package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Empleado;
import sgapt.util.ShaConversor;

public class SesionDAO 
{
    public static Empleado verificarUsuarioSesion(String usuario, String password) throws SQLException
    {
        Empleado usuarioVerificado = new Empleado();
        Connection conexion = ConexionBD.abrirConexionBD();
        if (conexion != null)
        {
            try
            {
                String consulta = "SELECT * FROM empleado WHERE username = ? AND password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, ShaConversor.sha256(password));
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) 
                {
                    usuarioVerificado.setIdEmpleado(resultado.getInt("idEmpleado"));
                    usuarioVerificado.setIdFarmacia(resultado.getInt("Farmacia_idFarmacia"));
                    usuarioVerificado.setNombre(resultado.getString("nombres"));
                    usuarioVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuarioVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuarioVerificado.setCorreo(resultado.getString("correoElectronico"));
                    usuarioVerificado.setTipoEmpleado(resultado.getInt("Tipo_Empleado_idTipo_Empleado"));
                    usuarioVerificado.setFoto(resultado.getBytes("fotografia"));
                }
                else
                {
                    usuarioVerificado.setTipoEmpleado(Empleado.NO_ENCONTRADO);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }finally{
                conexion.close();
            }
        }
        return usuarioVerificado;
    }
}