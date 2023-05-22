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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLFormularioProductoController implements Initializable {

    @FXML
    private ComboBox<?> cbRequiereReceta;
    @FXML
    private ComboBox<?> cbTipoProducto;
    @FXML
    private TextField tfNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicAgregarImagen(MouseEvent event) {
        System.out.println("hola");
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        
    }
    
}
