package sgapt.modelo.pojo;

import java.util.ArrayList;

public class ProveedorRespuesta {
    private int codigoRespuesta;
    private ArrayList<Proveedor> proveedores;

    public ProveedorRespuesta() {
    }
    
    public ProveedorRespuesta(int codigoRespuesta, ArrayList<Proveedor> proveedores) {
        this.codigoRespuesta = codigoRespuesta;
        this.proveedores = proveedores;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(ArrayList<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    @Override
    public String toString() {
        return "ProveedorRespuesta{" + "codigoRespuesta=" + codigoRespuesta + ", proveedores=" + proveedores + '}';
    }
    
}
