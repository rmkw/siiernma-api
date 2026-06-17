/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.entity.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

public class variables_armo_dto {

    private String idA;
    private String idFuente;
    private String acronimo;
    private String idS;
    private String variableS;
    private String variableA;
    private String url;
    private String pregunta;
    private String definicion;
    private String universo;
    private Integer anioReferencia;
    private String tematica;
    private String tema1;
    private String subtema1;
    private String tema2;
    private String subtema2;
    private Boolean tabulados;
    private Boolean clasificacion;
    private String microdatos;
    private Boolean datosabiertos;
    private Boolean mdea;
    private Boolean ods;
    private String comentarioS;
    private String comentarioA;

    public variables_armo_dto() {
    }

    public variables_armo_dto(String idA, String idFuente, String acronimo,
            String idS, String variableS, String variableA, String url,
            String pregunta, String definicion, String universo,
            Integer anioReferencia, String tematica, String tema1,
            String subtema1, String tema2, String subtema2, Boolean tabulados,
            Boolean clasificacion, String microdatos, Boolean datosabiertos,
            Boolean mdea, Boolean ods, String comentarioS, String comentarioA) {
        this.idA = idA;
        this.idFuente = idFuente;
        this.acronimo = acronimo;
        this.idS = idS;
        this.variableS = variableS;
        this.variableA = variableA;
        this.url = url;
        this.pregunta = pregunta;
        this.definicion = definicion;
        this.universo = universo;
        this.anioReferencia = anioReferencia;
        this.tematica = tematica;
        this.tema1 = tema1;
        this.subtema1 = subtema1;
        this.tema2 = tema2;
        this.subtema2 = subtema2;
        this.tabulados = tabulados;
        this.clasificacion = clasificacion;
        this.microdatos = microdatos;
        this.datosabiertos = datosabiertos;
        this.mdea = mdea;
        this.ods = ods;
        this.comentarioS = comentarioS;
        this.comentarioA = comentarioA;
    }

    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }

    public String getIdFuente() { return idFuente; }
    public void setIdFuente(String idFuente) { this.idFuente = idFuente; }

    public String getAcronimo() { return acronimo; }
    public void setAcronimo(String acronimo) { this.acronimo = acronimo; }

    public String getIdS() { return idS; }
    public void setIdS(String idS) { this.idS = idS; }

    public String getVariableS() { return variableS; }
    public void setVariableS(String variableS) { this.variableS = variableS; }

    public String getVariableA() { return variableA; }
    public void setVariableA(String variableA) { this.variableA = variableA; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getDefinicion() { return definicion; }
    public void setDefinicion(String definicion) { this.definicion = definicion; }

    public String getUniverso() { return universo; }
    public void setUniverso(String universo) { this.universo = universo; }

    public Integer getAnioReferencia() { return anioReferencia; }
    public void setAnioReferencia(Integer anioReferencia) { this.anioReferencia = anioReferencia; }

    public String getTematica() { return tematica; }
    public void setTematica(String tematica) { this.tematica = tematica; }

    public String getTema1() { return tema1; }
    public void setTema1(String tema1) { this.tema1 = tema1; }

    public String getSubtema1() { return subtema1; }
    public void setSubtema1(String subtema1) { this.subtema1 = subtema1; }

    public String getTema2() { return tema2; }
    public void setTema2(String tema2) { this.tema2 = tema2; }

    public String getSubtema2() { return subtema2; }
    public void setSubtema2(String subtema2) { this.subtema2 = subtema2; }

    public Boolean getTabulados() { return tabulados; }
    public void setTabulados(Boolean tabulados) { this.tabulados = tabulados; }

    public Boolean getClasificacion() { return clasificacion; }
    public void setClasificacion(Boolean clasificacion) { this.clasificacion = clasificacion; }

    public String getMicrodatos() { return microdatos; }
    public void setMicrodatos(String microdatos) { this.microdatos = microdatos; }

    public Boolean getDatosabiertos() { return datosabiertos; }
    public void setDatosabiertos(Boolean datosabiertos) { this.datosabiertos = datosabiertos; }

    public Boolean getMdea() { return mdea; }
    public void setMdea(Boolean mdea) { this.mdea = mdea; }

    public Boolean getOds() { return ods; }
    public void setOds(Boolean ods) { this.ods = ods; }

    public String getComentarioS() { return comentarioS; }
    public void setComentarioS(String comentarioS) { this.comentarioS = comentarioS; }

    public String getComentarioA() { return comentarioA; }
    public void setComentarioA(String comentarioA) { this.comentarioA = comentarioA; }
}