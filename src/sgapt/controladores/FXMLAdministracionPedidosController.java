package sgapt.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sgapt.modelo.dao.PedidoDAO;
import sgapt.modelo.dao.PedidoRespuesta;
import sgapt.modelo.pojo.Pedido;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLAdministracionPedidosController implements Initializable {

    @FXML
    private TableView<Pedido> tvPedidos;
    @FXML
    private TableColumn colNumeroPedido;
    @FXML
    private TableColumn colProveedor;
    @FXML
    private TableColumn colMontoTotal;
    @FXML
    private TableColumn colDirEntrega;
    @FXML
    private TableColumn colFechaPedido;
    @FXML
    private TableColumn colFechaEnvio;
    @FXML
    private TableColumn colEstadoRastreo;
    @FXML
    private TableColumn colFechaEntrega;
    
    private ObservableList<Pedido> pedidos;    
    private static int posicionPedidoEnTabla;

    public static int getPosicionPedidoEnTabla() {
        return posicionPedidoEnTabla;
    }

    public static void setPosicionPedidoEnTabla(int posicionPedidoEnTabla) {
        FXMLAdministracionPedidosController.posicionPedidoEnTabla = posicionPedidoEnTabla;
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        
        final ObservableList<Pedido> tablaPedidoSel = tvPedidos.getSelectionModel().getSelectedItems();
        tablaPedidoSel.addListener(selectorTablaPedidos);
    }    
    
    private void configurarTabla() {
        colNumeroPedido.setCellValueFactory(new PropertyValueFactory("idPedido"));
        colProveedor.setCellValueFactory(new PropertyValueFactory("nombreProveedor"));
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));
        colDirEntrega.setCellValueFactory(new PropertyValueFactory("direccionEntrega"));
        colFechaPedido.setCellValueFactory(new PropertyValueFactory("fechaPedido"));
        colFechaEnvio.setCellValueFactory(new PropertyValueFactory("fechaEnvio"));
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));
        colEstadoRastreo.setCellValueFactory(new PropertyValueFactory("estadoRastreo"));
        colMontoTotal.setCellValueFactory(new PropertyValueFactory("montoTotal"));        
    }
    
    private void cargarInformacionTabla() {
        pedidos = FXCollections.observableArrayList();
        PedidoRespuesta respuestaBD = PedidoDAO.obtenerInformacionPedido();
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder cargar la información", 
                    Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    pedidos.addAll(respuestaBD.getPedidos());
                    tvPedidos.setItems(pedidos);
                break;
        }
    }
    
    private final ListChangeListener<Pedido> selectorTablaPedidos = 
            new ListChangeListener<Pedido>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Pedido> c) {
                    recuperarIdPedidoSeleccionado();
                }
            };
    
    public Pedido getTablaPedidosSeleccionada() {
        if (tvPedidos != null) {
            List<Pedido> tabla = tvPedidos.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final Pedido pedidoSeleccionado = tabla.get(0);
                return pedidoSeleccionado;
            }
        }
        return null;
    }
    
    private void recuperarIdPedidoSeleccionado() {
        final Pedido pedido = getTablaPedidosSeleccionada();
        posicionPedidoEnTabla = pedido.getIdPedido();
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionInventarioProductos.fxml"));
        stagePrincipal.setTitle("Administración de inventario");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnRealizarPedido(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLProveedoresExternos.fxml"));
        stagePrincipal.setTitle("Proveedores externos");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLModificacionPedido.fxml"));
        stagePrincipal.setTitle("Modificación de pedido");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnConsultar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLInformacionPedido.fxml"));
        stagePrincipal.setTitle("Información de pedido");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }
    
}
