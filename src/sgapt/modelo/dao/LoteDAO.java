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
                String consulta = "SELECT DISTINCT idLote, numeroDeLote, producto.nombre, producto.tipoProducto, " + 
                        "fechaDeCaducidad, cantidad, (lote.cantidad * producto.precio) " + 
                        "AS 'precioLote', lote_pedido.cantidadLotes " + 
                        "FROM lote " + 
                        "INNER JOIN producto " + 
                        "ON lote.Producto_idProducto = producto.idProducto " + 
                        "INNER JOIN Lote_Pedido " + 
                        "ON Lote_Pedido.Lote_idLote = Lote.idLote " + 
                        "WHERE Lote_Pedido.Pedido_idPedido = ?";
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
                    lote.setCantidadLotes(resultado.getInt("cantidadLotes"));
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
                String consulta = "SELECT DISTINCT idLote, numeroDeLote, producto.nombre, producto.tipoProducto, " + 
                        "fechaDeCaducidad, cantidad, (lote.cantidad * producto.precio) " + 
                        "AS 'precioLote', lote.cantidadLotes " + 
                        "FROM lote " + 
                        "INNER JOIN producto " + 
                        "ON lote.Producto_idProducto = producto.idProducto " + 
                        "INNER JOIN Lote_Pedido " + 
                        "ON Lote_Pedido.Lote_idLote = Lote.idLote " + 
                        "WHERE lote.Proveedor_idProveedor = ? " + 
                        "AND lote.cantidadLotes > 0";
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
                    lote.setCantidadLotes(resultado.getInt("cantidadLotes"));
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
    
    public static int modificarLote(Lote lote){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE Lote SET " + 
                       "numeroDeLote = ?, cantidad = ?, " + 
                       "fechaDeCaducidad = ?, cantidadLotes = ? " + 
                       "WHERE idLote = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, lote.getNumeroDeLote());
                prepararSentencia.setInt(2, lote.getCantidad());
                prepararSentencia.setString(3, lote.getFechaDeCaducidad());
                prepararSentencia.setInt(4, lote.getCantidadLotes());
                prepararSentencia.setInt(5, lote.getIdLote());
               
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException e){
                 respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
             respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int agregarCantidadLotesEnLote(int idLote, int cantidadLotes){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "UPDATE Lote SET " + 
                       "cantidadLotes = (cantidadLotes + ?) " + 
                       "WHERE idLote = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, cantidadLotes);
                prepararSentencia.setInt(2, idLote);
               
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            }catch(SQLException e){
                 respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
             respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
}
