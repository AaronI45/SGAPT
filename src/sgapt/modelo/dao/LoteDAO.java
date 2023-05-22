package sgapt.modelo.dao;

import sgapt.modelo.pojo.LoteRespuesta;
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
                String consulta = "SELECT idLote, numeroDeLote, producto.nombre, producto.tipoProducto, " + 
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
                    lote.setIdLote(resultado.getInt("idLote"));
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
    
    public static LoteRespuesta obtenerInformacionLotePorProveedor(int idProveedor) {
        LoteRespuesta respuesta = new LoteRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idLote, numeroDeLote, producto.nombre, producto.tipoProducto, " + 
                        "fechaDeCaducidad, cantidad, (lote.cantidad * producto.precio) " + 
                        "AS 'precioLote' " + 
                        "FROM lote " + 
                        "INNER JOIN producto " + 
                        "ON lote.Producto_idProducto = producto.idProducto " + 
                        "WHERE lote.Proveedor_idProveedor = ? AND lote.Pedido_idPedido IS null";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProveedor);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Lote> lotesConsulta = new ArrayList();
                while (resultado.next())
                {
                    Lote lote = new Lote();
                    lote.setIdLote(resultado.getInt("idLote"));
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
    
    public static int desenlazarLotesDePedido(int idPedido) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE lote SET Pedido_idPedido = null WHERE Pedido_idPedido = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPedido);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas != 0)
                    respuesta = Constantes.OPERACION_EXITOSA;
                else
                    respuesta = Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int desenlazarLoteDePedido(int idLote, int idPedido) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE lote set Pedido_idPedido = null " + 
                        "WHERE idLote = ? AND Pedido_idPedido = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idLote);
                prepararSentencia.setInt(2, idPedido);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int enlazarLoteAPedido(int idLote, int idPedido) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE lote set Pedido_idPedido = ? WHERE idLote = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPedido);
                prepararSentencia.setInt(2, idLote);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}
