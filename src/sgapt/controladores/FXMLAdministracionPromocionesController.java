package sgapt.controladores;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
        
            tvPromocion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                if (newSelection.getFoto() !=null){
                    try {
                        ByteArrayInputStream inputFoto = new ByteArrayInputStream(newSelection.getFoto());
                        Image imgFotoEdicion = new Image(inputFoto);
                        ivProducto.setImage(imgFotoEdicion);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }else{
                    try {
                        Image img = new Image(new FileInputStream("src\\sgapt\\img\\imagen-no-disponible.png"));
                        ivProducto.setImage(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("no hay fotografía disponible");
                    }
                }
            }
        });
        
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLBannerPromociones1.fxml"));
        stagePrincipal.setTitle("Promociones");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnCrearProm(ActionEvent event) {
        irFormulario(false, null);
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
        Promocion promocion = tvPromocion.getSelectionModel().getSelectedItem();
        if(promocion != null){
            irFormulario(true, promocion);
        }else{
            Utilidades.mostrarDialogoSimple("Selecciona una promocion", "Selecciona el registro en la tabla de la promocion para su edicion", 
                    Alert.AlertType.WARNING);
            }
    }
    
     private void irFormulario(boolean esEdicion, Promocion promocionEdicion){
         try{
             FXMLLoader accesoControlador = new FXMLLoader
                     (sgapt.SGAPT.class.getResource("vistas/FXMLFormularioPromocion6.fxml"));
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
    }

    @Override
    public void notificarOperacionActualizar() {
        cargarInformacionTabla();
    }      
    
}
