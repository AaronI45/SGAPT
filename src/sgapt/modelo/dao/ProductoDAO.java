
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
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.ProductoRespuesta;
import sgapt.modelo.pojo.ResultadoOperacion;
import sgapt.modelo.pojo.Sucursal;
import sgapt.util.Constantes;

/**
 *
 * @author super
 */
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
                    //producto.setFoto(resultado.getBytes("foto"));
                    productosConsulta.add(producto);
                }
                productos.setProductos(productosConsulta);
                productos.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                productos.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }
        else{
            productos.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return productos;
    }

    public static ProductoRespuesta mostrarDisponibilidad (Producto product){
        ProductoRespuesta productos = new ProductoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null)
        {
            try{
                String consulta = "SELECT idProducto, disponibilidad FROM producto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Producto> productosConsulta = new ArrayList();
                while(resultado.next()){
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setDisponibilidad(resultado.getString("disponibilidad"));
                    producto.getDisponibilidad();
                }
                productos.setProductos(productosConsulta);
                productos.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                conexionBD.close();
            }catch(SQLException e){
                productos.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }
        else{
            productos.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return productos;
    }

    public static ResultadoOperacion eliminarProducto (Producto productoAEliminar) throws SQLException{
        ResultadoOperacion resultadoEliminacion = new ResultadoOperacion();
        resultadoEliminacion.setError(true);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null)
        {
            try{
                String consulta = "UPDATE producto SET disponibilidad = ? WHERE idProducto = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, "eliminado");
                prepararConsulta.setInt(2, productoAEliminar.getIdProducto());
                int filasAfectadas = prepararConsulta.executeUpdate();
                if (filasAfectadas > 0){
                    resultadoEliminacion.setMensaje("El producto ha sido eliminado correctamente");
                    resultadoEliminacion.setError(false);
                }else{
                    resultadoEliminacion.setMensaje("No se pudo eliminar el producto de manera correcta");
                }
            }catch(SQLException e){
                resultadoEliminacion.setMensaje("Error de conexión");
            }
            finally{
                conexionBD.close();
            }
        }else{
            resultadoEliminacion.setMensaje("No hay conexión a la base de datos");

        }
        return resultadoEliminacion;
    }

    public static ResultadoOperacion editarEstadoProducto (Producto productoAEditar){
        ResultadoOperacion resultadoEdicion = new ResultadoOperacion();

        return resultadoEdicion;
    }

    /*public static ProductoRespuesta obtenerProductoPorSucursal(int idProducto) {
        ProductoRespuesta respuesta = new ProductoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idProducto, nombre FROM producto where idSucursal = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProducto);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Producto> productoConsulta = new ArrayList();
                while (resultado.next())
                {
                    Producto producto = new Producto();
                    carrera.setIdCarrera(resultado.getInt("idCarrera"));
                    carrera.setNombre(resultado.getString("nombre"));
                    carrera.setCodigo(resultado.getString("codigo"));
                    productoConsulta.add(producto);
                }
                respuesta.setCarreras(carrerasConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }*/

}
