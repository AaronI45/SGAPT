package sgapt.modelo.pojo;

import java.util.ArrayList;
import sgapt.modelo.pojo.Lote;

public class LoteRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Lote> lotes;

    public LoteRespuesta() {
    }

    public LoteRespuesta(int codigoRespuesta, ArrayList<Lote> lotes) {
        this.codigoRespuesta = codigoRespuesta;
        this.lotes = lotes;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(ArrayList<Lote> lotes) {
        this.lotes = lotes;
    }
    
}
