
package sgapt.controladores;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sgapt.modelo.dao.SesionDAO;
import sgapt.modelo.pojo.Empleado;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    enum TipoEmpleado{
        ADMINISTRADOR,
        EMPLEADO;
    }
    
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIniciarSesion(ActionEvent event) {
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tipo de usuario");
        alert.setHeaderText("Seleccione el tipo de usuario");

        ButtonType btnEncargado = new ButtonType("Encargado");
        ButtonType btnAdministrador = new ButtonType("Administrador");
        alert.getButtonTypes().setAll(btnEncargado, btnAdministrador);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnEncargado) {            
            tfUsuario.setText("jamez");
            tfPassword.setText("hola");
        } else {
            tfUsuario.setText("brendamar");
            tfPassword.setText("abcd123");
        }        
        validarCampos();
    }
    
    private void validarCampos(){
        String usuario = tfUsuario.getText();
        String password = tfPassword.getText();
        boolean sonValidos = true;
        
        if(usuario.isEmpty()){
            sonValidos = false;
            lbErrorUsuario.setText("El campo usuario es obligatorio");
        }
        if(password.length() == 0){
            sonValidos = false;
            lbErrorPassword.setText("El campo contraseña es requerido");
        }
        if(sonValidos)
            validarCredencialesUsuario(usuario, password);
    }
    
    private void validarCredencialesUsuario(String usuario, String password) {
        Empleado usuarioRespuesta = SesionDAO.verificarUsuarioSesion(usuario, password);
        switch (usuarioRespuesta.getCodigoRespuesta())
        {
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexión", 
                        "Por el momento no hay conexión, intentelo más tarde", 
                        Alert.AlertType.ERROR);
                break;
            
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                        "Por el momento no se puede procesar la solicitud de verificación", 
                        Alert.AlertType.ERROR);
                break;
            
            case Constantes.OPERACION_EXITOSA:
                if (usuarioRespuesta.getIdEmpleado()> 0) {
                    String tipoUsuario = usuarioRespuesta.getTipoEmpleado().toUpperCase();
                    TipoEmpleado tipoLogin = Enum.valueOf(TipoEmpleado.class, tipoUsuario);
                    switch (tipoLogin){
                        case ADMINISTRADOR:
                            Utilidades.mostrarDialogoSimple("Bienvenido(a)", 
                                    "Bienvenido(a) "+usuarioRespuesta.toString()+"al sistema...", 
                                Alert.AlertType.INFORMATION);   
                            irPantallaAdmin();
                            break;
                        case EMPLEADO:
                            Utilidades.mostrarDialogoSimple("Bienvenido(a)", 
                                    "Bienvenido(a) "+usuarioRespuesta.toString()+"al sistema...", 
                                Alert.AlertType.INFORMATION);
                            irPantallaEmpleado();
                            break;
                            
                        default:
                            Utilidades.mostrarDialogoSimple("Usuario no soportado", 
                                    "Por favor comuníquese con los administradores del sistema...", 
                                Alert.AlertType.ERROR);
                    }
                } 
                break;
            
            default:
                Utilidades.mostrarDialogoSimple("Credenciales incorrectas", 
                        "El usuario y/o contraseña no son correctos, por favor verifica la información", 
                        Alert.AlertType.WARNING);
        }
    }
    
    private void irPantallaAdmin() {        
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        escenarioBase.setTitle("Home");
        escenarioBase.show();
    }
    
    private void irPantallaEmpleado(){
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalEncargado.fxml"));
        escenarioBase.setTitle("Home");
        escenarioBase.show();        

    }
    
}
