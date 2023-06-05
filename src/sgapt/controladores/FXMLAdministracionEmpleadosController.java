package sgapt.controladores;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sgapt.modelo.dao.EmpleadoDAO;
import sgapt.modelo.dao.Empleado_FarmaciaDAO;
import sgapt.modelo.pojo.Empleado;
import sgapt.modelo.pojo.EmpleadoRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLAdministracionEmpleadosController implements Initializable {

    @FXML
    private TableView<Empleado> tvEmpleados;
    @FXML
    private TableColumn   colNombreEmpleado;
    @FXML
    private TableColumn    colApellidoPaterno;
    @FXML
    private TableColumn   colApellidoMaterno;
    @FXML
    private TableColumn    colCorreoElectronico;
    @FXML
    private TableColumn<?, ?> colSucursal;
    @FXML
    private TableColumn   colNumeroTelefonico;
    private ObservableList<Empleado> empleados;
    @FXML
    private ImageView ivEmpleado;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        tvEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                if (newSelection.getFoto() !=null){
                    try {
                        ByteArrayInputStream inputFoto = new ByteArrayInputStream(newSelection.getFoto());
                        Image imgFotoEdicion = new Image(inputFoto);
                        ivEmpleado.setImage(imgFotoEdicion);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }else{
                    try {
                        Image img = new Image(new FileInputStream("src\\sgapt\\img\\usuario-sin-imagen.png"));
                        ivEmpleado.setImage(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("no hay fotografía disponible");
                    }
                }
            }
        });
    }    

      public void configurarTabla(){
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory("correo"));
        colNumeroTelefonico.setCellValueFactory(new PropertyValueFactory("numeroTelefonico"));
        colSucursal.setCellValueFactory(new PropertyValueFactory("direccion"));
    }
    
         private void cargarInformacionTabla() {
        empleados = FXCollections.observableArrayList();
       EmpleadoRespuesta respuestaBD = EmpleadoDAO.obtenerInformacionEmpleado();
        switch (respuestaBD.getCodigoRespuesta()) {
            case Constantes.ERROR_CONEXION:
                    Utilidades.mostrarDialogoSimple("Sin conexión", 
                    "Lo sentimos, por el momento no hay conexión para poder cargar la información", 
                    Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                    Utilidades.mostrarDialogoSimple("Error al cargar los datos", 
                    "Hubo un error al cargar la información, por favor inténtelo más tarde", 
                    Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                    empleados.addAll(respuestaBD.getEmpleados());
                    tvEmpleados.setItems(empleados);
                break;
        }
    }
 
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }

    private void clicIrConsultarEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLInformacionEmpleado.fxml"));
        stagePrincipal.setTitle("Información de empleado");
        stagePrincipal.show();
    }
    
    private Empleado verificarSeleccion(){
        int filaSeleccionada = tvEmpleados.getSelectionModel().getSelectedIndex();
        return (filaSeleccionada >= 0) ? empleados.get(filaSeleccionada) : null;
    }
    
    private void irFormularioEmpleado(Empleado empleado, boolean esEdicion){
        try{
        FXMLLoader accesoControlador = new FXMLLoader(getClass().getResource("/sgapt/vistas/FXMLFormularioEmpleado.fxml"));
        Parent vista = accesoControlador.load();
        FXMLFormularioEmpleadoController formulario = accesoControlador.getController();
        Scene sceneFormulario = new Scene(vista);
        Stage escenarioPrincipal = (Stage)ivEmpleado.getScene().getWindow();
        escenarioPrincipal.setScene(sceneFormulario);
        formulario.inicializarValores(empleado, esEdicion);
        }catch(IOException e){
            Utilidades.mostrarDialogoSimple("Error", "No se puede mostrar la pantalla de formulario", 
                    Alert.AlertType.ERROR);  
        }
        
    }

    @FXML
    private void clicIrModifcarEmpleado(ActionEvent event) {
        Empleado empleadoEdicion = verificarSeleccion();
        if (empleadoEdicion != null){
            irFormularioEmpleado(empleadoEdicion, true);
        }else{
            Utilidades.mostrarDialogoSimple("Error de selección", 
                    "Por favor seleccione un empleado para editar y vuelva a intentarlo", 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicIrDarDeAltaEmpleado(ActionEvent event) {
        irFormularioEmpleado(null, false);
    }

    
    @FXML
    private void clicDarDeBajaEmpleado(ActionEvent event) {
        int posicion = tvEmpleados.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            boolean darDeBajaEmpleado = Utilidades.mostrarDialogoConfirmacion(
                    "Confirmación de baja", 
                    "¿Está seguro de que desea dar de baja al empleado?");
            if (darDeBajaEmpleado) {
                int respuesta1 = Empleado_FarmaciaDAO.eliminarEmpleadoDeSucursal(
                        tvEmpleados.getSelectionModel().getSelectedItem().getIdEmpleado(), 
                        tvEmpleados.getSelectionModel().getSelectedItem().getIdFarmacia());
                int respuesta2 = Empleado_FarmaciaDAO.decrementarEmpleadoEnSucursal(
                        tvEmpleados.getSelectionModel().getSelectedItem().getIdFarmacia());
                Utilidades.mostrarDialogoSimple("Baja realizada exitosamente", 
                        "Se ha realizado la baja al usuario correctamente.", 
                        Alert.AlertType.INFORMATION);
                cargarInformacionTabla();
                ivEmpleado.setImage(null);
            }
        } else {
            Utilidades.mostrarDialogoSimple("Selección necesaria", 
                    "Debe seleccionar un empleado previamente para darlo de baja", 
                    Alert.AlertType.WARNING);
        }
    }
}
