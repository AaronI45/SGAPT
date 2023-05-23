package sgapt.controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sgapt.SGAPT;
import sgapt.modelo.dao.LoteDAO;
import sgapt.modelo.dao.PedidoDAO;
import sgapt.modelo.pojo.PedidoRespuesta;
import sgapt.modelo.pojo.Pedido;
import sgapt.util.Constantes;
import sgapt.util.Utilidades;

public class FXMLAdministracionPedidosController implements Initializable {

    @FXML
    private TableView<Pedido> tvPedidos;
    @FXML
    private TableColumn colNumeroPedido;
    @FXML
    private TableColumn colProveedor;
    @FXML
    private TableColumn colEstado;
    
    private ObservableList<Pedido> pedidos;        
    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }    
    
    private void configurarTabla() {
        colNumeroPedido.setCellValueFactory(new PropertyValueFactory("idPedido"));
        colProveedor.setCellValueFactory(new PropertyValueFactory("nombreProveedor"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estadoRastreo"));
    }
    
    private void cargarInformacionTabla() {
        pedidos = FXCollections.observableArrayList();
        PedidoRespuesta respuestaBD = PedidoDAO.obtenerInformacionPedidos();
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
                    pedidos.addAll(respuestaBD.getPedidos());
                    tvPedidos.setItems(pedidos);
                break;
        }
    }
    
    private void irFormularioPedido(boolean esEdicion, Pedido pedido){
        try {
            FXMLLoader accesoControlador = new FXMLLoader
                (SGAPT.class.getResource("vistas/FXMLFormularioPedido1.fxml"));
            Parent vista = accesoControlador.load();
            FXMLFormularioPedidoController formularioPedidoController = accesoControlador.getController();
            formularioPedidoController.inicializarInformacionFormulario(esEdicion, pedido);
            Stage source = (Stage) lbTitulo.getScene().getWindow();
            Stage stagePrincipal = (Stage) source.getScene().getWindow();
            stagePrincipal.setScene(new Scene(vista));
            if (esEdicion)
                stagePrincipal.setTitle("Modificación de pedido");
            else
                stagePrincipal.setTitle("Formulario de pedido");
                
            stagePrincipal.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLFormularioPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalAdmin.fxml"));
        stagePrincipal.setTitle("Home");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnRealizarPedido(ActionEvent event) {
        irFormularioPedido(false, null);
    }

    @FXML
    private void clicBtnModificar(ActionEvent event) {
          int posicion = tvPedidos.getSelectionModel().getSelectedIndex();
          if (posicion != -1) {
              Pedido pedidoSeleccion = pedidos.get(posicion);
                if (pedidoSeleccion.getEstadoRastreo().equals("sin enviar")) {
                    irFormularioPedido(true, pedidos.get(posicion));
                    
                } else if (pedidoSeleccion.getEstadoRastreo().equals("enviado") || 
                        pedidoSeleccion.getEstadoRastreo().equals("con demora")) {
                    Utilidades.mostrarDialogoSimple("Modificación no permitida", 
                        "No es posible modificar el pedido debido a que ya se ha enviado", 
                        Alert.AlertType.WARNING);
                    
                } if (pedidoSeleccion.getEstadoRastreo().equals("entregado")) {
                    Utilidades.mostrarDialogoSimple("Modificación no permitida", 
                        "No es posible modificar el pedido debido a que ya se ha entregado", 
                        Alert.AlertType.WARNING);
                }
          } else {
              Utilidades.mostrarDialogoSimple("Seleccion necesaria", 
                    "Debe seleccionar un pedido previamente", 
                    Alert.AlertType.WARNING);
          }
    }

    @FXML
    private void clicBtnConsultar(ActionEvent event) {
          int posicion = tvPedidos.getSelectionModel().getSelectedIndex();
          if (posicion != -1) {
              irConsultarPedido(posicion);
          } else {
              Utilidades.mostrarDialogoSimple("Seleccion necesaria", 
                    "Debe seleccionar un pedido previamente", 
                    Alert.AlertType.WARNING);
          }
    }
    
    private void irConsultarPedido(int posicion){
        try {
            FXMLLoader accesoControlador = new FXMLLoader
                (SGAPT.class.getResource("vistas/FXMLInformacionPedido1.fxml"));
            Parent vista = accesoControlador.load();
            FXMLInformacionPedidoController informacionPedidoController = accesoControlador.getController();
            informacionPedidoController.inicializarInformacion(pedidos.get(posicion));
            Stage source = (Stage) lbTitulo.getScene().getWindow();
            Stage stagePrincipal = (Stage) source.getScene().getWindow();
            stagePrincipal.setScene(new Scene(vista));
            stagePrincipal.setTitle("Información de pedido");
            stagePrincipal.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLInformacionPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        int posicion = tvPedidos.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            Pedido pedidoSeleccion = pedidos.get(posicion);
            
            if (pedidoSeleccion.getEstadoRastreo().equals("sin enviar")) {
                boolean cancelarPedido = Utilidades.mostrarDialogoConfirmacion("Cancelación de pedido", "¿Está seguro de " + 
                        "que desea cancelar el pedido?");
                
                if (cancelarPedido) {
                    int respuestaLoteDesenlazado = LoteDAO.desenlazarLotesDePedido(pedidoSeleccion.getIdPedido());
                    
                    switch (respuestaLoteDesenlazado) {
                    case Constantes.ERROR_CONEXION:
                            Utilidades.mostrarDialogoSimple("Sin conexión", 
                            "Lo sentimos, por el momento no hay conexión para poder eliminar el pedido", 
                            Alert.AlertType.ERROR);
                        break;
                    case Constantes.ERROR_CONSULTA:
                            Utilidades.mostrarDialogoSimple("Error al eliminar el pedido", 
                            "Hubo un error al eliminar el pedido, por favor inténtelo más tarde", 
                            Alert.AlertType.WARNING);
                        break;
                    case Constantes.OPERACION_EXITOSA:
                            int respuesta = PedidoDAO.
                                    eliminarPedido(pedidoSeleccion.getIdPedido());
                            switch (respuesta) {
                            case Constantes.ERROR_CONEXION:
                                    Utilidades.mostrarDialogoSimple("Sin conexión", 
                                    "Lo sentimos, por el momento no hay conexión para poder eliminar el pedido", 
                                    Alert.AlertType.ERROR);
                                break;
                            case Constantes.ERROR_CONSULTA:
                                    Utilidades.mostrarDialogoSimple("Error al eliminar el pedido", 
                                    "Hubo un error al eliminar el pedido, por favor inténtelo más tarde", 
                                    Alert.AlertType.WARNING);
                                break;
                            case Constantes.OPERACION_EXITOSA:
                                    Utilidades.mostrarDialogoSimple("Operación exitosa", 
                                            "Se ha eliminado el pedido satisfactoriamente", 
                                            Alert.AlertType.INFORMATION);
                                    cargarInformacionTabla();
                                break;
                            }
                        break;
                    }
                }
            } else if (pedidoSeleccion.getEstadoRastreo().equals("enviado") || 
                    pedidoSeleccion.getEstadoRastreo().equals("con demora")) {
                Utilidades.mostrarDialogoSimple("Cancelación no permitida", 
                    "No es posible cancelar el pedido debido a que ya se ha enviado", 
                    Alert.AlertType.WARNING);
                
            } else if (pedidoSeleccion.getEstadoRastreo().equals("entregado")) {
                Utilidades.mostrarDialogoSimple("Cancelación no permitida", 
                    "No es posible cancelar el pedido debido a que ya se ha entregado", 
                    Alert.AlertType.WARNING);
            }
        } else {
            Utilidades.mostrarDialogoSimple("Seleccion necesaria", 
                    "Debe seleccionar un pedido previamente", 
                    Alert.AlertType.WARNING);
        }
    }
    
}
