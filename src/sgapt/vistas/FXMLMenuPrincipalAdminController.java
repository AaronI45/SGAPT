package sgapt.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgapt.util.Utilidades;

public class FXMLMenuPrincipalAdminController implements Initializable {

    @FXML
    private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIrAdminPromociones(ActionEvent event) {
        Stage escenarioPromociones = new Stage();
        Scene esceneAdminPromociones = Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones.fxml");
        escenarioPromociones.setScene(esceneAdminPromociones);
        escenarioPromociones.setTitle("Administración de promociones");
        escenarioPromociones.initModality(Modality.APPLICATION_MODAL);
        escenarioPromociones.showAndWait();
    }

    @FXML
    private void clicIrAdminEmpleados(ActionEvent event) {
        Stage escenarioEmpleados = new Stage();
        Scene esceneAdminEmpleados = Utilidades.inicializarEscena("vistas/FXMLAdministracionEmpleados.fxml");
        escenarioEmpleados.setScene(esceneAdminEmpleados);
        escenarioEmpleados.setTitle("Administración de empleados");
        escenarioEmpleados.initModality(Modality.APPLICATION_MODAL);
        escenarioEmpleados.showAndWait();
    }

    @FXML
    private void clicIrAdminInventario(ActionEvent event) {
        Stage escenarioInventario = new Stage();
        Scene esceneAdminInventario = Utilidades.inicializarEscena("vistas/FXMLAdministracionInventarioProductos.fxml");
        escenarioInventario.setScene(esceneAdminInventario);
        escenarioInventario.setTitle("Administración de inventario");
        escenarioInventario.initModality(Modality.APPLICATION_MODAL);
        escenarioInventario.showAndWait();
    }

    @FXML
    private void clicSalir(ActionEvent event) {
            boolean validarCierreSesion = Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", 
                "¿Está seguro de cerrar la sesión?");
        if (validarCierreSesion){
            try {
            Parent vista = FXMLLoader.load(getClass().getResource("/sgapt/vistas/FXMLInicioSesion.fxml"));
            Scene escenaInicioSesion = new Scene(vista);
            Stage stagePrincipal = (Stage) btnSalir.getScene().getWindow();
            stagePrincipal.setScene(escenaInicioSesion);
            } catch (IOException e) {
                Utilidades.mostrarDialogoSimple("Error", "No se puede mostrar la pantalla de inicio de sesión", 
                        Alert.AlertType.ERROR);;
            }  
        }
        
    }
    
    
}
