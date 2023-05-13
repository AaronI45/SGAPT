package sgapt.vistas;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sgapt.modelo.dao.SesionDAO;
import sgapt.modelo.pojo.Empleado;
import sgapt.util.Utilidades;

public class FXMLInicioSesionController implements Initializable {

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
        try{
            Empleado usuarioRespuesta = SesionDAO.verificarUsuarioSesion(usuario, password);
            switch (usuarioRespuesta.getTipoEmpleado())
            {
                case Empleado.ADMINISTRADOR:
                        Utilidades.mostrarDialogoSimple("Bienvenido(a)", 
                            "Bienvenido(a) "+usuarioRespuesta.toString()+"al sistema...", 
                            Alert.AlertType.INFORMATION);
                        irPantallaAdministrador();
                    break;

                case Empleado.ENCARGADO:
                    Utilidades.mostrarDialogoSimple("Bienvenido(a)", 
                            "Bienvenido(a) "+usuarioRespuesta.toString()+"al sistema...", 
                            Alert.AlertType.INFORMATION);
                    irPantallaEncargado();
                    break;

                default:
                    Utilidades.mostrarDialogoSimple("Credenciales incorrectas", 
                            "El usuario y/o contraseña no son correctos, por favor verifica la información", 
                            Alert.AlertType.WARNING);
            }
        }catch (SQLException e){
            Utilidades.mostrarDialogoSimple("No hay conexión a la base de datos", 
                    "No existe conexión con la base de datos", Alert.AlertType.ERROR);
        }
    }
    
    private void irPantallaEncargado(){
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalEncargado.fxml"));
        escenarioBase.setTitle("Home");
        escenarioBase.show();
    }
    
    private void irPantallaAdministrador(){
        Stage escenarioBase = (Stage) tfUsuario.getScene().getWindow();
        escenarioBase.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        escenarioBase.setTitle("Home");
        escenarioBase.show();        
    }
    
    
}
