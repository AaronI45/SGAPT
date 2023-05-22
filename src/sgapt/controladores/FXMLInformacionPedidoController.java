package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sgapt.modelo.dao.LoteDAO;
import sgapt.modelo.pojo.LoteRespuesta;
import sgapt.modelo.pojo.Lote;
import sgapt.modelo.pojo.Pedido;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLInformacionPedidoController implements Initializable {

    @FXML
    private TableView<Lote> tvLotesPedido;
    @FXML
    private TableColumn colNumeroLote;
    @FXML
    private TableColumn colNombreProducto;
    @FXML
    private TableColumn colCantidadProducto;
    @FXML
    private TableColumn colPrecioLote;
    @FXML
    private Label lbPrecioProductos;
    @FXML
    private Label lbPrecioEnvio;
    @FXML
    private Label lbMontoTotal;
    @FXML
    private Label lbFechaPedido;
    @FXML
    private Label lbNumPedido;
    @FXML
    private Label lbDireccionEntrega;
    @FXML
    private Label lbFechaEntrega;
    @FXML
    private Label lbFechaEnvio;    
    private Pedido pedido;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbEstadoEnvio;
    @FXML
    private Pane paneEstadoEnvio;
    @FXML
    private Label lbEntregadoEl;
    @FXML
    private Label lbEnviadoEl;
    private ObservableList<Lote> lotes;   
        
    @Override        
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void inicializarInformacion(Pedido pedido) {
        this.pedido = pedido;
        cargarInformacionPedido();
        configurarTabla();
        cargarInformacionTabla();        
        validarEstadoEnvio();        
    }
    
    private void validarEstadoEnvio() {
        if (pedido.getEstadoRastreo().equals("sin enviar")) {
            lbEnviadoEl.visibleProperty().set(false);
            lbEntregadoEl.visibleProperty().set(false);
        } else if (pedido.getEstadoRastreo().equals("enviado") || 
                pedido.getEstadoRastreo().equals("con demora")) {
            lbEntregadoEl.visibleProperty().set(false);
        }
        establecerSemáforo();
    }
    
    private void establecerColumnasFijas() {
        colCantidadProducto.setResizable(false);
        colNombreProducto.setResizable(false);
        colNumeroLote.setResizable(false);
        colPrecioLote.setResizable(false);
    }
    
    private void establecerSemáforo() {
        switch (pedido.getEstadoRastreo())
        {
            case "sin enviar":
                paneEstadoEnvio.setStyle("-fx-background-color: #ffc93c");
                break;
            case "enviado":
                paneEstadoEnvio.setStyle("-fx-background-color: #4A90E2");
                break;
            case "con demora":
                paneEstadoEnvio.setStyle("-fx-background-color: #D0021B");
                break;
            case "entregado":
                paneEstadoEnvio.setStyle("-fx-background-color: #12CC48");
                break;
            default:
                break;
        }
    }    
    
    private void configurarTabla() {
        colNumeroLote.setCellValueFactory(new PropertyValueFactory("numeroDeLote"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colPrecioLote.setCellValueFactory(new PropertyValueFactory("precioLote"));
        establecerColumnasFijas();
    }
    
    private void cargarInformacionPedido(){
        lbFechaPedido.setText(pedido.getFechaPedido());
        lbNumPedido.setText(pedido.getNumPedido());
        lbDireccionEntrega.setText(pedido.getDireccionEntrega());
        lbPrecioProductos.setText(String.valueOf(pedido.getPrecioProductos()));
        lbPrecioEnvio.setText(String.valueOf(pedido.getPrecioEnvio()));
        lbMontoTotal.setText(String.valueOf(pedido.getMontoTotal()));
        lbEstadoEnvio.setText(pedido.getEstadoRastreo());
        lbFechaEnvio.setText(pedido.getFechaEnvio());
        lbFechaEntrega.setText(pedido.getFechaEntrega());
    }
    
    
    private void cargarInformacionTabla() {
        lotes = FXCollections.observableArrayList();
        LoteRespuesta respuestaBD = LoteDAO.
                obtenerInformacionLotePorPedido(pedido.getIdPedido());
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
                        "Lo sentimos, por el momento no hay conexión " + 
                        "para poder cargar la información", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                        "Hubo un error al cargar la información, por favor inténtelo más tarde", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    lotes.addAll(respuestaBD.getLotes());
                    tvLotesPedido.setItems(lotes);
                break;
        }
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPedidos.fxml"));
        stagePrincipal.setTitle("Administracion de pedidos");
        stagePrincipal.show();
    }

}
