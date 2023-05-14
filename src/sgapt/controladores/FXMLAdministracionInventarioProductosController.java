package sgapt.controladores;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Tipo de proveedor");
        alert.setHeaderText("Seleccione el tipo de proveedor");

        ButtonType btnInterno = new ButtonType("Interno");
        ButtonType btnExterno = new ButtonType("Externo");
        alert.getButtonTypes().setAll(btnInterno, btnExterno);

        Optional<ButtonType> result = alert.showAndWait();
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        if (result.isPresent() && result.get() == btnExterno) {            
            stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPedidos.fxml"));
            stagePrincipal.setTitle("Administracion de pedidos");
            stagePrincipal.show();
        } else {
            stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLProveedoresInternos.fxml"));
            stagePrincipal.setTitle("Proveedores internos");
            stagePrincipal.show();
        }
                
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
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }
    
}