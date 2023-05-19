package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import sgapt.util.Utilidades;

public class FXMLAdministracionEmpleadosController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }

    @FXML
    private void clicIrConsultarEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLInformacionEmpleado.fxml"));
        stagePrincipal.setTitle("Información de empleado");
        stagePrincipal.show();
    }
    
    

    @FXML
    private void clicIrModifcarEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLModificarEmpleado.fxml"));
        stagePrincipal.setTitle("Modificación de empleado");
        stagePrincipal.show(); 
 
    }

    @FXML
    private void clicIrDarDeBajaEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLBajaEmpleado.fxml"));
        stagePrincipal.setTitle("Baja de empleado");
        stagePrincipal.show();      
    }

    @FXML
    private void clicIrDarDeAltaEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLFormularioEmpleado.fxml"));
        stagePrincipal.setTitle("Alta de empleado");
        stagePrincipal.show(); 
    }


}
