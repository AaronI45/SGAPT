
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
import sgapt.modelo.pojo.Sucursal;
import sgapt.util.Constantes;

/**
 *
 * @author super
 */
public class SucursalDAO {
    public static SucursalRespuesta recuperarSucursales (){
        SucursalRespuesta sucursales = new SucursalRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            try {
                String consulta = "SELECT `farmacia`.*, `almacen`.`idAlmacen`\n" +
                        "FROM `farmacia` \n" +
                        "LEFT JOIN `almacen` ON `almacen`.`Farmacia_idFarmacia` = `farmacia`.`idFarmacia`";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Sucursal> sucursalesConsulta = new ArrayList<>();
                while (resultado.next()){
                    Sucursal sucursal = new Sucursal();
                    sucursal.setCiudad(resultado.getString("ciudad"));
                    sucursal.setEstado(resultado.getString("estado"));
                    sucursal.setDireccion(resultado.getString("direccion"));
                    sucursal.setIdInventario(resultado.getInt("idAlmacen"));
                    sucursalesConsulta.add(sucursal);
                }
                sucursales.setSucursales(sucursalesConsulta);
                sucursales.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            } catch (SQLException e) {
                sucursales.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else
        {
            sucursales.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return sucursales;
    }
}

