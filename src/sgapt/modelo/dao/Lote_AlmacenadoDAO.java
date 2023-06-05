package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Lote_Almacenado;
import sgapt.util.Constantes;

public class Lote_AlmacenadoDAO {
    
    public static Lote_Almacenado recuperarLoteAlmacenado(int idLote, int idAlmacen) {        
        Lote_Almacenado producto_almacenadoRespuesta = new Lote_Almacenado();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT cantidad " + 
                        "FROM Lote_Almacenado " + 
                        "WHERE Lote_idLote = ? AND Farmacia_idFarmacia = ?";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idLote);
                prepararSentencia.setInt(2, idAlmacen);
                
                ResultSet resultado = prepararSentencia.executeQuery();
                producto_almacenadoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    producto_almacenadoRespuesta.setLote_idLote(idLote);
                    producto_almacenadoRespuesta.setAlmacen_idAlmacen(idAlmacen);
                    producto_almacenadoRespuesta.setCantidad(resultado.getInt("cantidad"));
                } else {
                    producto_almacenadoRespuesta.setCodigoRespuesta(Constantes.SIN_RESULTADOS);
                }
                conexionBD.close();
            } catch (SQLException e) {
                producto_almacenadoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            producto_almacenadoRespuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return producto_almacenadoRespuesta;
    }
    
    //TODO
    public static int guardarLote_Almacenado(Lote_Almacenado lote_Almacenado) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO lote_almacenado(lote_idLote, " + 
                        "farmacia_idFarmacia, cantidad) "+ 
                        "VALUES (?, ?, ?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, lote_Almacenado.getLote_idLote());
                prepararSentencia.setInt(2, lote_Almacenado.getAlmacen_idAlmacen());
                prepararSentencia.setInt(3, lote_Almacenado.getCantidad());                
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
    
    public static int modificarLote_Almacenado(Lote_Almacenado lote_Almacenado) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE lote_almacenado " + 
                        "SET cantidad = ? " + 
                        "WHERE lote_idlote = ? AND Farmacia_idFarmacia = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, lote_Almacenado.getCantidad());
                prepararSentencia.setInt(2, lote_Almacenado.getLote_idLote());
                prepararSentencia.setInt(3, lote_Almacenado.getAlmacen_idAlmacen());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
                e.printStackTrace();
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int eliminarLote_Almacenado(int lote_idLote, int farmacia_idFarmacia) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM lote_almacenado " + 
                        "WHERE lote_idLote = ? " + 
                        "AND farmacia_idFarmacia = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, lote_idLote);
                prepararSentencia.setInt(2, farmacia_idFarmacia);
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException e) {
                respuesta = Constantes.ERROR_CONSULTA;
                e.printStackTrace();
            }
        } else {
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
