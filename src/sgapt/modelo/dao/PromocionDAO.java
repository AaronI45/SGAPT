/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Promocion;
import sgapt.modelo.pojo.PromocionRespuesta;
import sgapt.util.Constantes;

/**
 *
 * @author king_
 */
public class PromocionDAO {
    public static PromocionRespuesta obtenerInformacionPromocion() {
        PromocionRespuesta respuesta = new PromocionRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "Select idPromocion, producto.idProducto, producto.foto, producto.nombre AS nombreProducto,producto.precio AS precioProducto,porcentajeDescuento AS descuento, fechaInicio, fechaFin "
                        + " FROM promocion "
                        + " INNER JOIN producto ON promocion.Producto_IdProducto = idProducto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Promocion> promocionesConsulta = new ArrayList();
                while (resultado.next())
                {
                    Promocion promocion = new Promocion();
                    promocion.setIdPromocion(resultado.getInt("idPromocion")); 
                    promocion.setIdProducto(resultado.getInt("idProducto")); 
                    promocion.setProducto(resultado.getString("nombreProducto"));
                    promocion.setProductoPrecio(resultado.getFloat("precioProducto"));
                    promocion.setPorcentajeDescuento(resultado.getDouble("descuento")); 
                    promocion.setFechaInicio(resultado.getString("fechaInicio")); 
                    promocion.setFechaFin(resultado.getString("fechaFin"));
                    promocion.setFoto(resultado.getBytes("foto"));
                    promocion.setPrecioDescuento(descuento(promocion.getProductoPrecio(), promocion.getPorcentajeDescuento()));
                    promocionesConsulta.add(promocion);
                }
                respuesta.setPromociones(promocionesConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarPromocion(Promocion promocionNueva) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = " INSERT INTO promocion (Producto_idProducto, fechaInicio, fechaFin, porcentajeDescuento) VALUES"
                        + "(?,?,?,?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, promocionNueva.getIdProducto());
                prepararSentencia.setString(2, promocionNueva.getFechaInicio());
                prepararSentencia.setString(3, promocionNueva.getFechaFin());
                prepararSentencia.setDouble(4, promocionNueva.getPorcentajeDescuento());
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
    
    private static float descuento(float productoPrecio, double porcentajeDesc){
        float descuentoPro;
        porcentajeDesc=(porcentajeDesc/100);
        float descPro=(float)porcentajeDesc;
        descuentoPro = productoPrecio - (productoPrecio * descPro);
        return descuentoPro;
    }
    
       public static int darDeBajaPromocion(int idPromocion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE FROM promocion WHERE idPromocion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idPromocion);
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
       
     public static int modificarPromocion(Promocion promocionEdicion) {
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String sentencia = "UPDATE promocion SET porcentajeDescuento = ?, fechaInicio = ?, "
                       + "fechaFin = ?, Producto_IdProducto = ? "
                       + "WHERE idPromocion = ?";
               PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
               prepararSentencia.setDouble(1, promocionEdicion.getPorcentajeDescuento());
               prepararSentencia.setString(2,promocionEdicion.getFechaInicio());
               prepararSentencia.setString(3, promocionEdicion.getFechaFin());
               prepararSentencia.setInt(4, promocionEdicion.getIdProducto());
               prepararSentencia.setInt(5, promocionEdicion.getIdPromocion());
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
