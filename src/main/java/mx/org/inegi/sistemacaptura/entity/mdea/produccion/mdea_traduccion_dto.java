/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.mdea.produccion;

/**
 *
 * @author LUIS.CASTANEDAL
 */

public class mdea_traduccion_dto {

    private Integer idUnique;
    private String idA;
    private String idS;
    private String componente;
    private String componenteNombre;
    private String subcomponente;
    private String subcomponenteNombre;
    private String tema;
    private String temaNombre;
    private String estadistica1;
    private String estadistica1Nombre;
    private String estadistica2;
    private String estadistica2Nombre;
    private String contribucion;
    private String comentarioS;

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }

    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }

    public String getIdS() { return idS; }
    public void setIdS(String idS) { this.idS = idS; }

    public String getComponente() { return componente; }
    public void setComponente(String componente) { this.componente = componente; }

    public String getComponenteNombre() { return componenteNombre; }
    public void setComponenteNombre(String componenteNombre) { this.componenteNombre = componenteNombre; }

    public String getSubcomponente() { return subcomponente; }
    public void setSubcomponente(String subcomponente) { this.subcomponente = subcomponente; }

    public String getSubcomponenteNombre() { return subcomponenteNombre; }
    public void setSubcomponenteNombre(String subcomponenteNombre) { this.subcomponenteNombre = subcomponenteNombre; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getTemaNombre() { return temaNombre; }
    public void setTemaNombre(String temaNombre) { this.temaNombre = temaNombre; }

    public String getEstadistica1() { return estadistica1; }
    public void setEstadistica1(String estadistica1) { this.estadistica1 = estadistica1; }

    public String getEstadistica1Nombre() { return estadistica1Nombre; }
    public void setEstadistica1Nombre(String estadistica1Nombre) { this.estadistica1Nombre = estadistica1Nombre; }

    public String getEstadistica2() { return estadistica2; }
    public void setEstadistica2(String estadistica2) { this.estadistica2 = estadistica2; }

    public String getEstadistica2Nombre() { return estadistica2Nombre; }
    public void setEstadistica2Nombre(String estadistica2Nombre) { this.estadistica2Nombre = estadistica2Nombre; }

    public String getContribucion() { return contribucion; }
    public void setContribucion(String contribucion) { this.contribucion = contribucion; }

    public String getComentarioS() { return comentarioS; }
    public void setComentarioS(String comentarioS) { this.comentarioS = comentarioS; }
}