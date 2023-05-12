package sgapt.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
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
    
}
