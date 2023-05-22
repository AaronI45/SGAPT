package sgapt.modelo.pojo;

import java.util.ArrayList;
import sgapt.modelo.pojo.Pedido;

public class PedidoRespuesta {
    
    private int codigoRespuesta;
    private int idPedido;
    private ArrayList<Pedido> pedidos;

    public PedidoRespuesta() {
    }

    public PedidoRespuesta(int codigoRespuesta, int idPedido, ArrayList<Pedido> pedidos) {
        this.codigoRespuesta = codigoRespuesta;
        this.idPedido = idPedido;
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

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

}
