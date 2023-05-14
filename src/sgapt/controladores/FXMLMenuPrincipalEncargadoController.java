/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLMenuPrincipalEncargadoController implements Initializable {

    @FXML
    private Label lbTitulo;

    /**
     * Initializes the controller class.
     */
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
    private void clicAdministrarPromociones(ActionEvent event) {
        irPantallaAministracionPromociones();
    }
}
