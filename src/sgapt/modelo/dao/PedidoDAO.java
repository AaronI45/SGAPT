package sgapt.modelo.dao;

import sgapt.modelo.pojo.PedidoRespuesta;
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
                String consulta = "SELECT idPedido, Proveedor.nombre AS nombreProveedor, "
                        + "direccionEntrega, fechaPedido, fechaEnvio, fechaEntrega, " + 
                        "estadoRastreo, montoTotal " + 
                        "FROM Pedido " +  
                        "INNER JOIN Proveedor " + 
                        "ON Pedido.Proveedor_idProveedor = Proveedor.idProveedor";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Pedido> pedidosConsulta = new ArrayList();
                while (resultado.next())
                {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(resultado.getInt("idPedido"));
                    pedido.setNombreProveedor(resultado.getString("nombreProveedor"));
                    pedido.setDireccionEntrega(resultado.getString("direccionEntrega"));
                    pedido.setFechaPedido(resultado.getString("fechaPedido"));
                    pedido.setFechaEnvio(resultado.getString("fechaEnvio"));
                    pedido.setFechaEntrega(resultado.getString("fechaEntrega"));
                    pedido.setEstadoRastreo(resultado.getString("estadoRastreo"));
                    pedido.setMontoTotal(resultado.getDouble("montoTotal"));
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
