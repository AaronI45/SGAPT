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
                String consulta = "SELECT lote.numeroDeLote AS NumeroLote, " + 
                        "producto.nombre AS nombreProducto, " + 
                        "tipo_producto.tipo AS tipoProducto, " + 
                        "lote.cantidad AS cantidad, " + 
                        "producto.precio AS precio " + 
                        "FROM Pedido " + 
                        "INNER JOIN Lote ON idPedido = lote.Pedido_idPedido " +
                        "INNER JOIN Producto ON lote.Producto_idProducto = Producto.idProducto " + 
                        "INNER JOIN tipo_producto ON Producto.tipo_producto_idTipo_Producto = " + 
                        "tipo_producto.idTipo_producto " + 
                        "WHERE idPedido = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idPedido);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Lote> lotesConsulta = new ArrayList();
                while (resultado.next())
                {
                    Lote lote = new Lote();
                    lote.setNumeroLote(resultado.getString("numeroLote"));
                    lote.setNombreProducto(resultado.getString("nombreProducto"));
                    lote.setTipoProducto(resultado.getString("tipoProducto"));
                    lote.setCantidad(resultado.getInt("cantidad"));
                    lote.setPrecio(resultado.getDouble("precio"));
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
