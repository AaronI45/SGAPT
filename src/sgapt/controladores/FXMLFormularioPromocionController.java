package sgapt.controladores;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import sgapt.interfaz.INotificacionOperacion;
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

public class FXMLFormularioPromocionController implements Initializable, INotificacionOperacion {

    @FXML
    private TextField tfPorcentaje;
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
    private Promocion promocionEdicion;
    private boolean esEdicion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;

    private INotificacionOperacion interfazNotificacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarInformacionSucursal();
       cbSucursal.valueProperty().addListener(new ChangeListener<Sucursal>() {
          @Override
          public void changed(ObservableValue<? extends Sucursal> observable, Sucursal oldValue, Sucursal newValue){
             if(newValue != null){
                 cargarInformacionProducto(newValue);
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
        //LocalDate fechaInicio = dtFechaInicio.getValue();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LLLL-dd");
        String fomrStringInicio = fechaInicio.format(formatter);
        LocalDate fechaFin = dpFechaFin.getValue();
        String formStringFin = fechaFin.format(formatter);
        SingleSelectionModel<Producto> producto= cbIdProducto.getSelectionModel();
        int idProducto = cbIdProducto.getSelectionModel().getSelectedItem().getIdProducto();
        int Sucursal = cbSucursal.getSelectionModel().getSelectedItem().getIdInventario();
        boolean sonValidos=true;
        boolean sinDisponibilidad;
        //TO DO Validaciones
        if(porcentajeDescuento.isEmpty()){
            sonValidos=false;
            lbErrorTipo.setText("Campo vacío");
        }else{
            if(descuento>=100){
                sonValidos=false;
                lbErrorTipo.setText("Dato inválido");
            }else{
                if(descuento<=0){
                sonValidos=false;
                lbErrorTipo.setText("Dato inválido");
                }else{
                lbErrorTipo.setText("");
                }
            }
        }

        if(fomrStringInicio.isEmpty()){
            sonValidos=false;
            lbErrorFechaInicio.setText("Campo vacío");
        }else{
            if(fechaInicio.isAfter(fechaFin)){
                sonValidos=false;
                lbErrorFechaInicio.setText("Dato inválido");
            }else
            lbErrorFechaInicio.setText("");
        }

        if(formStringFin.isEmpty()){
            sonValidos=false;
            lbErrorFechaFin.setText("Campo vacío");
        }else{
            if(fechaFin.isBefore(fechaInicio)){
                sonValidos=false;
                lbErrorFechaFin.setText("Dato inválido");
            }else{
                lbErrorFechaFin.setText("");
            }
        }

        if(cbIdProducto.getSelectionModel().isEmpty()){
            sonValidos=false;
            lbErrorProducto.setText("Campo vacío");
        }

        if(cbSucursal.getSelectionModel().isEmpty()){
            sonValidos=false;
            lbErrorSucursal.setText("Campo vacío");
        }

        if("disponible".equals(cbIdProducto.getSelectionModel().getSelectedItem().getDisponibilidad())){
            sinDisponibilidad=false;
        }else{
            sinDisponibilidad=true;
            Utilidades.mostrarDialogoSimple("Sin disponibilidad", "No hay disponibilidad del producto en el inventario",
                        Alert.AlertType.WARNING);
        }

        if(sonValidos==false){
                mostrarMensajeDatos();
            }else{
                //---------
                if(sinDisponibilidad==true){
                    //no guardara la promocion
                }
                else{
                        Promocion promocionValidada = new Promocion();
                        promocionValidada.setIdProducto(idProducto);
                        promocionValidada.setPorcentajeDescuento(descuento);
                        promocionValidada.setFechaInicio(fomrStringInicio);
                        promocionValidada.setFechaFin(formStringFin);
                    if(esEdicion){
                        promocionValidada.setIdPromocion(promocionEdicion.getIdPromocion());
                        actualizarPromocion(promocionValidada);
                    }else{
                         registrarPromocion(promocionValidada);
                    }
            }
           //-------
        }
    }

    private void mostrarMensajeDatos(){
        Utilidades.mostrarDialogoSimple("Fallo de informacion", "Por favor, verifique la información",
                        Alert.AlertType.WARNING);
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación de cancelacion");
                alert.setHeaderText("¿Está seguro de que desea salir? Ningun cambio se guardará.");

                ButtonType btnConfirmar = new ButtonType("Confirmar");
                ButtonType btnCancelar = new ButtonType("Cancelar");
                alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get()== btnConfirmar){
                   cerrarVentana();
                }
    }

    private void cerrarVentana(){
        /*Stage stagePrincipal = (Stage) lbTitulo.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones1.fxml"));
        stagePrincipal.setTitle("Aministracion de promociones");
        stagePrincipal.show();*/
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }

    /*private void cerrarVentanaFormulario(){
         Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
         escenarioBase.close();
    }*/

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
                interfazNotificacion.notificarOperacionGuardar();
                cerrarVentana();
                break;
        }
    }

