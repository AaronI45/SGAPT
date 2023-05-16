/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgapt.modelo.pojo;

import java.util.ArrayList;
import sgapt.modelo.pojo.Sucursal;

/**
 *
 * @author super
 */
public class SucursalRespuesta {
    private int codigoRespuesta;
    private ArrayList<Sucursal> sucursales = new ArrayList<>();

    public SucursalRespuesta() {
    }
    
    public SucursalRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(ArrayList<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
    
    
}
