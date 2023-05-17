package sgapt.controladores;

import java.net.URL;
import java.util.List;
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
import sgapt.modelo.dao.ProveedorDAO;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Proveedor;
import sgapt.modelo.pojo.ProveedorRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLProveedoresExternosController implements Initializable {

    @FXML
    private TableView<Proveedor> tvProveedores;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colCiudad;
    @FXML
    private TableColumn colDireccion;

    private ObservableList<Proveedor> listaProveedores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colCiudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));        
    }
    
    private void cargarInformacionTabla() {
        listaProveedores = FXCollections.observableArrayList();
        ProveedorRespuesta respuestaBD = ProveedorDAO.obtenerInformacionProveedor();
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.OPERACION_EXITOSA:
                    listaProveedores.addAll(respuestaBD.getProveedores());
                    tvProveedores.setItems(listaProveedores);
                break;
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

    @FXML
    private void clicBtnSeleccionarProductos(ActionEvent event) {
        final Proveedor proveedorSeleccionado = tvProveedores.
                getSelectionModel().getSelectedItems().get(0);
        proveedorSeleccionado.getIdProveedor();
    }
    
}
