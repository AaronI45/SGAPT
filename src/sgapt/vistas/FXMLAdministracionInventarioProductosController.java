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

public class FXMLAdministracionInventarioProductosController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIrAdminReadquisiciones(ActionEvent event) {
        Stage escenarioReadquisiciones = new Stage();
        Scene esceneAdminReadquisiciones = Utilidades.inicializarEscena("vistas/FXMLAdministracionPedidos.fxml");
        escenarioReadquisiciones.setScene(esceneAdminReadquisiciones);
        escenarioReadquisiciones.setTitle("Administración de pedidos");
        escenarioReadquisiciones.initModality(Modality.APPLICATION_MODAL);
        escenarioReadquisiciones.showAndWait();
    }

    @FXML
    private void clicIrConsultarProductos(ActionEvent event) {
        Stage escenarioReadquisiciones = new Stage();
        Scene esceneAdminReadquisiciones = Utilidades.inicializarEscena("vistas/FXMLInformacionInventario.fxml");
        escenarioReadquisiciones.setScene(esceneAdminReadquisiciones);
        escenarioReadquisiciones.setTitle("Administración de inventario");
        escenarioReadquisiciones.initModality(Modality.APPLICATION_MODAL);
        escenarioReadquisiciones.showAndWait();
    }

    @FXML
    private void clicIrEliminarProductos(ActionEvent event) {
        Stage escenarioReadquisiciones = new Stage();
        Scene esceneAdminReadquisiciones = Utilidades.inicializarEscena("vistas/FXMLEliminacionProductos.fxml");
        escenarioReadquisiciones.setScene(esceneAdminReadquisiciones);
        escenarioReadquisiciones.setTitle("Administración de inventario");
        escenarioReadquisiciones.initModality(Modality.APPLICATION_MODAL);
        escenarioReadquisiciones.showAndWait();        
    }

    @FXML
    private void clicVolver(ActionEvent event) {
    }
    
}
