package sgapt.controladores;

import java.io.ByteArrayInputStream;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sgapt.modelo.dao.ProductoDAO;
import sgapt.modelo.pojo.ProductoRespuesta;
import sgapt.modelo.dao.SucursalDAO;
import sgapt.modelo.pojo.SucursalRespuesta;
import sgapt.modelo.pojo.Producto;
import sgapt.modelo.pojo.Sucursal;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

/**
 * FXML Controller class
 *
 * @author super
 */
public class FXMLAdministracionProductosController implements Initializable {

    @FXML
    private TableView<Producto> tvProductos;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colTipoProducto;
    @FXML
    private TableColumn<?, ?> colDisponibilidad;
    @FXML
    private TableColumn<?, ?> colCantidad;
    @FXML
    private TableColumn<?, ?> colFechaCaducidad;
    @FXML
    private TableColumn<?, ?> colRequiereReceta;
    @FXML
    private TableColumn<?, ?> colNumeroLote;
    @FXML
    private TableColumn<?, ?> colPrecio;
    @FXML
    private TableColumn<?, ?> colImagen;
    @FXML
    private ComboBox<Sucursal> cbSucursales;
    private ObservableList<Sucursal> listaSucursales;
    private ObservableList<Producto> listaProductos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarListaSucursales();
        cbSucursales.valueProperty().addListener(new ChangeListener<Sucursal>(){
                
            @Override
            public void changed(ObservableValue<? extends Sucursal> observable, 
                    Sucursal oldValue, Sucursal newValue) {
                if(newValue != null){
                    cargarDatosTabla(newValue);
                }
            }
        });
    }    
    
    public void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colTipoProducto.setCellValueFactory(new PropertyValueFactory("tipoProducto"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory("disponibilidad"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colRequiereReceta.setCellValueFactory(new PropertyValueFactory("requiereReceta"));
        colNumeroLote.setCellValueFactory(new PropertyValueFactory("numeroLote"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colFechaCaducidad.setCellValueFactory(new PropertyValueFactory("fechaCaducidad"));
        colImagen.setCellValueFactory(new PropertyValueFactory("visualizacionFoto"));
    }
    
    public void cargarDatosTabla(Sucursal sucursalSeleccionada){
            listaProductos = FXCollections.observableArrayList();
            ProductoRespuesta pr = ProductoDAO.recuperarProductosEnSucursal(sucursalSeleccionada);
            switch (pr.getCodigoRespuesta()){
                    case Constantes.OPERACION_EXITOSA:
                        for (Producto produto : pr.getProductos()){
                            try{
                                ByteArrayInputStream input = new ByteArrayInputStream(produto.getFoto());
                                Image imagenProducto = new Image(input);
                                ImageView visualizacionProducto = new ImageView(imagenProducto);
                                visualizacionProducto.setFitHeight(80);
                                visualizacionProducto.setFitWidth(80);
                                produto.setVisualizacionFoto(visualizacionProducto);
                            }catch(NullPointerException e){
                                System.out.println("no hay imagen para el producto");
                            }
                        }
                        listaProductos.addAll(pr.getProductos());                                
                        tvProductos.setItems(listaProductos);
                    break;
                    case Constantes.ERROR_CONSULTA:
                        Utilidades.mostrarDialogoSimple("Error en la solicitud", 
                            "Por el momento no se puede procesar la solicitud de verificación", 
                                Alert.AlertType.ERROR);
                    case Constantes.ERROR_CONEXION:
                        Utilidades.mostrarDialogoSimple("Error de conexión", 
                                "Por el momento no hay conexión, intentelo más tarde", 
                                Alert.AlertType.ERROR);
                    default:
                        Utilidades.mostrarDialogoSimple("Error de petición", 
                                "El sistema no está disponible por el momento", 
                                Alert.AlertType.ERROR);
            }
    }

    private void cargarListaSucursales(){
        listaSucursales = FXCollections.observableArrayList();
        SucursalRespuesta sr = SucursalDAO.recuperarSucursales();
        listaSucursales.addAll(sr.getSucursales());
        cbSucursales.setItems(listaSucursales);
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }
}