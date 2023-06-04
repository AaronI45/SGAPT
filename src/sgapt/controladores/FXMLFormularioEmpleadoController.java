package sgapt.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import sgapt.modelo.pojo.Empleado;
import sgapt.modelo.pojo.Sucursal;
import sgapt.util.Utilidades;

public class FXMLFormularioEmpleadoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfNumeroTelefonico;
    @FXML
    private ImageView ivEmpleado;
    @FXML
    private Label lbFormulario;
    @FXML
    private ComboBox<Sucursal.Estado> cbSucursal;
    private File fotoEmpleado;
    private boolean esEdicion;
    private Empleado empleadoEdicion;
    private ObservableList<Sucursal.Estado> listaSucursales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarCBSucursales();
    }    

    public void inicializarValores(Empleado empleado){
        this.empleadoEdicion = empleado;
        if (empleado != null){
            this.esEdicion = true;
            cargarInformacionEmpleado();
        }else{
            esEdicion = false;
        }
    }
    
    public void cargarInformacionEmpleado(){
        lbFormulario.setText("Edición de empleado " + empleadoEdicion.getNombre());
        tfNombre.setText(empleadoEdicion.getNombre());
        tfApellidoPaterno.setText(empleadoEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(empleadoEdicion.getApellidoMaterno());
        tfCorreo.setText(empleadoEdicion.getCorreo());
        tfNumeroTelefonico.setText(empleadoEdicion.getNumeroTelefonico());
        if(empleadoEdicion.getDireccion() != null){
            cbSucursal.getSelectionModel().select(Sucursal.Estado.obtenerEstado(empleadoEdicion.getDireccion()));        
        }
        if (empleadoEdicion.getFoto() != null){
            try {
                ByteArrayInputStream inputFoto = new ByteArrayInputStream(empleadoEdicion.getFoto());
                Image imgFotoEdicion = new Image(inputFoto);
                ivEmpleado.setImage(imgFotoEdicion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void inicializarCBSucursales(){
        listaSucursales = FXCollections.observableArrayList();
        listaSucursales.addAll(Sucursal.Estado.values());
        cbSucursal.setItems(listaSucursales);
    }

    private boolean validarCamposVacios(){
        boolean hayCamposVacios = false;
        if(tfNombre.getText() == null){
            hayCamposVacios = true;
        }if(tfApellidoPaterno == null){
            hayCamposVacios = true;
        }if(tfApellidoMaterno.getText() == null){
            hayCamposVacios = true;
        }if (tfCorreo.getText() == null){
            hayCamposVacios = true;
        }if(tfNumeroTelefonico.getText() == null){
            hayCamposVacios = true;
        }if(cbSucursal.getSelectionModel().getSelectedIndex() < 0){
            hayCamposVacios = true;
        }
        return hayCamposVacios;
    }
    
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
         Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionEmpleados.fxml"));
        stagePrincipal.setTitle("Administración de empleados");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if(!validarCamposVacios()){
            String nombre = tfNombre.getText();
            String apellidoPaterno = tfApellidoPaterno.getText();
            String apellidoMaterno = tfApellidoMaterno.getText();
            String correo = tfCorreo.getText();
            String numeroTelefonico = tfNumeroTelefonico.getText();
            int idFarmacia = cbSucursal.getSelectionModel().getSelectedIndex();
            
            Empleado empleado = new Empleado();
            empleado.setNombre(nombre);
            empleado.setApellidoPaterno(apellidoPaterno);
            empleado.setApellidoMaterno(apellidoMaterno);
            empleado.setCorreo(correo);
            empleado.setNumeroTelefonico(numeroTelefonico);
            empleado.setIdFarmacia(idFarmacia);
            if (esEdicion){
                empleado.setIdEmpleado(empleadoEdicion.getIdEmpleado());
                if(fotoEmpleado != null){
                   try {
                       empleado.setFoto(Files.readAllBytes(fotoEmpleado.toPath()));
                    } catch (IOException ex) {
                       ex.printStackTrace();
                    }
                }else{
                    empleado.setFoto(empleadoEdicion.getFoto());
                }
                guardarRegistroEditado(empleado);
            }else{
                guardarRegistro(empleado);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Error en el registro", 
                    "Hay campos vacios, por favor inserte la información necesaria y vuelva a intentarlo",
                    Alert.AlertType.ERROR);
        }
    }

    private void guardarRegistroEditado(Empleado empleadoEditado){
        //TODO
    }
    
    private void guardarRegistro(Empleado empeladoNuevo){
        //TODO
    }
    
    @FXML
    private void clcAgregarImagen(MouseEvent event) {
        FileChooser dialogoImagen = new FileChooser();
        dialogoImagen.setTitle("Seleccione la imagen del producto");
        FileChooser.ExtensionFilter filtroImagen = new FileChooser.ExtensionFilter("Archivos PNG" , "*.PNG");
        dialogoImagen.getExtensionFilters().add(filtroImagen);
        Stage stagePrincipal = (Stage) tfNombre.getScene().getWindow();
        fotoEmpleado = dialogoImagen.showOpenDialog(stagePrincipal);
        if (fotoEmpleado != null){
            try {
                BufferedImage bufferImg = ImageIO.read(fotoEmpleado);
                Image imagenFoto = SwingFXUtils.toFXImage(bufferImg, null);
                ivEmpleado.setImage(imagenFoto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
