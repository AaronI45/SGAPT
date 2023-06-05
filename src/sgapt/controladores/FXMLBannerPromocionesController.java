/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sgapt.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sgapt.util.Utilidades;

/**
 * FXML Controller class
 *
 * @author king_
 */
public class FXMLBannerPromocionesController implements Initializable {

    @FXML
    private AnchorPane panel1;
    @FXML
    private AnchorPane panel2;
    @FXML
    private AnchorPane panel3;
    @FXML
    private AnchorPane panel4;
    @FXML
    private AnchorPane panel5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        /*translateAnimation(0.5, panel2, 1280);
        translateAnimation(0.5, panel3, 1280);*/
        sliderAnimation();
    }
    
    int show=0;
    @FXML
    private void next(ActionEvent event) {
       if(show==0){
           translateAnimation(0.5, panel3,1280);
           show++;
       }else if(show==1){
           translateAnimation(0.5, panel2, 1280);
           show++;
       }
    }

    @FXML
    private void back(ActionEvent event) {
        if(show==1){
           translateAnimation(0.5, panel3, -1280);
           show--;
       }else if(show==2){
           translateAnimation(0.5, panel2, -1280);
           show--;
       }
    }

    @FXML
    private void clicBtnGestionar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLAdministracionPromociones4.fxml"));
        stagePrincipal.setTitle("Administración de promociones");
        stagePrincipal.show();
    }

    @FXML
    private void clicBtnRegresar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stagePrincipal = (Stage) source.getScene().getWindow();
        stagePrincipal.setScene(Utilidades.inicializarEscena("vistas/FXMLMenuPrincipalEncargado1.fxml"));
        stagePrincipal.setTitle("Administración de promociones");
        stagePrincipal.show();
    }
    
    public void translateAnimation(double duracion, Node node, double width){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duracion), node);
        translateTransition.setByX(width);
        translateTransition.play();
    }
    
    private void sliderAnimation(){
        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(3),panel5);
                translateAnimation(0.5, panel5, 450);
                fadeTransition.play();
                
                fadeTransition.setOnFinished(event2 -> {
                   FadeTransition fadeTransition3=new FadeTransition(Duration.seconds(3),panel4);
                   translateAnimation(0.5, panel5, -450);
                   translateAnimation(0.5, panel4, 450);
                   fadeTransition.play();
                   
                   fadeTransition.setOnFinished(event3->{
                      FadeTransition fadeTransition4=new FadeTransition(Duration.seconds(3),panel4);
                      translateAnimation(0.5, panel4, -450);
                      fadeTransition.play();
                      
                      fadeTransition.setOnFinished(event4 ->{
                         sliderAnimation();
                      });
                   });
                });
    }
    
}
