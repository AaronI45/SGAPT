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
                String consulta = "SELECT * FROM promocion ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Promocion> promocionesConsulta = new ArrayList();
                while (resultado.next())
                {
                    Promocion promocion = new Promocion();
                    promocion.setIdPromocion(resultado.getInt("idPromocion"));
                    promocion.setTipoPromocion(resultado.getString("tipoPromocion"));
                    promocion.setFechaInicio(resultado.getString("fechaInicio"));
                    promocion.setFechaFin(resultado.getString("fechaFin"));
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
                String sentencia = "INSERT INTO promocion (idPromocion, " +
                        " tipoPromocion, fechaInicio, fechaFin) " + 
                        " VALUES (?, ?, ?, ?)";
                PreparedStatement prepararSentencia =  conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, promocionNueva.getIdPromocion());
                prepararSentencia.setString(2, promocionNueva.getTipoPromocion());
                prepararSentencia.setString(3, promocionNueva.getFechaInicio());
                prepararSentencia.setString(4, promocionNueva.getFechaFin());
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
