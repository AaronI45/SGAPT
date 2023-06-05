package sgapt.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sgapt.modelo.dao.LoteDAO;
import sgapt.modelo.dao.PedidoDAO;
import sgapt.modelo.dao.ProveedorDAO;
import sgapt.modelo.dao.SucursalDAO;
import sgapt.modelo.pojo.Lote;
import sgapt.modelo.pojo.LoteRespuesta;
import sgapt.modelo.dao.Lote_PedidoDAO;
import sgapt.modelo.pojo.Pedido;
import sgapt.modelo.pojo.PedidoRespuesta;
import sgapt.modelo.pojo.Proveedor;
import sgapt.modelo.pojo.ProveedorRespuesta;
import sgapt.modelo.pojo.Sucursal;
import sgapt.modelo.pojo.SucursalRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLFormularioPedidoController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colNumLotesPro;
    @FXML
    private TableColumn colNombreProductoPro;
    @FXML
    private TableColumn colCantidadPro;
    @FXML
    private TableColumn colPrecioPro;
    @FXML
    private TableColumn colNumLotesPed;
    @FXML
    private TableColumn colNombreProductoPed;
    @FXML
    private TableColumn colCantidadPed;
    @FXML
    private TableColumn colPrecioPed;
    @FXML
    private ComboBox<Proveedor> cbProveedores;
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
    
    private ObservableList<Sucursal> sucursales;
    private ObservableList<Proveedor> proveedores;
    private ObservableList<Lote> lotesProveedor;
    private ObservableList<Lote> lotesPedido; 
    private ArrayList<Lote> lotesOriginalesProveedor;
    
    private boolean esEdicion;
    private Pedido pedidoEdicion;
    @FXML
    private ComboBox<Sucursal> cbSucursales;
    @FXML
    private TextArea taUbicacionSucursal;
    @FXML
    private TextField tfCantidadLotes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListaSucursales();
        cargarListaProveedores();
        agregarListenerDeSucursales();
        agregarListenerDeProveedores();
        configurarTablasLotes();
        lotesPedido = FXCollections.observableArrayList();
        lbPrecioProductos.setText(String.valueOf(0.00));
        lbPrecioTotal.setText(String.valueOf(0.00));
    }   
    
    private void cargarListaSucursales() {
        sucursales = FXCollections.observableArrayList();
        SucursalRespuesta sr = SucursalDAO.recuperarSucursales();
        sucursales.addAll(sr.getSucursales());
        cbSucursales.setItems(sucursales);
    }
    
    private void agregarListenerDeSucursales() {
        cbSucursales.valueProperty().addListener(new ChangeListener<Sucursal>(){
            @Override
            public void changed(ObservableValue<? extends Sucursal> observable, 
                    Sucursal oldValue, Sucursal newValue) {
                if(newValue != null){
                    taUbicacionSucursal.setText(newValue.getDireccion());
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
        colNumLotesPed.setCellValueFactory(new PropertyValueFactory("cantidadLotes"));
        colNumLotesPro.setCellValueFactory(new PropertyValueFactory("cantidadLotes"));
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
            lotesOriginalesProveedor = new ArrayList();
            
            for (Lote loteProveedor : lotesProveedor) {
                Lote copiaLoteProveedor = new Lote();
                copiaLoteProveedor.setCantidad(loteProveedor.getCantidad());
                copiaLoteProveedor.setCantidadLotes(loteProveedor.getCantidadLotes());
                copiaLoteProveedor.setFechaDeCaducidad(loteProveedor.getFechaDeCaducidad());
                copiaLoteProveedor.setIdLote(loteProveedor.getIdLote());
                copiaLoteProveedor.setNombre(loteProveedor.getNombre());
                copiaLoteProveedor.setNumeroDeLote(loteProveedor.getNumeroDeLote());
                copiaLoteProveedor.setPrecioLote(loteProveedor.getPrecioLote());
                copiaLoteProveedor.setTipoProducto(loteProveedor.getTipoProducto());
                
                lotesOriginalesProveedor.add(copiaLoteProveedor);
            }
            
            lbPrecioProductos.setText(String.valueOf(pedidoEdicion.getPrecioProductos()));
            lbPrecioTotal.setText(String.valueOf(pedidoEdicion.getMontoTotal()));
        } else {
            btnGuardarCambios.setVisible(false);
            lbTitulo.setText("Formulario de pedido");
        }
        
    }
    
    
    
    
    private void cargarInformacionEdicion() {
        seleccionarProveedorPedidoEdicion();
        seleccionarSucursalPedidoEdicion();
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
    
    private void seleccionarSucursalPedidoEdicion() {
        cbSucursales.getSelectionModel().selectFirst();
        
        while (cbSucursales.getSelectionModel().getSelectedItem() != null &&
                !cbSucursales.getSelectionModel().getSelectedItem().getDireccion().
                equals(pedidoEdicion.getDireccionEntrega())) {
            cbSucursales.getSelectionModel().selectNext();
        }
    }

    @FXML
    private void clicBtnRemover(ActionEvent event) {
        int posicionEnPedido = tvLotesPedido.getSelectionModel().getSelectedIndex();
        if (tfCantidadLotes.getText().length() > 0) {
            int cantidadLotesDevolucion = Integer.parseInt(tfCantidadLotes.getText());

            if (posicionEnPedido != -1) {
                if (cantidadLotesDevolucion <= lotesPedido.get(posicionEnPedido).getCantidadLotes()) {
                    Lote loteSeleccionado = lotesPedido.get(posicionEnPedido);
                    
                    double precioProductos = Double.parseDouble(lbPrecioProductos.getText());
                    precioProductos -= (lotesPedido.get(posicionEnPedido).getPrecioLote() * 
                            cantidadLotesDevolucion);
                    lbPrecioProductos.setText(String.valueOf(precioProductos));
                    lbPrecioTotal.setText(String.valueOf(precioProductos + 500.00));

                    boolean loteExisteEnProveedor = false;
                    int posicionEnProveedor = 0;
                    for (Lote loteProveedor : lotesProveedor) {
                        if (loteSeleccionado.getIdLote() == loteProveedor.getIdLote()) {
                            loteExisteEnProveedor = true;
                            break;
                        } else {
                            posicionEnProveedor++;
                        }
                    }

                    if (loteExisteEnProveedor) {
                        lotesProveedor.get(posicionEnProveedor).setCantidadLotes(
                            lotesProveedor.get(posicionEnProveedor).getCantidadLotes() + cantidadLotesDevolucion);
                    }

                    if (!loteExisteEnProveedor) {
                        lotesProveedor.add(loteSeleccionado);
                    }

                    if (cantidadLotesDevolucion == loteSeleccionado.getCantidadLotes()) {
                        lotesPedido.remove(posicionEnPedido);
                        if (lotesPedido.isEmpty() && esEdicion == false) 
                            cbProveedores.setDisable(false);
                    } else if (cantidadLotesDevolucion > 0 && 
                            cantidadLotesDevolucion < loteSeleccionado.getCantidadLotes()) {
                        lotesPedido.get(posicionEnPedido).setCantidadLotes(
                            lotesPedido.get(posicionEnPedido).getCantidadLotes() - cantidadLotesDevolucion);
                    }
                    
                    tvLotesPedido.setItems(lotesPedido);
                    tvLotesPedido.refresh();
                    tvLotesProveedor.setItems(lotesProveedor);
                    tvLotesProveedor.refresh();
                } else {
                    Utilidades.mostrarDialogoSimple("Error en cantidad de lotes", 
                            "La cantidad de lotes seleccionada es menor de la solicitada, " + 
                            "establezca una cantidad dentro del rango de existencias", 
                            Alert.AlertType.WARNING);
                }
            }
        } else {
            Utilidades.mostrarDialogoSimple("Cantidad de lotes necesaria", 
                    "Debe seleccionar la cantidad de lotes para agregar al pedido", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnAgregar(ActionEvent event) {
        int posicionEnProveedor = tvLotesProveedor.getSelectionModel().getSelectedIndex();
        if (tfCantidadLotes.getText().length() > 0) {
            int cantidadLotesAgregar = Integer.parseInt(tfCantidadLotes.getText());
            
            if (posicionEnProveedor != -1) {
                if (cantidadLotesAgregar <= lotesProveedor.get(posicionEnProveedor).getCantidadLotes()) {
                    Lote loteSeleccionado = new Lote();
                    loteSeleccionado.setIdLote(lotesProveedor.get(posicionEnProveedor).getIdLote());
                    loteSeleccionado.setCantidad(lotesProveedor.get(posicionEnProveedor).getCantidad());
                    loteSeleccionado.setCantidadLotes(cantidadLotesAgregar);
                    loteSeleccionado.setFechaDeCaducidad(lotesProveedor.get(posicionEnProveedor).getFechaDeCaducidad());
                    loteSeleccionado.setNombre(lotesProveedor.get(posicionEnProveedor).getNombre());
                    loteSeleccionado.setNumeroDeLote(lotesProveedor.get(posicionEnProveedor).getNumeroDeLote());
                    loteSeleccionado.setPrecioLote(lotesProveedor.get(posicionEnProveedor).getPrecioLote());
                    loteSeleccionado.setTipoProducto(lotesProveedor.get(posicionEnProveedor).getTipoProducto());
                    
                    double precioProductos = Double.parseDouble(lbPrecioProductos.getText());
                    precioProductos += (lotesProveedor.get(posicionEnProveedor).getPrecioLote() * 
                            cantidadLotesAgregar);
                    lbPrecioProductos.setText(String.valueOf(precioProductos));
                    lbPrecioTotal.setText(String.valueOf(precioProductos + 500.00));
                    
                    boolean loteExisteEnPedido = false;
                    int posicionEnPedido = 0;
                    
                    for (Lote lotePedido : lotesPedido) {
                        if (loteSeleccionado.getIdLote() == lotePedido.getIdLote()) {
                            loteExisteEnPedido = true;
                            break;
                        } else
                            posicionEnPedido++;
                    }
                    
                    if (loteExisteEnPedido) {
                        lotesPedido.get(posicionEnPedido).setCantidadLotes(
                            lotesPedido.get(posicionEnPedido).getCantidadLotes() + cantidadLotesAgregar);
                        tvLotesPedido.setItems(lotesPedido);
                    }
                    
                    if (!loteExisteEnPedido) {
                        lotesPedido.add(loteSeleccionado);
                        
                    }
                    
                    if (cantidadLotesAgregar > 0 && 
                            cantidadLotesAgregar <= lotesProveedor.get(posicionEnProveedor).getCantidadLotes()) {
                        lotesProveedor.get(posicionEnProveedor).setCantidadLotes(
                            lotesProveedor.get(posicionEnProveedor).getCantidadLotes() - cantidadLotesAgregar);
                    }
                    
                    tvLotesPedido.setItems(lotesPedido);
                    tvLotesPedido.refresh();
                    tvLotesProveedor.setItems(lotesProveedor);
                    tvLotesProveedor.refresh();
                    cbProveedores.setDisable(true);
                    
                } else {
                    Utilidades.mostrarDialogoSimple("Error en cantidad de lotes", 
                            "La cantidad de lotes existentes es menor de la solicitada, " + 
                            "establezca una cantidad dentro del rango de existencias", 
                            Alert.AlertType.WARNING);
                }
            }
        } else {
            Utilidades.mostrarDialogoSimple("Cantidad de lotes necesaria", 
                    "Debe seleccionar la cantidad de lotes para agregar al pedido", 
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicBtnRealizarPedido(ActionEvent event) {
        if (cbSucursales.getSelectionModel().getSelectedItem() != null) {
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
        nuevoPedido.setIdSucursal(cbSucursales.getSelectionModel().
                getSelectedItem().getIdSucursal());
        nuevoPedido.setFechaPedido(fechaPedido);
        nuevoPedido.setMontoTotal(Double.parseDouble(lbPrecioTotal.getText()));
        nuevoPedido.setDireccionEntrega(taUbicacionSucursal.getText());    
        nuevoPedido.setEstadoRastreo("sin enviar");
        nuevoPedido.setNumPedido(nuevoPedido.getIdProveedor() + "-" + fechaPedido + 
                "-" + nuevoPedido.getIdSucursal());
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
                        Lote_PedidoDAO.agregarLoteAPedido(lote.getIdLote(), 
                                idPedido, lote.getCantidadLotes());
                        
                    });
                    lotesProveedor.forEach((lote) -> {
                        LoteDAO.modificarLote(lote);
                    });
                    Utilidades.mostrarDialogoSimple("Pedido realizado", 
                            "Se ha realizado el pedido satisfactoriamente",
                            Alert.AlertType.INFORMATION);
                    limpiarInformacionPedidoRealizado();
                    regresarPantallaAnterior();
                }
                break;
        }
        
    }
    
    private void limpiarInformacionPedidoRealizado() {
        lotesPedido.clear();
        lbPrecioProductos.setText(String.valueOf(0.00));
        lbPrecioTotal.setText(String.valueOf(0.00));
        tvLotesPedido.setItems(lotesPedido);
        tfCantidadLotes.setText("");
    }
    
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        regresarPantallaAnterior();
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
                
                pedidoEdicion.setIdSucursal(cbSucursales.getSelectionModel().
                        getSelectedItem().getIdSucursal());
                pedidoEdicion.setFechaPedido(fechaPedido);
                pedidoEdicion.setMontoTotal(Double.parseDouble(lbPrecioTotal.getText()));
                pedidoEdicion.setDireccionEntrega(taUbicacionSucursal.getText());    
                pedidoEdicion.setNumPedido(pedidoEdicion.getIdProveedor() + "-" + fechaPedido + 
                        "-" + pedidoEdicion.getIdSucursal());
                pedidoEdicion.setPrecioProductos(Double.parseDouble(
                        lbPrecioProductos.getText()));
                
                actualizarLotesEditados();
                
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
                        regresarPantallaAnterior();
                        break;
                }
            }
        } else {
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "No puede dejar el pedido sin productos, seleccione al menos uno", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void actualizarLotesEditados() {
        for (Lote loteProveedor : lotesProveedor) {
            boolean cantidadEditada = false;
            
            for (Lote loteOriginal : lotesOriginalesProveedor) {
                if (loteProveedor.getIdLote() == loteOriginal.getIdLote()) {
                    if (loteProveedor.getCantidadLotes() != loteOriginal.getCantidadLotes()) {
                        cantidadEditada = true;
                        break;
                    }
                }
            }
            
            if (cantidadEditada) {
                LoteDAO.modificarLote(loteProveedor);
                boolean loteEstaEnTablaPedido = false;
                int cantidadLoteEnPedido = 0;
                for (Lote lotePedido : lotesPedido) {
                    if (loteProveedor.getIdLote() == lotePedido.getIdLote()) {
                        loteEstaEnTablaPedido = true;
                        cantidadLoteEnPedido = lotePedido.getCantidadLotes();
                    }
                }
                
                if (!loteEstaEnTablaPedido) {
                    int filasAfectadas = Lote_PedidoDAO.eliminarLoteDePedido(
                            loteProveedor.getIdLote(), pedidoEdicion.getIdPedido());
                } else if (loteEstaEnTablaPedido) {
                    int filasAfectadas = Lote_PedidoDAO.
                            modificarCantidadLotesDePedido(loteProveedor.getIdLote(), 
                                    pedidoEdicion.getIdPedido(), 
                                    cantidadLoteEnPedido);
                    if (filasAfectadas == Constantes.SIN_RESULTADOS) {
                        Lote_PedidoDAO.agregarLoteAPedido(loteProveedor.getIdLote(), 
                            pedidoEdicion.getIdPedido(), cantidadLoteEnPedido);
                    }
                }
                
            }
        }
    }
    
    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }
    
}
