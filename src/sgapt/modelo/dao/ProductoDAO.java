package sgapt.modelo.dao;

import sgapt.modelo.pojo.ProductoRespuesta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sgapt.modelo.ConexionBD;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Sucursal;
import sgapt.util.Constantes;

public class ProductoDAO {
    public static ProductoRespuesta recuperarProductosEnSucursal (Sucursal sucursal){
        ProductoRespuesta productos = new ProductoRespuesta(); 
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null)
        {
            try{
                String consulta = "SELECT `producto_almacenado`.*, `producto`.*, `almacen`.*, `lote`.`fechaDeCaducidad`, `lote`.`idLote`\n" +
                        "FROM `producto_almacenado` \n" +
                        "LEFT JOIN `producto` ON `producto_almacenado`.`Producto_idProducto` = `producto`.`idProducto` \n" +
                        "LEFT JOIN `almacen` ON `producto_almacenado`.`Almacen_idAlmacen` = `almacen`.`idAlmacen` \n" +
                        "LEFT JOIN `lote` ON `lote`.`Producto_idProducto` = `producto`.`idProducto`\n" +
                        "WHERE almacen.idAlmacen = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, sucursal.getIdInventario());
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Producto> productosConsulta = new ArrayList();
                while(resultado.next()){
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setCantidad(resultado.getInt("cantidad"));
                    producto.setSucursal(sucursal);
                    producto.setDisponibilidad(resultado.getString("disponibilidad"));
                    producto.setTipoProducto(resultado.getString("tipoProducto"));
                    producto.setFechaCaducidad(resultado.getDate("fechaDeCaducidad"));
                    producto.setRequiereReceta(resultado.getBoolean("requiereReceta"));
                    producto.setNumeroLote(resultado.getInt("idLote"));
                    producto.setPrecio(resultado.getInt("precio"));
                    productosConsulta.add(producto);
                }
                productos.setProductos(productosConsulta);
                productos.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
            }catch(SQLException e){
                productos.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }      
        }
        else{
            productos.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return productos;
    }
    
}