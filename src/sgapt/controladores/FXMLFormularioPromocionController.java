package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sgapt.modelo.dao.PromocionDAO;
import sgapt.modelo.dao.SucursalDAO;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.ProductoRespuesta;
import sgapt.modelo.pojo.Promocion;
import sgapt.modelo.pojo.Sucursal;
import sgapt.modelo.pojo.SucursalRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLFormularioPromocionController implements Initializable {

    @FXML
    private TextField tfPorcentaje;
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
    private ComboBox<Producto> cbIdProducto;
    @FXML
    private ComboBox<Sucursal> cbSucursal;
    private ObservableList<Producto> productos;
    private ObservableList<Sucursal> sucursales;
    @FXML
    private Label lbErrorSucursal;
    @FXML
    private Label lbErrorProducto;
    @FXML
    private TextField tfDisponibilidad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarInformacionSucursal();
       cbSucursal.valueProperty().addListener(new ChangeListener<Sucursal>() {
          @Override
          public void changed(ObservableValue<? extends Sucursal> observable, Sucursal oldValue, Sucursal newValue){
             if(newValue != null){
                 cargarInformacionProducto(newValue);
                 if(cbIdProducto.getValue()!= null){
                     //mostrarDisp(cbIdProducto.getValue().getDisponibilidad());
                 }
             } 
          }
       });
    }
    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCamposRegistros();
    }
    
    private void validarCamposRegistros(){
        String porcentajeDescuento = tfPorcentaje.getText();
        double descuento = Double.parseDouble(porcentajeDescuento);
        String fechInicio = tfFechaInicio.getText();
        String fechFin = tfFechaFin.getText();
        int idProducto = cbIdProducto.getSelectionModel().getSelectedItem().getIdProducto();
        int Sucursal = cbSucursal.getSelectionModel().getSelectedItem().getIdInventario();
        boolean sonValidos=true;
        //TO DO Validaciones
        if(porcentajeDescuento.isEmpty()){
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
        if(cbIdProducto.getSelectionModel().isEmpty()){
            sonValidos=false;
            lbErrorProducto.setText("El campo es obligatorio");
        }
        if(cbSucursal.getSelectionModel().isEmpty()){
            sonValidos=false;
            lbErrorSucursal.setText("El campo es obligatorio");
        }
       
        Promocion promocionValidada = new Promocion();
        promocionValidada.setIdProducto(idProducto);
        promocionValidada.setPorcentajeDescuento(descuento);
        promocionValidada.setFechaInicio(fechInicio);
        promocionValidada.setFechaFin(fechFin);
        registrarPromocion(promocionValidada);
    }

    @FXML 
    private void clicBtnCancelar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones1.fxml"));
        stagePrincipal.setTitle("Aministracion de promociones");
        stagePrincipal.show();
    }
    
    private void cerrarVentana(){
        Stage stagePrincipal = (Stage) lbTitulo.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones1.fxml"));
        stagePrincipal.setTitle("Aministracion de promociones");
        stagePrincipal.show();
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
    
     private void cargarInformacionSucursal(){
        sucursales = FXCollections.observableArrayList();
        SucursalRespuesta sucursalBD=SucursalDAO.recuperarSucursales();
        switch(sucursalBD.getCodigoRespuesta()){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "Error en la conexion con la base de datos", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error de consulta", "Por el momento no se pudo obtener la informacion", 
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                sucursales.addAll(sucursalBD.getSucursales());
                cbSucursal.setItems(sucursales);
                break;
        }
    }
    
    private void cargarInformacionProducto(Sucursal sucursal){
        productos = FXCollections.observableArrayList();
        ProductoRespuesta productoBD=ProductoDAO.recuperarProductosEnSucursal(sucursal);
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
                productos.addAll(productoBD.getProductos());
                cbIdProducto.setItems(productos);
                break;
        }
    }
    
    private void mostrarDisp(Producto producto){
        productos = FXCollections.observableArrayList();
        ProductoRespuesta productoBD=ProductoDAO.mostrarDisponibilidad(producto);
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
                tfDisponibilidad.setText(producto.getDisponibilidad());
                break;
        }
    }
    
}
