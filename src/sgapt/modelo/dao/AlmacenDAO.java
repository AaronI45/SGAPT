package sgapt.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Almacen;
import sgapt.modelo.pojo.AlmacenRespuesta;
import sgapt.util.Constantes;

public class AlmacenDAO {
    public static AlmacenRespuesta recuperarAlmacenes (){
        AlmacenRespuesta almacenes = new AlmacenRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            try {
                String consulta = "SELECT idAlmacen, Farmacia_idFarmacia, estado, " + 
                        "ciudad, direccion FROM almacen";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Almacen> almacenesConsulta = new ArrayList<>();
                while (resultado.next()){
                    Almacen almacen = new Almacen();
                    almacen.setCiudad(resultado.getString("ciudad"));
                    almacen.setEstado(resultado.getString("estado"));
                    almacen.setDireccion(resultado.getString("direccion"));
                    almacen.setIdAlmacen(resultado.getInt("idAlmacen"));
                    almacen.setIdFarmacia(resultado.getInt("Farmacia_idFarmacia"));
                    almacenesConsulta.add(almacen);
                }
                almacenes.setAlmacenes(almacenesConsulta);
                almacenes.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            } catch (SQLException e) {
                almacenes.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else
        {
            almacenes.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return almacenes;
    }
}
