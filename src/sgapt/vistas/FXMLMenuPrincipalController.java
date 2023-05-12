package sgapt.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgapt.util.Utilidades;

public class FXMLMenuPrincipalController implements Initializable {

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
    
}
