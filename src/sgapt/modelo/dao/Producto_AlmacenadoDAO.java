package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Producto_Almacenado;
import sgapt.util.Constantes;

public class Producto_AlmacenadoDAO {
    
    public static Producto_Almacenado recuperarProductoAlmacenado(int idProducto, int idAlmacen) {        
        Producto_Almacenado producto_almacenadoRespuesta = new Producto_Almacenado();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "SELECT cantidad " + 
                        "FROM Producto_Almacenado " + 
                        "WHERE Producto_idProducto = ? AND Almacen_idAlmacen = ?";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idProducto);
                prepararSentencia.setInt(2, idAlmacen);
                
                ResultSet resultado = prepararSentencia.executeQuery();
                producto_almacenadoRespuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if (resultado.next()) {
                    producto_almacenadoRespuesta.setProducto_idProducto(idProducto);
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
    
    public static int guardarProducto_Almacenado(Producto_Almacenado producto_Almacenado) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO producto_almacenado(producto_idProducto, " + 
                        "almacen_idAlmacen, cantidad) "+ 
                        "VALUES (?, ?, ?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, producto_Almacenado.getProducto_idProducto());
                prepararSentencia.setInt(2, producto_Almacenado.getAlmacen_idAlmacen());
                prepararSentencia.setInt(3, producto_Almacenado.getCantidad());                
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
    
    public static int modificarProducto_Almacenado(Producto_Almacenado producto_Almacenado) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE producto_almacenado " + 
                        "SET cantidad = ? " + 
                        "WHERE Producto_idProducto = ? AND Almacen_idAlmacen = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, producto_Almacenado.getCantidad());
                prepararSentencia.setInt(2, producto_Almacenado.getProducto_idProducto());
                prepararSentencia.setInt(3, producto_Almacenado.getAlmacen_idAlmacen());
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
    
    public static int eliminarProducto_Almacenado(int producto_idProducto, int almacen_idAlmacen) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM producto_almacenado " + 
                        "WHERE producto_idProducto = ? " + 
                        "AND almacen_idAlmacen = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, producto_idProducto);
                prepararSentencia.setInt(2, almacen_idAlmacen);
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
