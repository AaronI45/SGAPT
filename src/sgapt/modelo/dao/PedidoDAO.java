package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Pedido;
import sgapt.util.Constantes;

public class PedidoDAO {
    
    public static PedidoRespuesta obtenerInformacionPedido() {
        PedidoRespuesta respuesta = new PedidoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idPedido, " + 
                        "proveedor.idProveedor AS proveedor_idProveedor, " + 
                        "proveedor.nombre AS nombreProveedor, " + 
                        "farmacia.idFarmacia AS farmacia_idFarmacia, " + 
                        "farmacia.ciudad AS ciudadEntrega, " + 
                        "fechaEntrega " +
                        "FROM Pedido " + 
                        "INNER JOIN Proveedor ON Pedido.Proveedor_idProveedor = Proveedor.idProveedor " + 
                        "INNER JOIN Farmacia ON Pedido.Farmacia_idFarmacia = Farmacia.idFarmacia"; 
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Pedido> pedidosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(resultado.getInt("idPedido"));
                    pedido.setProveedor_idProveedor(resultado.getInt("proveedor_idProveedor"));
                    pedido.setNombreProveedor(resultado.getString("nombreProveedor"));
                    pedido.setFarmacia_idFarmacia(resultado.getInt("farmacia_idFarmacia"));
                    pedido.setCiudadEntrega(resultado.getString("ciudadEntrega"));
                    pedido.setFechaEntrega(resultado.getString("fechaEntrega"));
                    pedidosConsulta.add(pedido);
                }
                respuesta.setPedidos(pedidosConsulta);
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
