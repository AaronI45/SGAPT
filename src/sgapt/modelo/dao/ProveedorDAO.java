package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Proveedor;
import sgapt.modelo.pojo.ProveedorRespuesta;
import sgapt.util.Constantes;

public class ProveedorDAO {
    
    public static ProveedorRespuesta obtenerInformacionProveedor() {
        ProveedorRespuesta respuesta = new ProveedorRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idProveedor, estado, ciudad, direccion, nombre " + 
                        "FROM proveedor";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Proveedor> proveedoresConsulta = new ArrayList();
                while (resultado.next())
                {
                    
                    Proveedor proveedor = new Proveedor();
                    proveedor.setIdProveedor(resultado.getInt("idProveedor"));
                    proveedor.setEstado(resultado.getString("estado"));
                    proveedor.setCiudad(resultado.getString("ciudad"));
                    proveedor.setDireccion(resultado.getString("direccion"));
                    proveedor.setNombre(resultado.getString("nombre"));
                    proveedoresConsulta.add(proveedor);
                }
                respuesta.setProveedores(proveedoresConsulta);
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
