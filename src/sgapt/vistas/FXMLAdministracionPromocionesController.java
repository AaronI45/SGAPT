package sgapt.vistas;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgapt.modelo.dao.PromocionDAO;
import sgapt.modelo.pojo.Promocion;
import sgapt.modelo.pojo.PromocionRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLAdministracionPromocionesController implements Initializable {

    @FXML
    private TableColumn columnIdPromocion;
    @FXML
    private TableColumn columnPromocion;
    @FXML
    private TableColumn columnFechaInicio;
    @FXML
    private TableColumn columnFechaFin;
    @FXML
    private TableView<Promocion> tvPromocion;
    private ObservableList<Promocion> promociones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    /* private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }*/ 

    @FXML
    private void clicBtnCrearProm(ActionEvent event) {
        Stage escenarioFormulario = new Stage();
        escenarioFormulario.setScene(Utilidades.inicializarEscena("vistas/FXMLFormularioPromocion.fxml"));
        escenarioFormulario.setTitle("Formulario");
        escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
        escenarioFormulario.showAndWait();
    }
    
     private void configurarTabla() {
        columnIdPromocion.setCellValueFactory(new PropertyValueFactory("idPromocion"));
        columnPromocion.setCellValueFactory(new PropertyValueFactory("tipoPromocion")); //nombre de atribut en pojo
        columnFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        columnFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
    }
     
    private void cargarInformacionTabla() {
        promociones = FXCollections.observableArrayList();
        PromocionRespuesta respuestaBD = PromocionDAO.obtenerInformacionPromocion();
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
                    promociones.addAll(respuestaBD.getPromociones());
                    tvPromocion.setItems(promociones);
                break;
        }
    }

    @FXML
    private void clicBtnExpirar(ActionEvent event) {
    }
    
}
