package sgapt.modelo.pojo;

import java.util.ArrayList;
import sgapt.modelo.pojo.Pedido;

public class PedidoRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Pedido> pedidos;

    public PedidoRespuesta() {
    }

    public PedidoRespuesta(int codigoRespuesta, ArrayList<Pedido> pedidos) {
        this.codigoRespuesta = codigoRespuesta;
        this.pedidos = pedidos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "PedidoRespuesta{" + "codigoRespuesta=" + codigoRespuesta + ", pedidos=" + pedidos + '}';
    }

}
