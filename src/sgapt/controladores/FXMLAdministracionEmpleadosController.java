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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sgapt.modelo.dao.EmpleadoDAO;
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
    private TableColumn    colDireccion;
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
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
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
    
    

    @FXML
    private void clicIrModifcarEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLModificarEmpleado.fxml"));
        stagePrincipal.setTitle("Modificación de empleado");
        stagePrincipal.show(); 
 
    }



    @FXML
    private void clicIrDarDeAltaEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLFormularioEmpleado.fxml"));
        stagePrincipal.setTitle("Alta de empleado");
        stagePrincipal.show(); 
    }

    @FXML
    private void clicDarDeBajaEmpleado(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLBajaEmpleado.fxml"));
        stagePrincipal.setTitle("Baja de empleado");
        stagePrincipal.show();       
        
    }


}
