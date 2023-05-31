/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sgapt.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Sucursal;
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
    private ObservableList<Producto.TipoDeProducto> tipos;
    private ObservableList<Producto.RequiereReceta> requiereReceta;
            
    @FXML
    private ComboBox<Producto.RequiereReceta> cbRequiereReceta;
    @FXML
    private ComboBox<Producto.TipoDeProducto> cbTipoProducto;
    @FXML
    private TextField tfNombre;
    @FXML
    private ImageView ivProducto;
    @FXML
    private Label lbRequiereReceta;
    @FXML
    private TextField tfPrecio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarCbTipoProducto();
        inicilizarCbRequiereReceta();
        lbRequiereReceta.setVisible(false);
        cbRequiereReceta.setVisible(false);
        cbTipoProducto.valueProperty().addListener(new ChangeListener<Producto.TipoDeProducto>(){
            
            @Override
            public void changed(ObservableValue<? extends Producto.TipoDeProducto> observable, 
                    Producto.TipoDeProducto oldValue, Producto.TipoDeProducto newValue) {
                if(newValue == Producto.TipoDeProducto.MEDICAMENTO){
                    cbRequiereReceta.setVisible(true);
                    lbRequiereReceta.setVisible(true);
                }else{
                    lbRequiereReceta.setVisible(false);
                    cbRequiereReceta.setVisible(false);
                }
            }
        });
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
        tfNombre.setText(productoEdicion.getNombre());
        tfPrecio.setText(String.valueOf(productoEdicion.getPrecio()));
        cbTipoProducto.getSelectionModel().select(Producto.TipoDeProducto.t(productoEdicion.getTipoProducto()));
        cbRequiereReceta.getSelectionModel().select(Producto.RequiereReceta.requiere(productoEdicion.isRequiereReceta()));
        if (productoEdicion.getFoto() != null){
            try {
            ByteArrayInputStream inputFoto = new ByteArrayInputStream(productoEdicion.getFoto());
            Image imgFotoEdicion = new Image(inputFoto);
            ivProducto.setImage(imgFotoEdicion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void inicializarValores(Producto producto){
        this.productoEdicion = producto;
        esEdicion = (producto != null);
        if (esEdicion){
            cargarInformacionProducto();
        }else{
            inicializarCbTipoProducto();
        }
    }
    
    public void inicializarCbTipoProducto(){
        tipos = FXCollections.observableArrayList();
        tipos.addAll(Producto.TipoDeProducto.values());
        cbTipoProducto.setItems(tipos);
    }

    public void inicilizarCbRequiereReceta(){
        requiereReceta = FXCollections.observableArrayList();
        requiereReceta.addAll(Producto.RequiereReceta.values());
        cbRequiereReceta.setItems(requiereReceta);
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionProductos.fxml"));
        stagePrincipal.setTitle("Administracion de productos");
        stagePrincipal.show();
    }

    @FXML
    private void clicEditarProducto(ActionEvent event) {
        
    }

    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }
    
}
