/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sgapt.controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sgapt.modelo.dao.ProductoDAO;
import sgapt.modelo.pojo.ProductoRespuesta;
import sgapt.modelo.dao.SucursalDAO;
import sgapt.modelo.pojo.SucursalRespuesta;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.ResultadoOperacion;
import sgapt.modelo.pojo.Sucursal;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLEliminacionProductosController implements Initializable {

    @FXML
    private TableView<Producto> tvProductos;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colTipoProducto;
    @FXML
    private TableColumn<?, ?> colDisponibilidad;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<?, ?> colFechaCaducidad;
    @FXML
    private TableColumn<?, ?> colRequiereReceta;
    @FXML
    private TableColumn<?, ?> colNumeroLote;
    @FXML
    private TableColumn<?, ?> colPrecio;
    @FXML
    private ComboBox<Sucursal> cbSucursales;
    private ObservableList<Sucursal> listaSucursales;
    private ObservableList<Producto> listaProductos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarListaSucursales();
        cbSucursales.valueProperty().addListener(new ChangeListener<Sucursal>(){
                
            @Override
            public void changed(ObservableValue<? extends Sucursal> observable, 
                    Sucursal oldValue, Sucursal newValue) {
                if(newValue != null){
                    cargarProductosDisponibles(newValue);
                }
            }
        });
    }    
    
    public void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProducto"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory("disponibilidad"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colRequiereReceta.setCellValueFactory(new PropertyValueFactory("requiereReceta"));
        colNumeroLote.setCellValueFactory(new PropertyValueFactory("numeroLote"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory("fechaCaducidad"));
    }
    
    public void cargarProductosDisponibles(Sucursal sucursalSeleccionada){
            listaProductos = FXCollections.observableArrayList();
            ProductoRespuesta pr = ProductoDAO.recuperarProductosEnSucursal(sucursalSeleccionada);
            switch (pr.getCodigoRespuesta()){
                    case Constantes.OPERACION_EXITOSA:
                        for (Producto producto : pr.getProductos()){
                            if (producto.getDisponibilidad().equals("disponible")){
                                listaProductos.add(producto);
                            }
                        }
                        tvProductos.setItems(listaProductos);
                    break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                            "Por el momento no se puede procesar la solicitud de verificación", 
                                Alert.AlertType.ERROR);
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexión", 
                                "Por el momento no hay conexión, intentelo más tarde", 
                                Alert.AlertType.ERROR);
                    default:
                        Utilidades.mostrarDialogoSimple("Error de petición", 
                                "El sistema no está disponible por el momento", 
                                Alert.AlertType.ERROR);
            }
    }

    private void cargarListaSucursales(){
        listaSucursales = FXCollections.observableArrayList();
        SucursalRespuesta sr = SucursalDAO.recuperarSucursales();
        listaSucursales.addAll(sr.getSucursales());
        cbSucursales.setItems(listaSucursales);
    }
    
    private Producto verificarProductoSeleccionado(){
        int filaSeleccionada = tvProductos.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0)? listaProductos.get(filaSeleccionada) : null;
    }
    
    public void regresarAventanaAnterior(){
        Stage stagePrincipal = (Stage) tvProductos.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("/sgapt/vistas/FXMLAdministracionInventarioProductos.fxml"));
        stagePrincipal.setTitle("Administración de inventario");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        regresarAventanaAnterior();
    }

    @FXML
    private void clicEliminarProductos(ActionEvent event) {
        Producto productoSeleccionado = verificarProductoSeleccionado();
        if(productoSeleccionado != null){
            boolean seleccion = Utilidades.mostrarDialogoConfirmacion("Confirmar selección", 
                    "Está seguro de que desea eliminar estos productos del inventario?, hacer esto hará que no estén disponibles para su venta al público");
            if (seleccion){
                try{
                    ResultadoOperacion resultado = ProductoDAO.eliminarProducto(productoSeleccionado);
                    if (!resultado.isError()){
                        Utilidades.mostrarDialogoSimple("Éxito en la operación", 
                                resultado.getMensaje(), 
                                Alert.AlertType.INFORMATION); 
                        regresarAventanaAnterior();
                    }else{
                        Utilidades.mostrarDialogoSimple("Error en la operación", 
                                resultado.getMensaje(), Alert.AlertType.ERROR);
                    }
                }
                catch(SQLException e){
                    
                }
            }
        }else{
            Utilidades.mostrarDialogoSimple("Error de selección", 
                    "Por favor seleccione los distintos productos a eliminar y vuelva a intentarlo", 
                    Alert.AlertType.ERROR);
        }
    }
}
