package sgapt.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgapt.modelo.dao.PromocionDAO;
import static sgapt.modelo.dao.PromocionDAO.darDeBajaPromocion;
import sgapt.modelo.pojo.Promocion;
import sgapt.modelo.pojo.PromocionRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLAdministracionPromocionesController implements Initializable {
    @FXML
    private TableView<Promocion> tvPromocion;
    @FXML
    private TableColumn columnNomProd;
    @FXML
    private TableColumn columnFechaInicio;
    @FXML
    private TableColumn columnFechaFin;
    @FXML
    private TableColumn columnDescuento;
    @FXML
    private TableColumn columnPreDesc;
    
    private ObservableList<Promocion> promociones;
    @FXML
    private TableColumn columnPrecio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalEncargado1.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }
    
    /* private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }*/ 

    @FXML
    private void clicBtnCrearProm(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLFormularioPromocion1.fxml"));
        stagePrincipal.setTitle("Formulario de promoción");
        stagePrincipal.show();
    }
    
     private void configurarTabla() {
        columnNomProd.setCellValueFactory(new PropertyValueFactory("producto"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
        columnDescuento.setCellValueFactory(new PropertyValueFactory("porcentajeDescuento")); //nombre de atribut en pojo
        columnFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        columnFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        columnPreDesc.setCellValueFactory(new PropertyValueFactory("precioDescuento"));
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
        int posicion = tvPromocion.getSelectionModel().getSelectedIndex();
        if(posicion!= -1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación de readquisición");
                alert.setHeaderText("¿Está seguro de que desea realizar la readquisición?");

                ButtonType btnConfirmar = new ButtonType("Confirmar");
                ButtonType btnCancelar = new ButtonType("Cancelar");
                alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.isPresent() && result.get()== btnConfirmar){
                    expirar(promociones.get(posicion).getIdPromocion());
                    Utilidades.mostrarDialogoSimple("Promcion expirada", "La promcion ha expirado de manera correcta", 
                    Alert.AlertType.INFORMATION);
                }
                cargarInformacionTabla();
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona un alumno", "Selecciona el registro en la tabla del alumno para su edicion", 
                    Alert.AlertType.WARNING);
            }
    }
    
    private void expirar(int idPromocion){
        darDeBajaPromocion(idPromocion);
    }
    
}
