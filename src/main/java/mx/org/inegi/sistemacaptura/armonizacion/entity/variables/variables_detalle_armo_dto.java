package mx.org.inegi.sistemacaptura.armonizacion.entity.variables;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones.clasificaciones_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.datosabiertos.datos_abiertos_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.microdatos.microdatos_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados.tabulado_detalle_armo_dto;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_enty;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;

public class variables_detalle_armo_dto {

    private variables_armo_dto variable;
    private List<clasificaciones_armo_dto> clasificaciones;
    private List<microdatos_armo_dto> microdatos;
    private List<datos_abiertos_armo_dto> datosAbiertos;
    private List<tabulado_detalle_armo_dto> tabulados;
    private List<mdea_enty> mdeas;
    private List<ods_enty> odsList;
    private pertinencia_enty pertinencia;

    public variables_armo_dto getVariable() { return variable; }
    public void setVariable(variables_armo_dto variable) { this.variable = variable; }
    public List<clasificaciones_armo_dto> getClasificaciones() { return clasificaciones; }
    public void setClasificaciones(List<clasificaciones_armo_dto> clasificaciones) { this.clasificaciones = clasificaciones; }
    public List<microdatos_armo_dto> getMicrodatos() { return microdatos; }
    public void setMicrodatos(List<microdatos_armo_dto> microdatos) { this.microdatos = microdatos; }
    public List<datos_abiertos_armo_dto> getDatosAbiertos() { return datosAbiertos; }
    public void setDatosAbiertos(List<datos_abiertos_armo_dto> datosAbiertos) { this.datosAbiertos = datosAbiertos; }
    public List<tabulado_detalle_armo_dto> getTabulados() { return tabulados; }
    public void setTabulados(List<tabulado_detalle_armo_dto> tabulados) { this.tabulados = tabulados; }
    public List<mdea_enty> getMdeas() { return mdeas; }
    public void setMdeas(List<mdea_enty> mdeas) { this.mdeas = mdeas; }
    public List<ods_enty> getOdsList() { return odsList; }
    public void setOdsList(List<ods_enty> odsList) { this.odsList = odsList; }
    public pertinencia_enty getPertinencia() { return pertinencia; }
    public void setPertinencia(pertinencia_enty pertinencia) { this.pertinencia = pertinencia; }
}
