package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Lote;
import sgapt.util.Constantes;

public class LoteDAO {
    
    public static LoteRespuesta obtenerInformacionLotePorPedido(int idPedido) {
        LoteRespuesta respuesta = new LoteRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT numeroDeLote, producto.nombre, producto.tipoProducto, " + 
                        "fechaDeCaducidad, cantidad, (lote.cantidad * producto.precio) " + 
                        "AS 'precioLote' " + 
                        "FROM lote " + 
                        "INNER JOIN producto " + 
                        "ON lote.Producto_idProducto = producto.idProducto " + 
                        "WHERE lote.Pedido_idPedido = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idPedido);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Lote> lotesConsulta = new ArrayList();
                while (resultado.next())
                {
                    Lote lote = new Lote();
                    lote.setNumeroDeLote(resultado.getString("numeroDeLote"));
                    lote.setNombre(resultado.getString("nombre"));
                    lote.setTipoProducto(resultado.getString("tipoProducto"));
                    lote.setFechaDeCaducidad(resultado.getString("fechaDeCaducidad"));
                    lote.setCantidad(resultado.getInt("cantidad"));
                    lote.setPrecioLote(resultado.getDouble("precioLote"));
                    lotesConsulta.add(lote);
                }
                respuesta.setLotes(lotesConsulta);
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
