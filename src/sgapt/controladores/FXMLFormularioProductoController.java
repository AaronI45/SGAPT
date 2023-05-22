/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sgapt.controladores;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import sgapt.modelo.pojo.Producto;
import sgapt.util.Utilidades;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLFormularioProductoController implements Initializable {

    private File fotografiaProducto;
    private boolean esEdicion;
    private Producto productoEdicion;
    
    @FXML
    private ComboBox<String> cbRequiereReceta;
    @FXML
    private ComboBox<String> cbTipoProducto;
    @FXML
    private TextField tfNombre;
    @FXML
    private ImageView ivProducto;
    @FXML
    private TextField tfNombre2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicAgregarImagen(MouseEvent event) {
        FileChooser dialogoImagen = new FileChooser();
        dialogoImagen.setTitle("Seleccione la imagen del producto");
        FileChooser.ExtensionFilter filtroImagen = new FileChooser.ExtensionFilter("Archivos JPG" , "*.JPG");
        dialogoImagen.getExtensionFilters().add(filtroImagen);
        Stage stagePrincipal = (Stage) tfNombre.getScene().getWindow();
        fotografiaProducto = dialogoImagen.showOpenDialog(stagePrincipal);
        if (fotografiaProducto != null){
            try {
                BufferedImage bufferImg = ImageIO.read(fotografiaProducto);
                Image imagenFoto = SwingFXUtils.toFXImage(bufferImg, null);
                ivProducto.setImage(imagenFoto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void cargarInformacionProducto(){
        
    }
    
    public void inicializarValores(Producto producto){
        this.productoEdicion = producto;
        esEdicion = (producto != null);
        if (esEdicion){
            cargarInformacionProducto();
        }
    }
    

    @FXML
    private void clicVolver(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionProductos.fxml"));
        stagePrincipal.setTitle("Administracion de productos");
        stagePrincipal.show();
    }
    
}
