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
import sgapt.modelo.dao.Lote_AlmacenadoDAO;
import sgapt.modelo.dao.SucursalDAO;
import sgapt.modelo.pojo.ProductoRespuesta;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Lote_Almacenado;
import sgapt.modelo.pojo.Sucursal;
import sgapt.modelo.pojo.SucursalRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLAdministracionReadquisicionesController implements Initializable {

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
    private TableColumn colNumeroLote;
    @FXML
    private TextField tfCantidad;
    @FXML
    private ComboBox<Sucursal> cbSucursalOrigen;
    @FXML
    private ComboBox<Sucursal> cbSucursalDestino;
    
    private ObservableList<Producto> productos;
    private ObservableList<Sucursal> sucursales;
    
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
        colNumeroLote.setCellValueFactory(new PropertyValueFactory("numeroLote"));
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
        ProductoRespuesta pr = ProductoDAO.recuperarProductosEnSucursal(
                sucursalSeleccionada);
        switch (pr.getCodigoRespuesta()){
                case Constantes.OPERACION_EXITOSA:
                    for (Producto producto : pr.getProductos()){
                        if(producto.getDisponibilidad().equals("disponible")){
                           productos.add(producto);
                        }
                    }
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
        Sucursal sucursalOrigen=  cbSucursalOrigen.getSelectionModel().getSelectedItem();
        Sucursal sucursalDestino = cbSucursalDestino.getSelectionModel().getSelectedItem();
        
        if (sucursalOrigen != null && sucursalDestino != null) {
            if (sucursalOrigen.getIdSucursal()== sucursalDestino.getIdSucursal()) {
                Utilidades.mostrarDialogoSimple("Error", "La sucursal de origen no puede ser " + 
                        "la misma que la sucursal de destino. Por favor, cambie la selección.",
                        Alert.AlertType.WARNING);
            } else {                
                int posicion = tvProductos.getSelectionModel().getSelectedIndex();
                    if (posicion != -1) {
                        boolean realizarReadquisicion = Utilidades.mostrarDialogoConfirmacion(
                                "Readquisición de productos", 
                                "¿Está seguro de que desea realizar la readquisición?");
                        if (realizarReadquisicion) {                    
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
            int cantidadExistencias = obtenerCantidadDeProductoAlmacenado();
            if (cantidadExistencias > 0) {
                if (cantidadReadquisicion <= cantidadExistencias) {
                    realizarActualizacionDeProductosEnFarmacias(cantidadReadquisicion, 
                            cantidadExistencias);
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
    
    private void realizarActualizacionDeProductosEnFarmacias(int cantidadReadquisicion, 
            int cantidadExistencias) {
        Lote_Almacenado lote_Almacenado = Lote_AlmacenadoDAO.recuperarLoteAlmacenado(
                tvProductos.getSelectionModel().getSelectedItem().getIdLote(), 
                cbSucursalDestino.getSelectionModel().getSelectedItem().getIdSucursal());
        
        restarCantidadProductoFarmaciaOrigen(cantidadReadquisicion, cantidadExistencias);
        
        switch (lote_Almacenado.getCodigoRespuesta()){
            case Constantes.OPERACION_EXITOSA: 
                lote_Almacenado.setCantidad(cantidadReadquisicion + lote_Almacenado.getCantidad());
                    int resultado = Lote_AlmacenadoDAO.modificarLote_Almacenado(lote_Almacenado);
                
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
                        lote_Almacenado.setCantidad(cantidadReadquisicion);
                        lote_Almacenado.setLote_idLote(tvProductos.getSelectionModel().
                                getSelectedItem().getIdLote());
                        lote_Almacenado.setAlmacen_idAlmacen(cbSucursalDestino.
                                getSelectionModel().getSelectedItem().getIdSucursal());
                        int respuesta = Lote_AlmacenadoDAO.guardarLote_Almacenado(lote_Almacenado);
                    
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
    
    private void restarCantidadProductoFarmaciaOrigen(int cantidadSolicitada, 
            int cantidadExistencias) {
        Lote_Almacenado lote_almacenado = new Lote_Almacenado();
        lote_almacenado.setLote_idLote(tvProductos.
                getSelectionModel().getSelectedItem().getIdLote());
        lote_almacenado.setAlmacen_idAlmacen(cbSucursalOrigen.
                getSelectionModel().getSelectedItem().getIdSucursal());
        lote_almacenado.setCantidad(cantidadExistencias - cantidadSolicitada);
        
        int resultado = Lote_AlmacenadoDAO.modificarLote_Almacenado(lote_almacenado);
        
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
        Lote_Almacenado lote_Almacenado = Lote_AlmacenadoDAO.
                recuperarLoteAlmacenado(tvProductos.getSelectionModel().
                    getSelectedItem().getIdProducto(), 
                    cbSucursalOrigen.getSelectionModel().getSelectedItem().getIdSucursal());
            
        switch (lote_Almacenado.getCodigoRespuesta()) {
            case Constantes.OPERACION_EXITOSA :
                cantidadProductoAlmacenado = lote_Almacenado.getCantidad();
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
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }
    
    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }
    
}