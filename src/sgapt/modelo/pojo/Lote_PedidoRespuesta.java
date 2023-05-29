package sgapt.modelo.pojo;

import java.util.ArrayList;

public class Lote_PedidoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Lote_Pedido> lotesPedido;

    public Lote_PedidoRespuesta() {
    }

    public Lote_PedidoRespuesta(int codigoRespuesta, ArrayList<Lote_Pedido> lotesPedido) {
        this.codigoRespuesta = codigoRespuesta;
        this.lotesPedido = lotesPedido;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Lote_Pedido> getLotesPedido() {
        return lotesPedido;
    }

    public void setLotesPedido(ArrayList<Lote_Pedido> lotesPedido) {
        this.lotesPedido = lotesPedido;
    }
    
}
