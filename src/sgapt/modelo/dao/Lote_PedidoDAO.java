package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Lote_PedidoRespuesta;
import sgapt.modelo.pojo.Lote_Pedido;
import sgapt.util.Constantes;

public class Lote_PedidoDAO {
    
    public static int agregarLoteAPedido(int idLote, int idPedido, int cantidadLotes){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "INSERT INTO Lote_Pedido (Lote_idLote, " + 
                        "Pedido_idPedido, cantidadLotes) "
                        + "VALUES (?,?,?)";
                PreparedStatement prepararSetencia = conexionBD.prepareStatement(sentencia);
                prepararSetencia.setInt(1, idLote);
                prepararSetencia.setInt(2, idPedido);
                prepararSetencia.setInt(3, cantidadLotes);
                
                int filasAfectadas = prepararSetencia.executeUpdate();
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
    
//    public static int removerCantidadLotesSolicitados(int idLote, int cantidadSolicitada) {
//        int respuesta;
//        Connection conexionBD = ConexionBD.abrirConexionBD();
//        if(conexionBD != null){
//            try{
//               String sentencia = "UPDATE Lote SET cantidadLotes = (cantidadLotes - ?) "
//                       + "WHERE idLote = ?";
//               PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
//               prepararSentencia.setInt(1, cantidadSolicitada);
//               prepararSentencia.setInt(2, idLote);
//               
//                int filasAfectadas = prepararSentencia.executeUpdate();
//                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
//                        Constantes.ERROR_CONSULTA;
//                conexionBD.close();
//            }catch(SQLException e){
//                 respuesta = Constantes.ERROR_CONSULTA;
//            }
//        }else{
//             respuesta = Constantes.ERROR_CONEXION;
//        }
//        return respuesta;
//    }
    
    
    public static int modificarCantidadLotesDePedido(int idLote, 
        int idPedido, int cantidadLotes){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
               String sentencia = "UPDATE Lote_Pedido SET cantidadLotes = ? "
                       + "WHERE Lote_idLote = ? AND Pedido_idPedido = ?";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
               prepararSentencia.setInt(1, cantidadLotes);
               prepararSentencia.setInt(2, idLote);
               prepararSentencia.setInt(3, idPedido);
               
                int filasAfectadas = prepararSentencia.executeUpdate();
                
                switch (filasAfectadas)
                {
                    case 1:
                        respuesta = Constantes.OPERACION_EXITOSA;
                        break;
                    case 0:
                        respuesta = Constantes.SIN_RESULTADOS;
                        break;
                    default:
                        respuesta = Constantes.ERROR_CONSULTA;
                        break;
                }
                conexionBD.close();
            }catch(SQLException e){
                 respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
             respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int eliminarLoteDePedido(int idLote, int idPedido){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "DELETE lote_pedido WHERE Pedido_idPedido = ? " + 
                        "AND lote_idLote = ?";
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
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        
        return respuesta;
       
    }
    
    public static int eliminarLotesDePedido(int idPedido) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM lote_pedido WHERE Pedido_idPedido = ?";
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
    
    public static Lote_PedidoRespuesta obtenerLotesEnPedido(int idPedido) {
        Lote_PedidoRespuesta respuesta = new Lote_PedidoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT lote_idLote, cantidadLotes " +
                        "FROM Lote_Pedido WHERE Pedido_idPedido = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idPedido);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Lote_Pedido> lotes_pedidoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Lote_Pedido lote_pedido = new Lote_Pedido();
                    lote_pedido.setLote_idLote(resultado.getInt("lote_idLote"));
                    lote_pedido.setPedido_idPedido(idPedido);
                    lote_pedido.setCantidadLotes(resultado.getInt("cantidadLotes"));
                    lotes_pedidoConsulta.add(lote_pedido);
                }
                respuesta.setLotesPedido(lotes_pedidoConsulta);
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
