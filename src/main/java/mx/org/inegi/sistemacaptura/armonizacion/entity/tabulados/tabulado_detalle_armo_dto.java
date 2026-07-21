package mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones.desagregaciones_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses.desgloses_armo_dto;

public class tabulado_detalle_armo_dto {

    private Integer idUnique;
    private String idA;
    private String idTabulado;
    private String comentarioRelacion;
    private tabulados_armo_dto tabulado;
    private List<desgloses_armo_dto> desgloses;
    private List<desagregaciones_armo_dto> desagregaciones;

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }
    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }
    public String getIdTabulado() { return idTabulado; }
    public void setIdTabulado(String idTabulado) { this.idTabulado = idTabulado; }
    public String getComentarioRelacion() { return comentarioRelacion; }
    public void setComentarioRelacion(String comentarioRelacion) {
        this.comentarioRelacion = comentarioRelacion;
    }
    public tabulados_armo_dto getTabulado() { return tabulado; }
    public void setTabulado(tabulados_armo_dto tabulado) {
        this.tabulado = tabulado;
    }
    public List<desgloses_armo_dto> getDesgloses() { return desgloses; }
    public void setDesgloses(List<desgloses_armo_dto> desgloses) {
        this.desgloses = desgloses;
    }
    public List<desagregaciones_armo_dto> getDesagregaciones() {
        return desagregaciones;
    }
    public void setDesagregaciones(
            List<desagregaciones_armo_dto> desagregaciones) {
        this.desagregaciones = desagregaciones;
    }
}
