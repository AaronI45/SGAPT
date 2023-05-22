package sgapt.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sgapt.modelo.dao.AlmacenDAO;
import sgapt.modelo.dao.LoteDAO;
import sgapt.modelo.dao.PedidoDAO;
import sgapt.modelo.dao.ProveedorDAO;
import sgapt.modelo.pojo.Almacen;
import sgapt.modelo.pojo.AlmacenRespuesta;
import sgapt.modelo.pojo.Lote;
import sgapt.modelo.pojo.LoteRespuesta;
import sgapt.modelo.pojo.Pedido;
import sgapt.modelo.pojo.PedidoRespuesta;
import sgapt.modelo.pojo.Proveedor;
import sgapt.modelo.pojo.ProveedorRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLFormularioPedidoController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colNumLotePro;
    @FXML
    private TableColumn colNombreProductoPro;
    @FXML
    private TableColumn colCantidadPro;
    @FXML
    private TableColumn colPrecioPro;
    @FXML
    private TableColumn colNumLotePed;
    @FXML
    private TableColumn colNombreProductoPed;
    @FXML
    private TableColumn colCantidadPed;
    @FXML
    private TableColumn colPrecioPed;
    @FXML
    private ComboBox<Proveedor> cbProveedores;
    @FXML
    private ComboBox<Almacen> cbAlmacenes;
    @FXML
    private TextArea taUbicacionAlmacen;
    @FXML
    private TextArea taUbicacionProveedor;
    @FXML
    private TableView<Lote> tvLotesProveedor;
    @FXML
    private TableView<Lote> tvLotesPedido;
    @FXML
    private Button btnRealizarPedido;
    @FXML
    private Button btnGuardarCambios;
    @FXML
    private Label lbPrecioProductos;
    @FXML
    private Label lbPrecioTotal;
    
    private ObservableList<Almacen> almacenes;
    private ObservableList<Proveedor> proveedores;
    private ObservableList<Lote> lotesProveedor;
    private ObservableList<Lote> lotesPedido; 
    private ObservableList<Lote> lotesOriginalesProveedor;
    private ObservableList<Lote> lotesOriginalesPedido;
    
    private boolean esEdicion;
    private Pedido pedidoEdicion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListaAlmacenes();
        cargarListaProveedores();
        agregarListenerDeAlmacenes();
        agregarListenerDeProveedores();
        configurarTablasLotes();
        lotesPedido = FXCollections.observableArrayList();
        lbPrecioProductos.setText(String.valueOf(0.00));
        lbPrecioTotal.setText(String.valueOf(0.00));
    }   
    
    private void cargarListaAlmacenes() {
        almacenes = FXCollections.observableArrayList();
        AlmacenRespuesta ar = AlmacenDAO.recuperarAlmacenes();
        almacenes.addAll(ar.getAlmacenes());
        cbAlmacenes.setItems(almacenes);
    }
    
    private void agregarListenerDeAlmacenes() {
        cbAlmacenes.valueProperty().addListener(new ChangeListener<Almacen>(){
            @Override
            public void changed(ObservableValue<? extends Almacen> observable, 
                    Almacen oldValue, Almacen newValue) {
                if(newValue != null){
                    taUbicacionAlmacen.setText(newValue.getDireccion());
                }
            }
        });
    }
    
    private void cargarListaProveedores() {
        proveedores = FXCollections.observableArrayList();
        ProveedorRespuesta pr = ProveedorDAO.obtenerInformacionProveedor();
        proveedores.addAll(pr.getProveedores());
        cbProveedores.setItems(proveedores);
    }
    
    private void agregarListenerDeProveedores() {
        cbProveedores.valueProperty().addListener(new ChangeListener<Proveedor>(){
            @Override
            public void changed(ObservableValue<? extends Proveedor> observable, 
                    Proveedor oldValue, Proveedor newValue) {
                if(newValue != null){
                    taUbicacionProveedor.setText(newValue.getDireccion());
                    cargarLotesProveedor(newValue.getIdProveedor());
                }
            }
        });
    }
    
    private void configurarTablasLotes(){
        colCantidadPed.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colCantidadPro.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colNombreProductoPed.setCellValueFactory(new PropertyValueFactory("nombre"));
        colNombreProductoPro.setCellValueFactory(new PropertyValueFactory("nombre"));
        colNumLotePed.setCellValueFactory(new PropertyValueFactory("numeroDeLote"));
        colNumLotePro.setCellValueFactory(new PropertyValueFactory("numeroDeLote"));
        colPrecioPed.setCellValueFactory(new PropertyValueFactory("precioLote"));
        colPrecioPro.setCellValueFactory(new PropertyValueFactory("precioLote"));
    }
    
    private void cargarLotesProveedor(int idProveedor) {
        lotesProveedor = FXCollections.observableArrayList();
        LoteRespuesta lr = LoteDAO.obtenerInformacionLotePorProveedor(idProveedor);
        switch (lr.getCodigoRespuesta()){
                case Constantes.OPERACION_EXITOSA:
                    lotesProveedor.addAll(lr.getLotes());
                    tvLotesProveedor.setItems(lotesProveedor);
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
    
    public void inicializarInformacionFormulario(boolean esEdicion, Pedido pedido) {
        this.esEdicion = esEdicion;
        this.pedidoEdicion = pedido;
        
        if (esEdicion) {
            cargarInformacionEdicion();
            btnRealizarPedido.setVisible(false);
            cbProveedores.setDisable(true);
            lbTitulo.setText("Modificación de pedido");
            lotesOriginalesProveedor = FXCollections.observableArrayList();
            lotesOriginalesPedido = FXCollections.observableArrayList();
            lotesOriginalesProveedor.setAll(lotesProveedor);
            lotesOriginalesPedido.setAll(lotesPedido);
            lbPrecioProductos.setText(String.valueOf(pedidoEdicion.getPrecioProductos()));
            lbPrecioTotal.setText(String.valueOf(pedidoEdicion.getMontoTotal()));
        } else {
            btnGuardarCambios.setVisible(false);
            lbTitulo.setText("Formulario de pedido");
        }
        
    }
    
    private void cargarInformacionEdicion() {
        seleccionarProveedorPedidoEdicion();
        seleccionarAlmacenPedidoEdicion();
        cargarLotesPedidoEdicion();
    }
    
    private void cargarLotesPedidoEdicion() {
        LoteRespuesta lr = LoteDAO.obtenerInformacionLotePorPedido(
                pedidoEdicion.getIdPedido());
        switch (lr.getCodigoRespuesta()){
                case Constantes.OPERACION_EXITOSA:
                    lotesPedido.addAll(lr.getLotes());
                    tvLotesPedido.setItems(lotesPedido);
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
    
    private void seleccionarProveedorPedidoEdicion() {
        cbProveedores.getSelectionModel().selectFirst();
        
        while (cbProveedores.getSelectionModel().getSelectedItem() != null &&
                !cbProveedores.getSelectionModel().getSelectedItem().getNombre().
                equals(pedidoEdicion.getNombreProveedor()))
            cbProveedores.getSelectionModel().selectNext();
    }
    
    private void seleccionarAlmacenPedidoEdicion() {
        cbAlmacenes.getSelectionModel().selectFirst();
        
        while (cbAlmacenes.getSelectionModel().getSelectedItem() != null &&
                !cbAlmacenes.getSelectionModel().getSelectedItem().getDireccion().
                equals(pedidoEdicion.getDireccionEntrega())) {
            cbAlmacenes.getSelectionModel().selectNext();
        }
    }

    @FXML
    private void clicBtnRemover(ActionEvent event) {
        int posicion = tvLotesPedido.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            double precioProductos = Double.parseDouble(lbPrecioProductos.getText());
            precioProductos -= lotesPedido.get(posicion).getPrecioLote();
            lbPrecioProductos.setText(String.valueOf(precioProductos));
            lbPrecioTotal.setText(String.valueOf(precioProductos + 500.00));
            lotesProveedor.add(lotesPedido.get(posicion));
            lotesPedido.remove(posicion);
            if (lotesPedido.isEmpty() && esEdicion == false) 
                cbProveedores.setDisable(false);
        }
    }

    @FXML
    private void clicBtnAgregar(ActionEvent event) {
        int posicion = tvLotesProveedor.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            double precioProductos = Double.parseDouble(lbPrecioProductos.getText());
            precioProductos += lotesProveedor.get(posicion).getPrecioLote();
            lbPrecioProductos.setText(String.valueOf(precioProductos));
            lbPrecioTotal.setText(String.valueOf(precioProductos + 500.00));
            lotesPedido.add(lotesProveedor.get(posicion));
            lotesProveedor.remove(posicion);
            tvLotesPedido.setItems(lotesPedido);
            cbProveedores.setDisable(true);
        }
    }

    @FXML
    private void clicBtnRealizarPedido(ActionEvent event) {
        realizarValidacionesParaCrearPedido();
    }
    
    private void realizarValidacionesParaCrearPedido() {
        if (cbAlmacenes.getSelectionModel().getSelectedItem() != null) {
            if (!lotesPedido.isEmpty()) {
                boolean realizarPedido = Utilidades.mostrarDialogoConfirmacion(
                                "Realizar pedido", 
                                "¿Está seguro de que desea realizar el pedido?");
                if (realizarPedido) {                    
                    generarNuevoPedido();
                }
            } else {
                Utilidades.mostrarDialogoSimple("Selección necesaria", 
                        "Seleccione al menos un producto en su pedido", 
                        Alert.AlertType.WARNING);
            }
        } else {
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "Seleccione la sucursal de destino", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void generarNuevoPedido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String fechaPedido = now.format( formatter );
        
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setIdProveedor(cbProveedores.getSelectionModel().
                getSelectedItem().getIdProveedor());
        nuevoPedido.setIdAlmacen(cbAlmacenes.getSelectionModel().
                getSelectedItem().getIdAlmacen());
        nuevoPedido.setFechaPedido(fechaPedido);
        nuevoPedido.setMontoTotal(Double.parseDouble(lbPrecioTotal.getText()));
        nuevoPedido.setDireccionEntrega(taUbicacionAlmacen.getText());    
        nuevoPedido.setEstadoRastreo("sin enviar");
        nuevoPedido.setNumPedido(nuevoPedido.getIdProveedor() + "-" + fechaPedido + 
                "-" + nuevoPedido.getIdAlmacen());
        nuevoPedido.setPrecioProductos(Double.parseDouble(lbPrecioProductos.getText()));
        nuevoPedido.setPrecioEnvio(500.0);
        
        PedidoRespuesta resultadoPedido = PedidoDAO.guardarPedido(nuevoPedido);
        switch (resultadoPedido.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", 
                        "El pedido no pudo ser generado debido a un error de conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la generación de pedido",
                        "El pedido no pudo ser generado, por favor intentelo más tarde" , Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                int idPedido = resultadoPedido.getIdPedido();
                if (idPedido != 0) {
                    lotesPedido.forEach((lote) -> { 
                        LoteDAO.enlazarLoteAPedido(lote.getIdLote(), idPedido);
                    });
                    Utilidades.mostrarDialogoSimple("Pedido realizado", 
                            "Se ha realizado el pedido satisfactoriamente",
                            Alert.AlertType.INFORMATION);
                    limpiarInformacionPedidoRealizado();
                }
                break;
        }
        
    }
    
    private void limpiarInformacionPedidoRealizado() {
        lotesPedido.clear();
        lbPrecioProductos.setText(String.valueOf(0.00));
        lbPrecioTotal.setText(String.valueOf(0.00));
        tvLotesPedido.setItems(lotesPedido);
    }
    
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPedidos.fxml"));
        stagePrincipal.setTitle("Administracion de pedidos");
        stagePrincipal.show();
    }
    
    private void regresarPantallaAnterior() {
        Stage stagePrincipal = (Stage) lbTitulo.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPedidos.fxml"));
        stagePrincipal.setTitle("Administracion de pedidos");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnGuardarCambios(ActionEvent event) {
        if (!lotesPedido.isEmpty()) {
            boolean modificarPedido = Utilidades.mostrarDialogoConfirmacion(
                            "Realizar modificación", 
                            "¿Está seguro de que desea modificar la información del pedido?");
            if (modificarPedido) {                    
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate now = LocalDate.now();
                String fechaPedido = now.format( formatter );
                
                pedidoEdicion.setIdAlmacen(cbAlmacenes.getSelectionModel().
                        getSelectedItem().getIdAlmacen());
                pedidoEdicion.setFechaPedido(fechaPedido);
                pedidoEdicion.setMontoTotal(Double.parseDouble(lbPrecioTotal.getText()));
                pedidoEdicion.setDireccionEntrega(taUbicacionAlmacen.getText());    
                pedidoEdicion.setNumPedido(pedidoEdicion.getIdProveedor() + "-" + fechaPedido + 
                        "-" + pedidoEdicion.getIdAlmacen());
                pedidoEdicion.setPrecioProductos(Double.parseDouble(
                        lbPrecioProductos.getText()));
                
                actualizarLotesProveedorEditados();
                actualizarLotesPedidoEditados();
                
                int respuesta = PedidoDAO.modificarPedido(pedidoEdicion);
                switch (respuesta) {
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexion", 
                                "El pedido no pudo ser modificado debido a un error de conexión...", 
                                Alert.AlertType.ERROR);
                        break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error en la modificación del pedido",
                                "El pedido no pudo ser modificado, por favor intentelo más tarde" , Alert.AlertType.WARNING);
                        break;
                    case Constantes.OPERACION_EXITOSA:
                        Utilidades.mostrarDialogoSimple("Pedido modificado", 
                                "Se ha modificado el pedido satisfactoriamente",
                                Alert.AlertType.INFORMATION);
                        break;
                }
            }
        } else {
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "No puede dejar el pedido sin productos, seleccione al menos uno", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void actualizarLotesProveedorEditados() {
        for (Lote loteProveedor : lotesProveedor) {
            boolean loteSinEditar = false;
            for (Lote loteOriginal : lotesOriginalesProveedor) {
                if (loteProveedor.equals(loteOriginal)) {
                    loteSinEditar = true;
                }
            }
            if (!loteSinEditar) {
                LoteDAO.desenlazarLoteDePedido(loteProveedor.getIdLote(), 
                        pedidoEdicion.getIdPedido());
            }
        }
    }
    
    private void actualizarLotesPedidoEditados() {
        for (Lote lotePedido : lotesPedido) {
            boolean loteSinEditar = false;
            for (Lote loteOriginal : lotesOriginalesPedido) {
                if (lotePedido.equals(loteOriginal)) {
                    loteSinEditar = true;
                }
            }
            if (!loteSinEditar) {
                LoteDAO.enlazarLoteAPedido(lotePedido.getIdLote(), 
                        pedidoEdicion.getIdPedido());
            }
        }
    }
    
}
