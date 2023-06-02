
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgapt.modelo.dao;

import java.io.File;
import java.nio.file.Files;
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
                String consulta = "SELECT `lote_almacenado`.*, `farmacia`.`idFarmacia`, `lote`.`fechaDeCaducidad`, lote.numeroDeLote, `producto`.*\n" +
                        "FROM `lote_almacenado` \n" +
                        "LEFT JOIN `farmacia` ON `lote_almacenado`.`Farmacia_idFarmacia` = `farmacia`.`idFarmacia` \n" +
                        "LEFT JOIN `lote` ON `lote_almacenado`.`Lote_idLote` = `lote`.`idLote` \n" +
                        "LEFT JOIN `producto` ON `lote`.`Producto_idProducto` = `producto`.`idProducto`\n" +
                        "WHERE idFarmacia = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, sucursal.getIdSucursal());
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Producto> productosConsulta = new ArrayList();
                while (resultado.next()) {
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setCantidad(resultado.getInt("cantidad"));
                    producto.setSucursal(sucursal);
                    producto.setDisponibilidad(resultado.getString("disponibilidad"));
                    producto.setTipoProducto(resultado.getString("tipoProducto"));
                    producto.setFechaCaducidad(resultado.getDate("fechaDeCaducidad"));
                    producto.setRequiereReceta(resultado.getBoolean("requiereReceta"));
                    producto.setIdLote(resultado.getInt("Lote_idLote"));
                    producto.setNumeroLote(resultado.getString("numeroDeLote"));
                    producto.setPrecio(resultado.getDouble("precio"));
                    producto.setFoto(resultado.getBytes("foto"));
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

    public static ResultadoOperacion editarProductoPorID (Producto productoEditado, int idProducto, byte[] foto){
        ResultadoOperacion resultadoEdicion = new ResultadoOperacion();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        resultadoEdicion.setError(true);
        if (conexionBD != null){
            try{
                String consulta = "UPDATE `producto` SET `requiereReceta` = ?, `precio` = ?, `tipoProducto` = ?, foto = ? "
                        + "WHERE `producto`.`idProducto` = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setBoolean(1, productoEditado.isRequiereReceta());
                prepararSentencia.setDouble(2, productoEditado.getPrecio());
                prepararSentencia.setString(3, productoEditado.getTipoProducto());
                prepararSentencia.setBytes(4, foto);
                prepararSentencia.setInt(5, idProducto);
                int numeroFilas = prepararSentencia.executeUpdate();
                if (numeroFilas > 0){
                    resultadoEdicion.setError(false);
                    resultadoEdicion.setMensaje("El producto ha sido editado correctamente");
                }else{
                    resultadoEdicion.setMensaje("No se pudo editar el producto correctamente ");
                }
            }catch(SQLException e){
                resultadoEdicion.setMensaje("Error de conexión");
                e.printStackTrace();
            }                
        }else{
            resultadoEdicion.setMensaje("No hay conexión a la base de datos");
        }
        return resultadoEdicion;
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

    public static ResultadoOperacion caducarProducto (Producto productoACaducar){
        ResultadoOperacion resultadoCaducar = new ResultadoOperacion();
        resultadoCaducar.setError(true);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            try{
                String consulta = "UPDATE producto SET disponibilidad = ? WHERE idProducto = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, "caducado");
                prepararConsulta.setInt(2, productoACaducar.getIdProducto());
                int filasAfectadas = prepararConsulta.executeUpdate();
                if(filasAfectadas > 0){
                    resultadoCaducar.setMensaje("El producto " + productoACaducar.toString() +" se ha registrado como caducado");
                    resultadoCaducar.setError(false);
                }else{
                    resultadoCaducar.setMensaje("No se pudo caducar el producto de manera correcta");
                }
            }catch (SQLException e){
                resultadoCaducar.setMensaje("Error de conexión");
            }
        }else{
            resultadoCaducar.setMensaje("No hay conexión con la base de datos");
        }
        return resultadoCaducar;
    }
    
    public static ResultadoOperacion agregarProducto (Producto productoNuevo) throws SQLException{
        ResultadoOperacion resultadoAgregar = new ResultadoOperacion();
        resultadoAgregar.setError(true);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null){
            try {
                String consulta = "INSERT INTO producto (nombre, requiereReceta, precio, tipoProducto, disponibilidad) "
                        + "VALUES (?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, productoNuevo.getNombre());
                prepararSentencia.setBoolean(2, productoNuevo.isRequiereReceta());
                prepararSentencia.setDouble(3, productoNuevo.getPrecio());
                prepararSentencia.setString(4, productoNuevo.getTipoProducto());
                prepararSentencia.setString(5, "no disponible");
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas >0){
                    resultadoAgregar.setError(false);
                    resultadoAgregar.setMensaje("El producto ha sido registrado exitosamente, ahora puede ordenar este tipo de productos");
                }else{
                    resultadoAgregar.setMensaje("Hubo un error al registrar el nuevo producto, por favor inténtelo de nuevo");
                }
            } catch (SQLException e) {
                resultadoAgregar.setMensaje("Error de conexión");
            }
            finally{
                conexionBD.close();
            }
        }else{
            resultadoAgregar.setMensaje("No hay conexión a la base de datos");
        }
        return resultadoAgregar;
    }

    public static ProductoRespuesta recuperarProductosEnAlmacen (int idAlmacen){
        ProductoRespuesta productos = new ProductoRespuesta();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null)
        {
            try{
                String consulta = "SELECT idProducto, nombre, disponibilidad, " +
                        "tipoProducto, lote.numeroDeLote, lote.idLote, lote_almacenado.cantidad " +
                        "FROM producto INNER JOIN lote ON producto.idProducto = " +
                        "lote.Producto_idProducto INNER JOIN lote_almacenado ON " +
                        "lote.idLote = lote_almacenado.Lote_idLote " +
                        "WHERE lote_almacenado.Almacen_idAlmacen = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAlmacen);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Producto> productosConsulta = new ArrayList();
                while(resultado.next()){
                    Producto producto = new Producto();
                    producto.setIdProducto(resultado.getInt("idProducto"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setDisponibilidad(resultado.getString("disponibilidad"));
                    producto.setTipoProducto(resultado.getString("tipoProducto"));
                    producto.setNumeroLote(resultado.getString("numeroDeLote"));
                    producto.setCantidad(resultado.getInt("cantidad"));
                    producto.setIdLote(resultado.getInt("idLote"));
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
}
