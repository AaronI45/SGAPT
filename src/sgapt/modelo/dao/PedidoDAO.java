package sgapt.modelo.dao;

import sgapt.modelo.pojo.PedidoRespuesta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Pedido;
import sgapt.util.Constantes;

public class PedidoDAO {
    
    public static PedidoRespuesta obtenerInformacionPedidos() {
        PedidoRespuesta respuesta = new PedidoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idPedido, Proveedor.nombre AS nombreProveedor, "
                        + "direccionEntrega, fechaPedido, fechaEnvio, fechaEntrega, " + 
                        "estadoRastreo, montoTotal, numPedido, precioProductos, precioEnvio, " + 
                        "Proveedor_idProveedor, Farmacia_idFarmacia " +
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
                    pedido.setNumPedido(resultado.getString("numPedido"));
                    pedido.setPrecioProductos(resultado.getDouble("precioProductos"));
                    pedido.setPrecioEnvio(resultado.getDouble("precioEnvio"));
                    pedido.setIdProveedor(resultado.getInt("Proveedor_idProveedor"));
                    pedido.setIdSucursal(resultado.getInt("Farmacia_idFarmacia"));
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
    
    public static int eliminarPedido(int idPedido) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM pedido WHERE idPedido = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPedido);
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
    
    public static PedidoRespuesta guardarPedido(Pedido nuevoPedido){
        PedidoRespuesta respuesta = new PedidoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO pedido (Proveedor_idProveedor, " + 
                        "Farmacia_idFarmacia, fechaPedido, montoTotal, direccionEntrega, " + 
                        "estadoRastreo, numPedido, precioProductos, precioEnvio) VALUES " + 
                        "(?,?,?,?,?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setInt(1, nuevoPedido.getIdProveedor());
                prepararSentencia.setInt(2, nuevoPedido.getIdSucursal());
                prepararSentencia.setString(3, nuevoPedido.getFechaPedido());
                prepararSentencia.setDouble(4, nuevoPedido.getMontoTotal());
                prepararSentencia.setString(5, nuevoPedido.getDireccionEntrega());
                prepararSentencia.setString(6, nuevoPedido.getEstadoRastreo());
                prepararSentencia.setString(7, nuevoPedido.getNumPedido());
                prepararSentencia.setDouble(8, nuevoPedido.getPrecioProductos());
                prepararSentencia.setDouble(9, nuevoPedido.getPrecioEnvio());
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas == 1) {
                    respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                    ResultSet resultadoLlave = prepararSentencia.getGeneratedKeys();
                    if (resultadoLlave.next()) {
                        respuesta.setIdPedido(resultadoLlave.getInt(1));
                    }
                }
                else
                    respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
                        
                conexionBD.close();
                
            }catch(SQLException e){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }   
    
    public static int modificarPedido(Pedido pedidoEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
               String sentencia = "UPDATE pedido SET Proveedor_idProveedor = ?, " + 
                        "Farmacia_idFarmacia = ?, fechaPedido = ?, montoTotal = ?, " + 
                       "direccionEntrega = ?, estadoRastreo = ?, numPedido = ?, " + 
                       "precioProductos = ?, precioEnvio = ? " + 
                       "WHERE idPedido = ?";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
               prepararSentencia.setInt(1, pedidoEdicion.getIdProveedor());
                prepararSentencia.setInt(2, pedidoEdicion.getIdSucursal());
                prepararSentencia.setString(3, pedidoEdicion.getFechaPedido());
                prepararSentencia.setDouble(4, pedidoEdicion.getMontoTotal());
                prepararSentencia.setString(5, pedidoEdicion.getDireccionEntrega());
                prepararSentencia.setString(6, pedidoEdicion.getEstadoRastreo());
                prepararSentencia.setString(7, pedidoEdicion.getNumPedido());
                prepararSentencia.setDouble(8, pedidoEdicion.getPrecioProductos());
                prepararSentencia.setDouble(9, pedidoEdicion.getPrecioEnvio());
                prepararSentencia.setDouble(10, pedidoEdicion.getIdPedido());
               
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
