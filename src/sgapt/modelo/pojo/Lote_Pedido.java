package sgapt.modelo.pojo;

public class Lote_Pedido {
    private int lote_idLote;
    private int pedido_idPedido;
    private int cantidadLotes;

    public Lote_Pedido() {
    }

    public Lote_Pedido(int lote_idLote, int pedido_idPedido, int cantidadLotes) {
        this.lote_idLote = lote_idLote;
        this.pedido_idPedido = pedido_idPedido;
        this.cantidadLotes = cantidadLotes;
    }

    public int getLote_idLote() {
        return lote_idLote;
    }

    public void setLote_idLote(int lote_idLote) {
        this.lote_idLote = lote_idLote;
    }

    public int getPedido_idPedido() {
        return pedido_idPedido;
    }

    public void setPedido_idPedido(int pedido_idPedido) {
        this.pedido_idPedido = pedido_idPedido;
    }

    public int getCantidadLotes() {
        return cantidadLotes;
    }

    public void setCantidadLotes(int cantidadLotes) {
        this.cantidadLotes = cantidadLotes;
    }

    @Override
    public String toString() {
        return "Lote_Pedido{" + "lote_idLote=" + lote_idLote + ", pedido_idPedido=" + pedido_idPedido + ", cantidadLotes=" + cantidadLotes + '}';
    }
    
}
