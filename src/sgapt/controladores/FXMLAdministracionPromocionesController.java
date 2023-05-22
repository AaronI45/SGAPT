package sgapt.controladores;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgapt.interfaz.INotificacionOperacion;
import sgapt.modelo.dao.PromocionDAO;
import static sgapt.modelo.dao.PromocionDAO.darDeBajaPromocion;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Promocion;
import sgapt.modelo.pojo.PromocionRespuesta;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLAdministracionPromocionesController implements Initializable, INotificacionOperacion {
    @FXML
    private TableView<Promocion> tvPromocion;
    @FXML
    private TableColumn columnNomProd;
    @FXML
    private TableColumn columnFechaInicio;
    @FXML
    private TableColumn columnFechaFin;
    @FXML
    private TableColumn columnDescuento;
    @FXML
    private TableColumn columnPreDesc;
    
    private ObservableList<Promocion> promociones;
    @FXML
    private TableColumn columnPrecio;

    private INotificacionOperacion interfazNotificacion;
    @FXML
    private ImageView ivProducto;
    
    private Producto producto;
    @FXML
    private Button btImagen;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLBannerPromociones.fxml"));
        stagePrincipal.setTitle("Promociones");
        stagePrincipal.show();
    }
    
    /* private void cerrarVentana(){
        Stage escenarioBase = (Stage) lbTitulo.getScene().getWindow();
        escenarioBase.close();
    }*/ 

    @FXML
    private void clicBtnCrearProm(ActionEvent event) {
        irFormulario(false, null);
        /*Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLFormularioPromocion2.fxml"));
        stagePrincipal.setTitle("Formulario de promoción");
        stagePrincipal.show();*/
    }
    
     private void configurarTabla() {
        columnNomProd.setCellValueFactory(new PropertyValueFactory("producto"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory("productoPrecio"));
        columnDescuento.setCellValueFactory(new PropertyValueFactory("porcentajeDescuento")); 
        columnFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        columnFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        columnPreDesc.setCellValueFactory(new PropertyValueFactory("precioDescuento"));
    } 
     
    private void cargarInformacionTabla() {
        promociones = FXCollections.observableArrayList();
        PromocionRespuesta respuestaBD = PromocionDAO.obtenerInformacionPromocion();
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
                    promociones.addAll(respuestaBD.getPromociones());
                    tvPromocion.setItems(promociones);
                break;
        }
    }

    @FXML
    private void clicBtnExpirar(ActionEvent event) {
        int posicion = tvPromocion.getSelectionModel().getSelectedIndex();
        if(posicion!= -1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación de expiracion");
                alert.setHeaderText("¿Está seguro de que desea expirar la promoción?");

                ButtonType btnConfirmar = new ButtonType("Confirmar");
                ButtonType btnCancelar = new ButtonType("Cancelar");
                alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();
                
                if(result.isPresent() && result.get()== btnConfirmar){
                    expirar(promociones.get(posicion).getIdPromocion());
                    Utilidades.mostrarDialogoSimple("Promcion expirada", "La promcion ha expirado de manera correcta", 
                    Alert.AlertType.INFORMATION);
                }
                cargarInformacionTabla();
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una promoción", "Selecciona el registro en la tabla de promociones para su expiracion", 
                    Alert.AlertType.WARNING);
            }
    }
    
    private void expirar(int idPromocion){
        darDeBajaPromocion(idPromocion);
    }
    
    @FXML
    private void clicBtnModificar(ActionEvent event) {
        int posicion = tvPromocion.getSelectionModel().getSelectedIndex();
        if(posicion!= -1){
            irFormulario(true, promociones.get(posicion));
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una promocion", "Selecciona el registro en la tabla de la promocion para su edicion", 
                    Alert.AlertType.WARNING);
            }
    }
    
     private void irFormulario(boolean esEdicion, Promocion promocionEdicion){
         try{
             FXMLLoader accesoControlador = new FXMLLoader
                     (sgapt.SGAPT.class.getResource("vistas/FXMLFormularioPromocion3.fxml"));
             Parent vista= accesoControlador.load();
             FXMLFormularioPromocionController formulario = accesoControlador.getController();
             formulario.inicializarInformacionFormulario(esEdicion, promocionEdicion, this);
             
             Stage escenarioFormulario = new Stage();
             escenarioFormulario.setScene(new Scene(vista));
             escenarioFormulario.setTitle("Formulario");
             escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
             escenarioFormulario.showAndWait();
         }catch(IOException ex){
             Logger.getLogger(FXMLAdministracionPromocionesController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void notificarOperacionGuardar() {
        cargarInformacionTabla();
         //Utilidades.mostrarDialogoSimple("Notificacion", 
            //    "Promocion del producto "+nombreProducto+" guardada", Alert.AlertType.INFORMATION);
    }

    @Override
    public void notificarOperacionActualizar() {
        cargarInformacionTabla();
        //Utilidades.mostrarDialogoSimple("Notificacion", 
               // "Promocion del producto "+nombreProducto+" actualizada", Alert.AlertType.INFORMATION);
    }
    
    private void mostrarImagenProducto(){
        Promocion promocion = tvPromocion.getSelectionModel().getSelectedItem();
        if(promocion != null){
            ByteArrayInputStream inputFoto = new ByteArrayInputStream(promocion.getFoto());
            Image imgFotoAlumno = new Image(inputFoto);
            ivProducto.setImage(imgFotoAlumno);
        }else{
            System.out.println("hola");
        }        
    }

    @FXML
    private void clicMostrarImagen(ActionEvent event) {
        mostrarImagenProducto();
    }
    
}
