package sgapt.controladores;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ObservableList<Producto> productos;
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
    @FXML
    private ImageView ivFotoProducto;
    
    String estiloError="-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 2;";
    String estiloNormal="-fx-border-width: 0;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarInformacionProducto();
       
       cbIdProducto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
           if(newSelection != null){
               if(newSelection.getFoto()!= null){
                   try {
                       ByteArrayInputStream inputFoto = new ByteArrayInputStream(newSelection.getFoto());
                       Image imgFotoEdicion = new Image(inputFoto);
                       ivFotoProducto.setImage(imgFotoEdicion);
                   }catch (Exception e) {
                       e.printStackTrace();
                   }
               }else{
                   try {
                       Image img = new Image(new FileInputStream("src\\sgapt\\img\\capsule.png"));
                       ivFotoProducto.setImage(img);
                   }catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
       });
       
       tfPorcentaje.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, 
            String newValue) {
        if (!newValue.matches("\\d*")) {
            tfPorcentaje.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }});
       
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        validarCamposRegistros();
    }

    private void validarCamposRegistros(){
        establecerEstiloNormal();
        
        String porcentajeDescuento = tfPorcentaje.getText();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        double descuento;
        int posicionProducto =cbIdProducto.getSelectionModel().getSelectedIndex();
        boolean sonValidos=true;
        boolean sinDisponibilidad=false;
        //TO DO Validaciones
        
        //Validacion del porcentaje de descuento
        if(porcentajeDescuento.isEmpty()){
            sonValidos=false;
            tfPorcentaje.setStyle(estiloError);
        }else{
            descuento = Double.parseDouble(porcentajeDescuento);
            if(descuento<=0){
                sonValidos=false;
                tfPorcentaje.setStyle(estiloError);
            }else{
                if(descuento>=100){
                    sonValidos=false;
                    tfPorcentaje.setStyle(estiloError);
                }
            }
        }
        
        ///Validacion fecha inicio
        if(fechaInicio == null){
            dpFechaInicio.setStyle(estiloError);
            sonValidos=false;
        }else{
            if(fechaInicio.isAfter(fechaFin)){
                dpFechaInicio.setStyle(estiloError);
                sonValidos=false;
            }
        }
        
        //Validacion fecha fin
        if(fechaFin==null){
            dpFechaFin.setStyle(estiloError);
            sonValidos=false;
        }else{
            if(fechaFin.isBefore(fechaInicio)){
                dpFechaFin.setStyle(estiloError);
                sonValidos=false;
            }
        }
        
        //Validacion producto    
        if(posicionProducto == -1){
            cbIdProducto.setStyle(estiloError);
            sonValidos=false;
        }else{
             //Validacion de disponibilidad del producto
            if("disponible".equals(cbIdProducto.getSelectionModel().getSelectedItem().getDisponibilidad())){
                //hay disponibilidad del producto
            }else{ 
                sinDisponibilidad=true;
                Utilidades.mostrarDialogoSimple("Sin disponibilidad", "No hay disponibilidad del producto en el inventario",
                        Alert.AlertType.WARNING);
            }
        }
        
        //Registro o actualizacion de la promocion
        
        if(sonValidos==true){
            Promocion promocionValidada = new Promocion();
            promocionValidada.setIdProducto(cbIdProducto.getSelectionModel().getSelectedItem().getIdProducto());
            descuento = Double.parseDouble(porcentajeDescuento);
            promocionValidada.setPorcentajeDescuento(descuento);
            promocionValidada.setFechaInicio(fechaInicio.toString());
            promocionValidada.setFechaFin(fechaFin.toString());
            if(sinDisponibilidad==true){
                    //no guardara la promocion
                }
                else{
                    if(esEdicion){
                        promocionValidada.setIdPromocion(promocionEdicion.getIdPromocion());
                        actualizarPromocion(promocionValidada);
                    }else{
                         registrarPromocion(promocionValidada);
                    }
            }
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
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
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

    private void cargarInformacionProducto(){
        productos = FXCollections.observableArrayList();
        ProductoRespuesta productoBD=ProductoDAO.obtenerProductos();
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
        double desc=promocionEdicion.getPorcentajeDescuento();
        int des= (int) desc;
        String porDesc= Integer.toString(des);
        tfPorcentaje.setText(porDesc);
        LocalDate fechaInicio = LocalDate.parse(promocionEdicion.getFechaInicio());
        dpFechaInicio.setValue(fechaInicio);
        LocalDate fechaFin = LocalDate.parse(promocionEdicion.getFechaFin());
        dpFechaFin.setValue(fechaFin);
        int posicionProducto=obtenerPosicionComboProducto(promocionEdicion.getIdProducto());
        cbIdProducto.getSelectionModel().select(posicionProducto);
        if(promocionEdicion.getFoto()!=null){
            ByteArrayInputStream inputFoto = new ByteArrayInputStream(promocionEdicion.getFoto());
            Image imgFotoAlumno = new Image(inputFoto);
            ivFotoProducto.setImage(imgFotoAlumno);
        }else{
            try{
                Image img = new Image(new FileInputStream("src\\sgapt\\img\\capsule.png"));
                ivFotoProducto.setImage(img);
            }catch(IOException e){
               e.printStackTrace();
               System.out.println("no hay fotografía disponible");
        }
      }
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
    
    private void establecerEstiloNormal(){
        tfPorcentaje.setStyle(estiloNormal);
        dpFechaInicio.setStyle(estiloNormal);
        dpFechaFin.setStyle(estiloNormal);
        cbIdProducto.setStyle(estiloNormal);
    }

}
