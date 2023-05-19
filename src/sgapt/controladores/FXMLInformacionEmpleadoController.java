package sgapt.controladores;

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
import javafx.stage.Stage;
import sgapt.modelo.dao.EmpleadoDAO;
import sgapt.modelo.dao.LoteDAO;
import sgapt.modelo.dao.PedidoDAO;
import sgapt.modelo.pojo.Empleado;
import sgapt.modelo.pojo.EmpleadoRespuesta;
import sgapt.modelo.pojo.LoteRespuesta;
import sgapt.modelo.pojo.PedidoRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLInformacionEmpleadoController implements Initializable {

    @FXML
    private TableView<Empleado> tvEmpleados;
    @FXML
    private TableColumn colNombreEmpleado;
    @FXML
    private TableColumn  colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colCorreoElectronico;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colNumeroTelefonico;
    @FXML
    private TableColumn  colImagen;
    
    
      private ObservableList  <Empleado> empleados;
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    

    
    
    
    
     public void configurarTabla(){
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellido paterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellido materno"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory("correo electronico"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion del empleado"));
        colNumeroTelefonico.setCellValueFactory(new PropertyValueFactory("numero telfonico"));
        colImagen.setCellValueFactory(new PropertyValueFactory("visualizacionFoto"));
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
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionEmpleados.fxml"));
        stagePrincipal.setTitle("Administración de empleados");
        stagePrincipal.show();   
    }
    
}
