package sgapt.controladores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
import sgapt.modelo.dao.EmpleadoDAO;
import sgapt.modelo.dao.Empleado_FarmaciaDAO;
import sgapt.modelo.dao.SucursalDAO;
import sgapt.modelo.pojo.Empleado;
import sgapt.modelo.pojo.EmpleadoRespuesta;
import sgapt.modelo.pojo.Sucursal;
import sgapt.modelo.pojo.SucursalRespuesta;
import sgapt.util.Constantes;
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
    private ComboBox<Sucursal> cbSucursal;
    private File fotoEmpleado;
    private boolean esEdicion;
    private Empleado empleadoEdicion;
    private Empleado empleadoEdicionOriginal;
    private ObservableList<Sucursal> listaSucursales;
    String estiloError = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal = "-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarCBSucursales();
    }    

    public void inicializarValores(Empleado empleado, boolean esEdicion){
        this.empleadoEdicion = empleado;
        this.esEdicion = esEdicion;
        if (empleado != null){
            cargarInformacionEmpleado();
            copiarInformacionOriginal();
        }
    }
    
    public void cargarInformacionEmpleado(){
        lbFormulario.setText("Edición de empleado " + empleadoEdicion.getNombre());
        tfNombre.setText(empleadoEdicion.getNombre());
        tfApellidoPaterno.setText(empleadoEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(empleadoEdicion.getApellidoMaterno());
        tfCorreo.setText(empleadoEdicion.getCorreo());
        tfNumeroTelefonico.setText(empleadoEdicion.getNumeroTelefonico());
        System.out.println("Es edicion: " + esEdicion);
        if (esEdicion) {
            System.out.println("Tipo empleado: " + empleadoEdicion.getTipoEmpleado());
            if (empleadoEdicion.getTipoEmpleado().equals("administrador")) {
               cbSucursal.setDisable(esEdicion);
            }
            else seleccionarSucursalEmpleadoEdicion();
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
    
    private void seleccionarSucursalEmpleadoEdicion() {
        for (int i = 0; i < listaSucursales.size(); i++) {
            if(listaSucursales.get(i).getIdSucursal() == empleadoEdicion.getIdFarmacia())
                cbSucursal.getSelectionModel().select(i);
        }
    }
    
    private void copiarInformacionOriginal() {
        empleadoEdicionOriginal = new Empleado();
        empleadoEdicionOriginal.setNombre(empleadoEdicion.getNombre());
        empleadoEdicionOriginal.setApellidoMaterno(empleadoEdicion.getApellidoMaterno());
        empleadoEdicionOriginal.setApellidoPaterno(empleadoEdicion.getApellidoPaterno());
        empleadoEdicionOriginal.setCorreo(empleadoEdicion.getCorreo());
        empleadoEdicionOriginal.setDireccion(empleadoEdicion.getDireccion());
        empleadoEdicionOriginal.setFoto(empleadoEdicion.getFoto());
        empleadoEdicionOriginal.setIdEmpleado(empleadoEdicion.getIdEmpleado());
        empleadoEdicionOriginal.setIdFarmacia(empleadoEdicion.getIdFarmacia());
        empleadoEdicionOriginal.setNumeroTelefonico(empleadoEdicion.getNumeroTelefonico());
        empleadoEdicionOriginal.setPassword(empleadoEdicion.getPassword());
        empleadoEdicionOriginal.setTipoEmpleado(empleadoEdicion.getTipoEmpleado());
        empleadoEdicionOriginal.setUsername(empleadoEdicion.getUsername());
    }
    
    private void inicializarCBSucursales(){
        listaSucursales = FXCollections.observableArrayList();
        SucursalRespuesta sucursalesRespuesta = SucursalDAO.recuperarSucursales();
        listaSucursales.addAll(sucursalesRespuesta.getSucursales());
        cbSucursal.setItems(listaSucursales);
        
    }

    private boolean validarCamposInvalidos(){
        establecerEstiloNormal();
        boolean datosInvalidos = false;
        String correo = tfCorreo.getText();
        
        if(tfNombre.getText().isEmpty()){
            tfNombre.setStyle(estiloError);
            datosInvalidos = true;
        }
        if(tfApellidoPaterno.getText().isEmpty()){
            tfApellidoPaterno.setStyle(estiloError);
            datosInvalidos = true;
        }
        if(tfApellidoMaterno.getText().isEmpty()){
            tfApellidoMaterno.setStyle(estiloError);
            datosInvalidos = true;
        }
        if(tfCorreo.getText().isEmpty()){
            tfCorreo.setStyle(estiloError);
            datosInvalidos = true;
        }
        if (esEdicion && empleadoEdicion.getTipoEmpleado().equals("administrador")) {
        } else {
            if(cbSucursal.getSelectionModel().getSelectedIndex() < 0){
                cbSucursal.setStyle(estiloError);
                datosInvalidos = true;
            }
        }
        if(tfNumeroTelefonico.getLength() != 10) {
            tfNumeroTelefonico.setStyle(estiloError);
            datosInvalidos = true;
        }
        if (!Utilidades.correoValido(correo)) {
            tfCorreo.setStyle(estiloError);
            datosInvalidos = true;
        }
        
        return datosInvalidos;
    }
    
    private void establecerEstiloNormal() {
        tfNombre.setStyle(estiloNormal);
        tfApellidoPaterno.setStyle(estiloNormal);
        tfApellidoMaterno.setStyle(estiloNormal);
        tfCorreo.setStyle(estiloNormal);
        tfNumeroTelefonico.setStyle(estiloNormal);
        cbSucursal.setStyle(estiloNormal);
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
        regresarAVentanaAnterior();
    }
    
    private void regresarAVentanaAnterior() {
        Stage stagePrincipal = (Stage) lbFormulario.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionEmpleados.fxml"));
        stagePrincipal.setTitle("Administración de empleados");
        stagePrincipal.show();
    }
    
    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if(!validarCamposInvalidos()){
            String nombre = tfNombre.getText();
            String apellidoPaterno = tfApellidoPaterno.getText();
            String apellidoMaterno = tfApellidoMaterno.getText();
            String correo = tfCorreo.getText();
            String numeroTelefonico = tfNumeroTelefonico.getText();
            

            Empleado empleado = new Empleado();
            empleado.setNombre(nombre);
            empleado.setApellidoPaterno(apellidoPaterno);
            empleado.setApellidoMaterno(apellidoMaterno);
            empleado.setCorreo(correo);
            empleado.setNumeroTelefonico(numeroTelefonico);
            
            
            if (esEdicion && empleadoEdicion.getTipoEmpleado().equals("administrador")) {
                empleado.setTipoEmpleado("administrador");
            } else {
                int idFarmacia = cbSucursal.getSelectionModel().getSelectedItem().getIdSucursal();
                empleado.setIdFarmacia(idFarmacia);
                empleado.setTipoEmpleado("empleado");
            }

            try {
                if (esEdicion) {
                    if (fotoEmpleado != null || empleadoEdicion.getFoto().length > 0) {
                        if (fotoEmpleado != null) {
                            empleado.setFoto(Files.readAllBytes(fotoEmpleado.toPath()));
                        } else {
                            empleado.setFoto(empleadoEdicion.getFoto());
                        }

                        empleado.setIdEmpleado(empleadoEdicion.getIdEmpleado());
                        actualizarEmpleado(empleado);
                    } else {
                        Utilidades.mostrarDialogoSimple("Selecciona una imagen", 
                                "Para editar el registro del alumno debes seleccionar " + 
                                        "su foto desde la opción seleccionar imagen", 
                                Alert.AlertType.WARNING);
                    }
                } else {
                    if (fotoEmpleado != null) {
                        empleado.setFoto(Files.readAllBytes(fotoEmpleado.toPath()));
                        registrarEmpleado(empleado);
                    }
                    else {
                        Utilidades.mostrarDialogoSimple("Selecciona una imagen", 
                                "Para guardar el registro del alumno debes seleccionar " + 
                                        "su foto desde la opción seleccionar imagen", 
                                Alert.AlertType.WARNING);
                    }
                }
            } catch (IOException e) {
                Utilidades.mostrarDialogoSimple("Error con el archivo", 
                        "Hubo un error al intentar guardar la imagen, vuelva a seleccionar el archivo", 
                        Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarDialogoSimple("Error en el registro", 
                    "Hay campos inválidos, por favor inserte la información necesaria y " + 
                            "vuelva a intentarlo",
                    Alert.AlertType.ERROR);
        }
    }

    private void actualizarEmpleado(Empleado empleadoEdicion) {
        int codigoRespuesta = EmpleadoDAO.modificarEmpleado(empleadoEdicion);
        switch (codigoRespuesta) {
        case Constantes.ERROR_CONEXION:
            Utilidades.mostrarDialogoSimple("Error de conexion", 
                    "El empleado no pudo ser actualizado"
                    + " debido a un error de conexión.", Alert.AlertType.ERROR);
            break;
        case Constantes.ERROR_CONSULTA:
            Utilidades.mostrarDialogoSimple("Error en la información",
                    "La información del empleado no puede ser actualizada,"
                   + "por favor verifique que sea correcta" , Alert.AlertType.WARNING);
            break;
        case Constantes.OPERACION_EXITOSA:
            // Experimental
            modificarVinculoDeEmpleadoAFarmacia(empleadoEdicion);
            Utilidades.mostrarDialogoSimple("Empleado registrado", "La información del empleado "
            + "fue actualizada correctamente", Alert.AlertType.INFORMATION);
            break;
        }
    }
    
    private void registrarEmpleado(Empleado empleadoRegistro){
        EmpleadoRespuesta empleadoRespuesta = EmpleadoDAO.agregarEmpleado(empleadoRegistro);
        switch(empleadoRespuesta.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "El empleado " + 
                        "no pudo ser guardado debido a un error de conexión.", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información",
                        "La información del empleado no puede ser guardada,"
                       + "por favor verifique que sea correcta" , 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                empleadoRegistro.setIdEmpleado(empleadoRespuesta.getIdEmpleado());
                vincularEmpleadoAFarmacia(empleadoRegistro);
                Utilidades.mostrarDialogoSimple("Empleado registrado", "La información del empleado "
                + "fue guardada correctamente", Alert.AlertType.INFORMATION);
                break;
        }
    }
    
    private void modificarVinculoDeEmpleadoAFarmacia(Empleado empleado) {
        if (empleadoEdicionOriginal.getIdFarmacia() != 
                empleado.getIdFarmacia()) {
            int codigoRespuesta1 = Empleado_FarmaciaDAO.eliminarEmpleadoDeSucursal(
                    empleado.getIdEmpleado(), 
                    empleadoEdicionOriginal.getIdFarmacia());
            System.out.println("codigoRespuesta1 = " + codigoRespuesta1);
            int codigoRespuesta2 = Empleado_FarmaciaDAO.decrementarEmpleadoEnSucursal(
                    empleadoEdicionOriginal.getIdFarmacia());
            System.out.println("codigoRespuesta2 = " + codigoRespuesta2);
            int codigoRespuesta3 = Empleado_FarmaciaDAO.agregarEmpleadoASucursal(
                    empleado.getIdEmpleado(), empleado.getIdFarmacia());
            System.out.println("codigoRespuesta3 = " + codigoRespuesta3);
            int codigoRespuesta4 = Empleado_FarmaciaDAO.aumentarEmpleadoEnSucursal(
                    empleado.getIdFarmacia());
            System.out.println("codigoRespuesta4 = " + codigoRespuesta4);
        }
    }
    
    private void vincularEmpleadoAFarmacia(Empleado empleado) {
        int codigoRespuesta1 = Empleado_FarmaciaDAO.agregarEmpleadoASucursal(
                empleado.getIdEmpleado(), empleado.getIdFarmacia());
        System.out.println("codigoRespuesta1 = " + codigoRespuesta1);
        int codigoRespuesta2 = Empleado_FarmaciaDAO.aumentarEmpleadoEnSucursal(
                empleado.getIdFarmacia());
        System.out.println("codigoRespuesta2 = " + codigoRespuesta2);
    }
    
    @FXML
    private void clcAgregarImagen(MouseEvent event) {
        FileChooser dialogoImagen = new FileChooser();
        dialogoImagen.setTitle("Seleccione la imagen del producto");
        FileChooser.ExtensionFilter filtroImagen = new FileChooser.ExtensionFilter(
                "Archivos PNG" , "*.PNG");
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
