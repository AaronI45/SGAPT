package sgapt.modelo.pojo;

import java.util.ArrayList;

public class AlmacenRespuesta {
    int codigoRespuesta;
    ArrayList<Almacen> almacenes;

    public AlmacenRespuesta() {
    }

    public AlmacenRespuesta(int codigoRespuesta, ArrayList<Almacen> almacenes) {
        this.codigoRespuesta = codigoRespuesta;
        this.almacenes = almacenes;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(ArrayList<Almacen> almacenes) {
        this.almacenes = almacenes;
    }
    
}
