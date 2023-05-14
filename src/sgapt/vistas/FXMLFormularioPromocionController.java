package sgapt.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import sgapt.modelo.dao.ProductoDAO;
import sgapt.modelo.dao.ProductoRespuesta;
import sgapt.modelo.dao.PromocionDAO;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Promocion;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLFormularioPromocionController implements Initializable {

    @FXML
    private TextField tfTipo;
    @FXML
    private TextField tfFechaInicio;
    @FXML
    private TextField tfFechaFin;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbErrorTipo;
    @FXML
    private Label lbErrorFechaInicio;
    @FXML
    private Label lbErrorFechaFin;
    @FXML
    private Label lbErrorIdPromocion;
    @FXML
    private ComboBox<Producto> cbIdProducto;
    @FXML
    private Button clicBtnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCamposRegistros();
    }
    
     private void validarCamposRegistros(){
        String tipoPromocion = tfTipo.getText();
        String fechInicio = tfFechaInicio.getText();
        String fechFin = tfFechaFin.getText();
        boolean sonValidos=true;
        //TODO Validaciones
        if(tipoPromocion.isEmpty()){
            sonValidos=false;
            lbErrorTipo.setText("El campo es obligatorio");
        }
        if(fechInicio.isEmpty()){
            sonValidos=false;
            lbErrorFechaInicio.setText("El campo es obligatorio");
        }
        if(fechFin.isEmpty()){
            sonValidos=false;
            lbErrorFechaFin.setText("El campo es obligatorio");
        }
        //
        Promocion promocionValidada = new Promocion();
        promocionValidada.setTipoPromocion(tipoPromocion);
        promocionValidada.setFechaInicio(fechInicio);
        promocionValidada.setFechaFin(fechFin);
        registrarPromocion(promocionValidada);
    }

    @FXML 
    private void clicBtnCancelar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMAdministracionFormulario.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }
    
    private void cerrarVentana(){
        Stage stagePrincipal = (Stage) lbTitulo.getScene().getWindow();
        stagePrincipal.close();
    } 
    
    private void registrarPromocion(Promocion promocionRegistro){
        int codigoRespuesta = PromocionDAO.guardarPromocion(promocionRegistro);
        switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "La promocion no pudo ser guardada debido a un error en su conexión...", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la información", "La información de la promocion no puede ser guardada, por favor verifique su información", 
                        Alert.AlertType.WARNING);
                break;
            case Constantes.OPERACION_EXITOSA:
                Utilidades.mostrarDialogoSimple("Promocion registrada", "La información de la promocion fue guardada correctamente", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
                break;
        }
    }
    
    /*private void cargarInformacionProducto(int idProducto){
        producto = FXCollections.observableArrayList();
        ProductoRespuesta productoBD=ProductoDAO.obtenerInformacionProducto(idProducto);
        switch(productoBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                producto.addAll(productoBD.getCarreras());
                cbIdProducto.setItems(producto);
                break;
        }
    }*/
    
}
