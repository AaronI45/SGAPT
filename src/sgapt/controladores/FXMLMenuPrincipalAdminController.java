package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones.fxml"));
        stagePrincipal.setTitle("Administración de promociones");
        stagePrincipal.show();
    }

    @FXML
    private void clicIrAdminEmpleados(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionEmpleados.fxml"));
        stagePrincipal.setTitle("Administración de empleados");
        stagePrincipal.show();        
    }

    @FXML
    private void clicIrAdminInventario(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("/sgapt/vistas/FXMLAdministracionInventarioProductos.fxml"));
        stagePrincipal.setTitle("Administración de inventario");
        stagePrincipal.show();
    }

    @FXML
    private void clicSalir(ActionEvent event) {
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
}
