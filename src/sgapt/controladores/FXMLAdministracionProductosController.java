package sgapt.controladores;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class FXMLAdministracionProductosController implements Initializable {

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
    @FXML
    private ImageView ivProducto;

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
                    cargarDatosTabla(newValue);
                    ivProducto.setImage(null);
                }
            }
        });
        
        tvProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                if (newSelection.getFoto() !=null){
                    try {
                        ByteArrayInputStream inputFoto = new ByteArrayInputStream(newSelection.getFoto());
                        Image imgFotoEdicion = new Image(inputFoto);
                        ivProducto.setImage(imgFotoEdicion);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }else{
                    try {
                        Image img = new Image(new FileInputStream("src\\sgapt\\img\\imagen-no-disponible.png"));
                        ivProducto.setImage(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("no hay fotografía disponible");
                    }
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
    
    public void cargarDatosTabla(Sucursal sucursalSeleccionada){
            listaProductos = FXCollections.observableArrayList();
            ProductoRespuesta pr = ProductoDAO.recuperarProductosEnSucursal(sucursalSeleccionada);
            ArrayList<Producto> listaProductosActualizados = actualizarProductosCaducados(pr.getProductos());
            pr.setProductos(listaProductosActualizados);
            switch (pr.getCodigoRespuesta()){
                    case Constantes.OPERACION_EXITOSA:
                        for (Producto producto : listaProductosActualizados){
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
                        break;
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexión", 
                                "Por el momento no hay conexión, intentelo más tarde", 
                                Alert.AlertType.ERROR);
                        break;
                    default:
                        Utilidades.mostrarDialogoSimple("Error de petición", 
                                "El sistema no está disponible por el momento", 
                                Alert.AlertType.ERROR);
                        break;
            }
    }

    private ArrayList<Producto> actualizarProductosCaducados(ArrayList<Producto> productos){
        ResultadoOperacion resultadoEdicion;
        for (Producto producto : productos){
            if (!producto.getDisponibilidad().equals("caducado")){
                if(Date.from(Instant.now()).after(producto.getFechaCaducidad())){
                    resultadoEdicion = ProductoDAO.caducarProducto(producto);
                    producto.setDisponibilidad("caducado");
                    if (!resultadoEdicion.isError()){
                        Utilidades.mostrarDialogoSimple("Nuevo producto caducado", 
                                resultadoEdicion.getMensaje(),
                                Alert.AlertType.WARNING);
                    }else{
                        Utilidades.mostrarDialogoSimple("Error al registrar producto caducado", 
                                resultadoEdicion.getMensaje(), Alert.AlertType.ERROR);
                    }
                }
            }
        }
        return productos;
    }
    
    private void cargarListaSucursales(){
        listaSucursales = FXCollections.observableArrayList();
        SucursalRespuesta sr = SucursalDAO.recuperarSucursales();
        listaSucursales.addAll(sr.getSucursales());
        cbSucursales.setItems(listaSucursales);
    }
    
    private Producto verificarProductoSeleccionado(){
        Producto filaSeleccionada = tvProductos.getSelectionModel().getSelectedItem();
        return filaSeleccionada;
    }
    
    public void regresarAventanaAnterior(){
        Stage stagePrincipal = (Stage) tvProductos.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("/sgapt/vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Administración de inventario");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        regresarAventanaAnterior();
    }

    @FXML
    private void clicEliminarProducto(ActionEvent event) {
        Producto productoSeleccionado = verificarProductoSeleccionado();
        if(productoSeleccionado != null){
            boolean seleccion = Utilidades.mostrarDialogoConfirmacion("Confirmar selección", 
                    "Está seguro de que desea eliminar estos productos del inventario?, hacer esto hará que no estén disponibles para su venta al público");
            if (seleccion){
                if (!productoSeleccionado.getDisponibilidad().equals("eliminado")){
                    try{
                        ResultadoOperacion resultado = ProductoDAO.eliminarProducto(productoSeleccionado);
                        if (!resultado.isError()){
                            Utilidades.mostrarDialogoSimple("Éxito en la operación", 
                                    resultado.getMensaje(), 
                                    Alert.AlertType.INFORMATION); 
                            cargarDatosTabla(productoSeleccionado.getSucursal());
                        }else{
                            Utilidades.mostrarDialogoSimple("Error en la operación", 
                                    resultado.getMensaje(), Alert.AlertType.ERROR);
                        }
                    }
                    catch(SQLException e){

                    }
                }else{
                    Utilidades.mostrarDialogoSimple("Error de eliminación", 
                            "Se está intentando eliminar un producto que ya está eliminado, por favor seleccione un producto distinto para eliminarlo", 
                            Alert.AlertType.ERROR);
                }
            }
        }
    }

    private void irAFormulario(Producto producto){
        try {
            FXMLLoader accesoControlador = 
                    new FXMLLoader(getClass().getResource("/sgapt/vistas/FXMLFormularioProducto.fxml"));
            Parent vista = accesoControlador.load();
            
            FXMLFormularioProductoController formulario = 
                    accesoControlador.getController();
            Scene escenaFormulario = new Scene(vista);
            Stage escenario = (Stage) tvProductos.getScene().getWindow();
            escenario.setScene(escenaFormulario);
            formulario.inicializarValores(producto);
        } catch (IOException e) {
            Utilidades.mostrarDialogoSimple("Error", "No se puede mostrar la pantalla de formulario", 
                    Alert.AlertType.ERROR);
        }
    }        
    
    @FXML
    private void clicEditarProducto(ActionEvent event) {
        Producto productoSeleccionado = verificarProductoSeleccionado();
        if (productoSeleccionado != null){
            irAFormulario(productoSeleccionado);
        }else{
            Utilidades.mostrarDialogoSimple("Error de selección", "Por favor seleccione un producto para editar", 
                    Alert.AlertType.ERROR);
        }
    }

}