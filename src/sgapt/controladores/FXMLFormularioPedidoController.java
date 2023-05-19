package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sgapt.modelo.pojo.Pedido;
import sgapt.util.Utilidades;

public class FXMLFormularioPedidoController implements Initializable {

    @FXML
    private Label lbTitulo;
    private boolean esEdicion;
    private Pedido pedidoEdicion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPedidos.fxml"));
        stagePrincipal.setTitle("Administracion de pedidos");
        stagePrincipal.show();
    }
    
    public void inicializarInformacionFormulario(boolean esEdicion, Pedido pedidoEdicion){
        this.esEdicion = esEdicion;
        this.pedidoEdicion = pedidoEdicion;
        
        if (esEdicion) {
            lbTitulo.setText("Modificación de pedido #"+ pedidoEdicion.getIdPedido());
            cargarInformacionEdicion();
            
        } else {
            lbTitulo.setText("Formulario de pedido");
        }
    }
    
    private void cargarInformacionEdicion() {
        
    }
    
}
