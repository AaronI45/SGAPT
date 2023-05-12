/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgapt.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author king_
 */
public class PromocionRespuesta {
     private int codigoRespuesta;
    private ArrayList<Promocion> promociones;

    public PromocionRespuesta() {
    }

    public PromocionRespuesta(int codigoRespuesta, ArrayList<Promocion> promociones) {
        this.codigoRespuesta = codigoRespuesta;
        this.promociones = promociones;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Promocion> getPromociones() {
        return promociones;
    }

    public void setPromociones(ArrayList<Promocion> promociones) {
        this.promociones = promociones;
    }
}
