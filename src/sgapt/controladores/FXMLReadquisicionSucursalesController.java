package sgapt.controladores;

import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sgapt.modelo.dao.ProductoDAO;
import sgapt.modelo.dao.Producto_AlmacenadoDAO;
import sgapt.modelo.pojo.ProductoRespuesta;
import sgapt.modelo.dao.SucursalDAO;
import sgapt.modelo.pojo.SucursalRespuesta;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Producto_Almacenado;
import sgapt.modelo.pojo.Sucursal;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLReadquisicionSucursalesController implements Initializable {

    @FXML
    private TableView<Producto> tvProductos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colTipoProducto;
    @FXML
    private TableColumn colDisponibilidad;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colFechaCaducidad;
    @FXML
    private TableColumn colRequiereReceta;
    @FXML
    private TableColumn colNumeroLote;
    @FXML
    private TableColumn colPrecio;
    @FXML
    private ComboBox<Sucursal> cbSucursalOrigen;
    @FXML
    private ComboBox<Sucursal> cbSucursalDestino;
    @FXML
    private TextField tfCantidad;
    
    private ObservableList<Sucursal> sucursales;
    private ObservableList<Producto> productos;
    private int idProductoSeleccionadoEnTabla; 
    private int idAlmacenOrigenSeleccionado;
    private int idAlmacenDestinoSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarListasSucursales();
        cbSucursalOrigen.valueProperty().addListener(new ChangeListener<Sucursal>(){
            @Override
            public void changed(ObservableValue<? extends Sucursal> observable, 
                    Sucursal oldValue, Sucursal newValue) {
                if(newValue != null){
                    cargarDatosTabla(newValue);
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
    
    private void cargarListasSucursales() {
        sucursales = FXCollections.observableArrayList();
        SucursalRespuesta sr = SucursalDAO.recuperarSucursales();
        sucursales.addAll(sr.getSucursales());
        cbSucursalOrigen.setItems(sucursales);
        cbSucursalDestino.setItems(sucursales);
    }
    
    
    public void cargarDatosTabla(Sucursal sucursalSeleccionada){
        productos = FXCollections.observableArrayList();
        ProductoRespuesta pr = ProductoDAO.recuperarProductosEnSucursal(sucursalSeleccionada);
        switch (pr.getCodigoRespuesta()){
                case Constantes.OPERACION_EXITOSA:
                    productos.addAll(pr.getProductos());
                    tvProductos.setItems(productos);
                break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud", 
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

    @FXML
    private void clicRealizarReadquisicion(ActionEvent event) {
        Sucursal sucursalOrigen = cbSucursalOrigen.getSelectionModel().getSelectedItem();
        Sucursal sucursalDestino = cbSucursalDestino.getSelectionModel().getSelectedItem();
        
        if (sucursalOrigen != null && sucursalDestino != null) {
            if (sucursalOrigen.getIdInventario() == sucursalDestino.getIdInventario()) {
                Utilidades.mostrarDialogoSimple("Error", "La sucursal de origen no puede ser " + 
                        "la misma que la sucursal de destino. Por favor, cambie la selección.",
                        Alert.AlertType.WARNING);
            } else {                
                int posicionProductoSel = tvProductos.getSelectionModel().getSelectedIndex();
                    if (posicionProductoSel != -1) {
                        idProductoSeleccionadoEnTabla = productos.
                                get(posicionProductoSel).getIdProducto();                        
                        boolean realizarReadquisicion = Utilidades.mostrarDialogoConfirmacion(
                                "Readquisición de productos", 
                                "¿Está seguro de que desea realizar la readquisición?");

                        if (realizarReadquisicion) {                    
                            idAlmacenOrigenSeleccionado = sucursalOrigen.getIdInventario();
                            idAlmacenDestinoSeleccionado = sucursalDestino.getIdInventario();
                            validarCantidadReadquisicion();  
                        }
                    } else
                        Utilidades.mostrarDialogoSimple("Selecciona un producto", 
                                "Para realizar la readquisicion debes seleccionar el producto " + 
                                        "de la tabla", Alert.AlertType.WARNING);
                
            }
        }
    }

    private void validarCantidadReadquisicion() {
        int cantidadReadquisicion = Integer.parseInt(tfCantidad.getText());
        
        if (cantidadReadquisicion > 0) {
            int cantidadProductoAlmacenado = obtenerCantidadDeProductoAlmacenado();
            if (cantidadProductoAlmacenado > 0) {
                if (cantidadReadquisicion <= cantidadProductoAlmacenado) {
                    realizarActualizacionEnAlmacenDestino(cantidadReadquisicion);
                    cargarDatosTabla(cbSucursalOrigen.getSelectionModel().getSelectedItem());                        
                } else {
                    Utilidades.mostrarDialogoSimple("Error", 
                        "Debe introducir una cantidad de productos menor o igual " + 
                                "a la cantidad en almacen", 
                        Alert.AlertType.WARNING);
                    tfCantidad.setText("");
                }
            }
        } else {
            Utilidades.mostrarDialogoSimple("Error", 
                    "Debe introducir una cantidad de productos mayor a 0", 
                    Alert.AlertType.WARNING);
            tfCantidad.setText("");
        }
    }
    
    private void realizarActualizacionEnAlmacenDestino(int cantidad) {
        
        Producto_Almacenado producto_Almacenado = Producto_AlmacenadoDAO.
            recuperarProductoAlmacenado(idProductoSeleccionadoEnTabla, 
                    idAlmacenDestinoSeleccionado);
        restarCantidadProductoAlmacenOrigen(cantidad);
        
        switch (producto_Almacenado.getCodigoRespuesta()){
            case Constantes.OPERACION_EXITOSA: 
                producto_Almacenado.setCantidad(cantidad + producto_Almacenado.getCantidad());
                    int resultado = Producto_AlmacenadoDAO.
                            modificarProducto_Almacenado(producto_Almacenado);
                
                switch (resultado) {
                    case Constantes.OPERACION_EXITOSA:
                        Utilidades.mostrarDialogoSimple("Operacion exitosa", 
                            "La readquisición se ha realizado correctamente", 
                            Alert.AlertType.INFORMATION);
                        break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                            "Por el momento no se puede procesar la solicitud", 
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
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                    "Por el momento no se puede procesar la solicitud", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "Por el momento no hay conexión, intentelo más tarde", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.SIN_RESULTADOS:
                        producto_Almacenado.setCantidad(cantidad);
                        producto_Almacenado.setProducto_idProducto(idProductoSeleccionadoEnTabla);
                        producto_Almacenado.setAlmacen_idAlmacen(idAlmacenDestinoSeleccionado);
                        int respuesta = Producto_AlmacenadoDAO.guardarProducto_Almacenado(producto_Almacenado);
                    
                    switch (respuesta) {
                        case Constantes.OPERACION_EXITOSA:
                            Utilidades.mostrarDialogoSimple("Operacion exitosa", 
                                "La readquisición se ha realizado correctamente", 
                                Alert.AlertType.INFORMATION);
                            break;
                        case Constantes.ERROR_CONSULTA:
                            Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                                "Por el momento no se puede procesar la solicitud", 
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
                break;
            default:
                Utilidades.mostrarDialogoSimple("Error de petición", 
                        "El sistema no está disponible por el momento", 
                        Alert.AlertType.ERROR);
                break;
        }
    }
    
    private void restarCantidadProductoAlmacenOrigen(int cantidadSolicitada) {
        Producto_Almacenado producto_almacenadoOrigen = new Producto_Almacenado();
        producto_almacenadoOrigen.setProducto_idProducto(idProductoSeleccionadoEnTabla);
        producto_almacenadoOrigen.setAlmacen_idAlmacen(idAlmacenOrigenSeleccionado);
        int cantidadProductoOriginal = obtenerCantidadDeProductoAlmacenado();
        producto_almacenadoOrigen.setCantidad(cantidadProductoOriginal - cantidadSolicitada);
        
        int resultado = Producto_AlmacenadoDAO.modificarProducto_Almacenado(producto_almacenadoOrigen);        
        
        switch (resultado) {
            case Constantes.OPERACION_EXITOSA:
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                    "Por el momento no se puede procesar la solicitud", 
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
    
    private int obtenerCantidadDeProductoAlmacenado() {
        int cantidadProductoAlmacenado = 0;
        Producto_Almacenado producto_Almacenado = Producto_AlmacenadoDAO.
            recuperarProductoAlmacenado(idProductoSeleccionadoEnTabla, 
                    idAlmacenOrigenSeleccionado);
            
            switch (producto_Almacenado.getCodigoRespuesta()){
                case Constantes.OPERACION_EXITOSA :
                    cantidadProductoAlmacenado = producto_Almacenado.getCantidad();
                    break;
                case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud", 
                            Alert.AlertType.ERROR);
                    break;
                case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Error de conexión", 
                            "Por el momento no hay conexión, intentelo más tarde", 
                            Alert.AlertType.ERROR);
                    break;
                case Constantes.SIN_RESULTADOS:
                    Utilidades.mostrarDialogoSimple("Sin resultados", 
                            "El producto seleccionado no cuenta con las suficientes existencias", 
                            Alert.AlertType.ERROR);
                    break;
                default:
                    Utilidades.mostrarDialogoSimple("Error de petición", 
                            "El sistema no está disponible por el momento", 
                            Alert.AlertType.ERROR);
                    break;
            }
        return cantidadProductoAlmacenado;
    }   
    
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("/sgapt/vistas/FXMLAdministracionInventarioProductos.fxml"));
        stagePrincipal.setTitle("Administración de inventario");
        stagePrincipal.show();
    }
    
    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }
    
}
