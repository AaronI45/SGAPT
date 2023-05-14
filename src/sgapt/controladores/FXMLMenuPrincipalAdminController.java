package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sgapt.util.Utilidades;

public class FXMLMenuPrincipalAdminController implements Initializable {

    @FXML
    private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clicIrAdminPromociones(ActionEvent event) {
        Stage stagePrincipal = (Stage) btnSalir.getScene().getWindow();
        Scene esceneAdminPromociones = Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones.fxml");
        stagePrincipal.setScene(esceneAdminPromociones);
        stagePrincipal.setTitle("Administración de promociones");        
    }

    @FXML
    private void clicIrAdminEmpleados(ActionEvent event) {
        Stage stagePrincipal = (Stage) btnSalir.getScene().getWindow();
        Scene esceneAdminEmpleados = Utilidades.inicializarEscena("vistas/FXMLAdministracionEmpleados.fxml");
        stagePrincipal.setScene(esceneAdminEmpleados);
        stagePrincipal.setTitle("Administración de empleados");
    }

    @FXML
    private void clicIrAdminInventario(ActionEvent event) {
        Stage stagePrincipal = (Stage) btnSalir.getScene().getWindow();
        Scene escenaAdminInventario = Utilidades.inicializarEscena("/sgapt/vistas/FXMLAdministracionInventarioProductos.fxml");
        stagePrincipal.setScene(escenaAdminInventario);
        stagePrincipal.setTitle("Administración de inventario");
    }

    @FXML
    private void clicSalir(ActionEvent event) {
        boolean validarCierreSesion = Utilidades.mostrarDialogoConfirmacion("Cerrar sesión", 
                "¿Está seguro de cerrar la sesión?");
        if (validarCierreSesion) {
            Stage stagePrincipal = (Stage) btnSalir.getScene().getWindow();
            Scene escenaInicioSesion = Utilidades.inicializarEscena("/sgapt/vistas/FXMLInicioSesion.fxml");
            stagePrincipal.setScene(escenaInicioSesion);  
        }
    }    
}
