package sgapt.util;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sgapt.SGAPT;

public class Utilidades {
    
    public static void mostrarDialogoSimple(String titulo, String mensaje,
        Alert.AlertType tipo) {
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();
    }
    
    public static boolean mostrarDialogoConfirmacion(String titulo, String mensaje){
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setContentText(mensaje);
        alertaConfirmacion.setHeaderText(null);
        Optional<ButtonType> respuesta = alertaConfirmacion.showAndWait();
        return (respuesta.get() == ButtonType.OK);
    }
    
    public static Scene inicializarEscena(String ruta) {
        Scene escena = null;
        try
        {
            Parent vista = FXMLLoader.load(SGAPT.class.getResource(ruta));
            escena = new Scene(vista);
        } catch (IOException ex)
        {
            System.err.println("ERROR: " + ex.getMessage());
        }
        return escena;
    }
    
    public static boolean correoValido(String correo) {
        if (correo != null && !correo.isEmpty()) {
            Pattern patronCorreo = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
            Matcher matchPatron = patronCorreo.matcher(correo);
            return matchPatron.find();
        } else {
            return false;
        }
    }
}