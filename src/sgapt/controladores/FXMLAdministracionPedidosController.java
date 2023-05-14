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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
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
    private TableColumn colNombreProveedor;
    @FXML
    private TableColumn colFechaEntrega;
    @FXML
    private TableColumn colCiudadEntrega;    
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
        colNumeroPedido.setCellValueFactory(new PropertyValueFactory("idPedido")); //nombre de atribut en pojo
        colNombreProveedor.setCellValueFactory(new PropertyValueFactory("nombreProveedor"));
        colFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));
        colCiudadEntrega.setCellValueFactory(new PropertyValueFactory("ciudadEntrega"));
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
                    ponerPedidoSeleccionado();
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
    
    private void ponerPedidoSeleccionado() {
        final Pedido pedido = getTablaPedidosSeleccionada();
        posicionPedidoEnTabla = pedidos.indexOf(pedido) + 1;
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
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
    }

    @FXML
    private void clicBtnConsultar(ActionEvent event) {
        Stage escenarioInformacionPedido = new Stage();
        Scene esceneInformacionPedido = Utilidades.inicializarEscena("vistas/FXMLInformacionPedido.fxml");        
        escenarioInformacionPedido.setScene(esceneInformacionPedido);
        escenarioInformacionPedido.setTitle("Información de pedido");
        escenarioInformacionPedido.initModality(Modality.APPLICATION_MODAL);
        escenarioInformacionPedido.showAndWait();
        
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }
    
}
