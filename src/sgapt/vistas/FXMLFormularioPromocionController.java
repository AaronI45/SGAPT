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
    @FXML
    private Label lbErrorTipo;
    @FXML
    private Label lbErrorFechaInicio;
    @FXML
    private Label lbErrorFechaFin;
    @FXML
    private Label lbErrorIdPromocion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCamposRegistros();
    }
    
     private void validarCamposRegistros(){
        String idPromocion =  tfIdPromocion.getText();
        String tipoPromocion = tfTipo.getText();
        String fechInicio = tfFechaInicio.getText();
        String fechFin = tfFechaFin.getText();
        boolean sonValidos=true;
        //TODO Validaciones
        if(idPromocion.isEmpty()){
            sonValidos=false;
            lbErrorIdPromocion.setText("El campo es obligatorio");
        }
        if(tipoPromocion.isEmpty()){
            sonValidos=false;
            lbErrorTipo.setText("El campo es obligatorio");
        }
        if(fechInicio.isEmpty()){
            sonValidos=false;
            lbErrorFechaInicio.setText("El campo es obligatorio");
        }
        if(fechFin.isEmpty()){
            sonValidos=false;
            lbErrorFechaFin.setText("El campo es obligatorio");
        }
        //
        Promocion promocionValidada = new Promocion();
        promocionValidada.setIdPromocion(Integer.parseInt(idPromocion));
        promocionValidada.setTipoPromocion(tipoPromocion);
        promocionValidada.setFechaInicio(fechInicio);
        promocionValidada.setFechaFin(fechFin);
        registrarPromocion(promocionValidada);
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    } 
    
    private void registrarPromocion(Promocion promocionRegistro){
        int codigoRespuesta = PromocionDAO.guardarPromocion(promocionRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "La promocion no pudo ser guardada debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", "La información de la promocion no puede ser guardada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Promocion registrada", "La información de la promocion fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
}
