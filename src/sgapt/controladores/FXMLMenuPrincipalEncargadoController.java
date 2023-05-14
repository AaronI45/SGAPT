package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sgapt.util.Utilidades;

public class FXMLMenuPrincipalEncargadoController implements Initializable {

    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void clicAdmProm(ActionEvent event) {
        
    }
    
    private void irPantallaAministracionPromociones(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones.fxml"));
        escenarioBase.setTitle("Administracion promociones");
        escenarioBase.show();
    }

    @FXML
    private void clicBtnSalir(ActionEvent event) {
        boolean validarCierreSesion = Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", 
                "¿Está seguro de cerrar la sesión?");
        if (validarCierreSesion) {
            Node source = (Node) event.getSource();
            Stage stagePrincipal = (Stage) source.getScene().getWindow();
            stagePrincipal.setScene(Utilidades.inicializarEscena("/sgapt/vistas/FXMLInicioSesion.fxml"));
            stagePrincipal.setTitle("Inicio de sesión");
            stagePrincipal.show();
        }
    }

    @FXML
    private void clicBtnAdministrarPromociones(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones.fxml"));
        stagePrincipal.setTitle("Administración de promociones");
        stagePrincipal.show();
    }
}
