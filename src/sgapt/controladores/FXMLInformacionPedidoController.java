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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sgapt.modelo.dao.LoteDAO;
import sgapt.modelo.dao.LoteRespuesta;
import sgapt.modelo.pojo.Lote;
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
    private TableColumn colTipoProducto;
    @FXML
    private TableColumn colCantidadProducto;
    @FXML
    private TableColumn colFechaCaducidad;
    @FXML
    private TableColumn colPrecioLote;
    
    private ObservableList<Lote> lotes;   
        
    @Override        
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla() {
        colNumeroLote.setCellValueFactory(new PropertyValueFactory("numeroDeLote"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProducto"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory("fechaDeCaducidad"));
        colCantidadProducto.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colPrecioLote.setCellValueFactory(new PropertyValueFactory("precioLote"));
    }
    
    private void cargarInformacionTabla() {
        lotes = FXCollections.observableArrayList();
        LoteRespuesta respuestaBD = LoteDAO.obtenerInformacionLotePorPedido(
                FXMLAdministracionPedidosController.getPosicionPedidoEnTabla());
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
