/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sgapt.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
import javafx.scene.control.Alert;
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
import sgapt.modelo.dao.ProductoDAO;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.ResultadoOperacion;
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

    public boolean validarPrecio(){
        double precio = 0;
        boolean precioValido = false;
        if (tfPrecio.getText() != null){
            try{
                precio = Double.parseDouble(tfPrecio.getText());
                if (precio >0){
                    precioValido = true;                
                }else{
                    Utilidades.mostrarDialogoSimple("Error de asignación de precio", 
                            "Por favor introduzca un precio mayor a 0 y vuelva a intentarlo", 
                            Alert.AlertType.ERROR);
                }
            }catch (NumberFormatException e){
                Utilidades.mostrarDialogoSimple("Error de asignación de precio", 
                        "Por favor introduzca un valor válido para el precio", 
                        Alert.AlertType.ERROR);
            }
        }
            else {
            Utilidades.mostrarDialogoSimple("Error de asignación de precio", 
                    "El campo de precio no puede quedar vacío, indique un precio y vuelva a intentarlo", 
                    Alert.AlertType.ERROR);
        }
        return precioValido;
    }
    
    public void regresarAventanaAnterior(){
        Stage stagePrincipal = (Stage) tfNombre.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionProductos.fxml"));
        stagePrincipal.setTitle("Administración de productos");
        stagePrincipal.show();
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
        if (validarPrecio()){
            Producto producto = new Producto();
            producto.setPrecio(Double.parseDouble(tfPrecio.getText()));
            producto.setTipoProducto(cbTipoProducto.getValue().toString().toLowerCase());
            if (producto.getTipoProducto() == "medicamento"){
                producto.setRequiereReceta(Producto.RequiereReceta.getRequiere(cbRequiereReceta.getValue()));
            }else{
                producto.setRequiereReceta(false);
            }
            if(fotografiaProducto != null){
                try {
                    producto.setFoto(Files.readAllBytes(fotografiaProducto.toPath()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else{
                    producto.setFoto(productoEdicion.getFoto());
            }
            if(producto.getFoto() != null){
                ResultadoOperacion resultadoEdicion = ProductoDAO.editarProductoPorID(producto, productoEdicion.getIdProducto(), producto.getFoto());
                if(!resultadoEdicion.isError()){
                    Utilidades.mostrarDialogoSimple("Éxito al editar el producto", 
                            resultadoEdicion.getMensaje(), Alert.AlertType.INFORMATION);
                    regresarAventanaAnterior();
                }else{
                    Utilidades.mostrarDialogoSimple("Error al editar el producto", 
                            resultadoEdicion.getMensaje(), Alert.AlertType.ERROR);
                }
            }else{
                Utilidades.mostrarDialogoSimple("Error de edición de producto", 
                        "Por favor seleccione una imágen para el producto", 
                        Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void permitirInputSoloNumeros(KeyEvent event) {
        String entrada = event.getCharacter();
        if (!".0123456789".contains(entrada)) 
            event.consume();
    }
    
}