    public void inicializarInformacionFormulario(boolean esEdicion, Promocion promocionEdicion, INotificacionOperacion interfazNotificacion){
        this.esEdicion=esEdicion;
        this.promocionEdicion=promocionEdicion;
        this.interfazNotificacion= interfazNotificacion;
        // TO DO
        if(esEdicion){
            lbTitulo.setText("Editar informacion de la promocion");
            cargarInformacionEdicion();
        }else{
            lbTitulo.setText("Registrar nueva promocion");
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
    private void actualizarPromocion(Promocion promocionActualizar){
        int codigoRespuesta = PromocionDAO.modificarPromocion(promocionActualizar);
         switch(codigoRespuesta){
            case Constantes.ERROR_CONEXION:
                Utilidades.mostrarDialogoSimple("Error de conexion", "La promoción no pudo ser actualizada debido a un error en su conexion...",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.ERROR_CONSULTA:
                Utilidades.mostrarDialogoSimple("Error en la informacion", "La informacion de la promoción no puede ser actualizada, por favor verifica la informacion",
                        Alert.AlertType.ERROR);
                break;
            case Constantes.OPERACION_EXITOSA:
                 Utilidades.mostrarDialogoSimple("Promocion actualizada", "La informacion de la promocion fue actualizada correctamente",
                        Alert.AlertType.INFORMATION);
                 cerrarVentana();
                 interfazNotificacion.notificarOperacionActualizar();
                 cerrarVentana();
                 break;
        }
    }

    //
    private void cargarInformacionEdicion(){
        tfPorcentaje.setText(Double.toString(promocionEdicion.getPorcentajeDescuento()));
        LocalDate fechaInicio = LocalDate.parse(promocionEdicion.getFechaInicio());
        dpFechaInicio.setValue(fechaInicio);
        LocalDate fechaFin = LocalDate.parse(promocionEdicion.getFechaFin());
        dpFechaFin.setValue(fechaFin);
        int posicionSucursal=obtenerPosicionComboSucursal(promocionEdicion.getIdSucursal());
        cbSucursal.getSelectionModel().select(posicionSucursal);
        int posicionProducto=obtenerPosicionComboProducto(promocionEdicion.getIdProducto());
        cbIdProducto.getSelectionModel().select(posicionProducto);
    }

    private int obtenerPosicionComboSucursal(int idSucursal){
        for(int i=0; i <sucursales.size(); i++){
            if(sucursales.get(i).getIdInventario()== idSucursal){
                return i;
            }
        }
        return 0;
    }

    private int obtenerPosicionComboProducto(int idProducto){
         for(int i=0; i <productos.size(); i++){
            if(productos.get(i).getIdProducto()== idProducto){
                return i;
            }
        }
        return 0;
    }

    @Override
    public void notificarOperacionGuardar() {

    }

    @Override
    public void notificarOperacionActualizar() {

    }

}
