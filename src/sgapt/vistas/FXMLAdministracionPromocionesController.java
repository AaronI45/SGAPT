package sgapt.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sgapt.modelo.pojo.Promocion;

public class FXMLAdministracionPromocionesController implements Initializable {

    @FXML
    private TableColumn columnIdProducto;
    @FXML
    private TableColumn columnPromocion;
    @FXML
    private TableColumn columnFechaInicio;
    @FXML
    private TableColumn columnFechaFin;
    @FXML
    private TableView<Promocion> tvPromocion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
    }

    @FXML
    private void clicBtnCrearProm(ActionEvent event) {
    }
    
}
