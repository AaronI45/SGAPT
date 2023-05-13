package sgapt.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sgapt.modelo.dao.PromocionDAO;
import sgapt.modelo.pojo.Promocion;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLFormularioPromocionController implements Initializable {

    @FXML
    private TextField tfTipo;
    @FXML
    private TextField tfFechaInicio;
    @FXML
    private TextField tfFechaFin;
    @FXML
    private Label lbTitulo;
    @FXML
    private TextField tfIdPromocion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        
    }
    
     private void validarCamposRegistros(){
        String tipoPromocion = tfTipo.getText();
        String fechInicio = tfFechaInicio.getText();
        String fechFin = tfFechaFin.getText();
        
        //TODO Validaciones
        Promocion promocionValidada = new Promocion();
        promocionValidada.setTipoPromocion(tipoPromocion);
        promocionValidada.setFechaInicio(fechInicio);
        promocionValidada.setFechaFin(fechFin);
        registrarPromocion(promocionValidada);
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    } 
    
    private void registrarPromocion(Promocion promocionRegistro){
        int codigoRespuesta = PromocionDAO.guardarPromocion(promocionRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El alumno no pudo ser guardadp debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", "La información del alumno no puede ser guardada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Alumno registrado", "La información del alumno fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
}
