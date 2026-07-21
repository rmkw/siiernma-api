package mx.org.inegi.sistemacaptura.armonizacion.entity.tickets;

public class tickets_variables_dto {

    private Long idTicket;
    private String idA;
    private Long idUsuarioReporta;
    private Long idUsuarioAsignado;
    private String incidencia;
    private String propiedad;
    private String estatus;
    private String fechaCreacion;
    private String fechaActualizacion;
    private String fechaResolucion;

    public Long getIdTicket() { return idTicket; }
    public void setIdTicket(Long idTicket) { this.idTicket = idTicket; }
    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }
    public Long getIdUsuarioReporta() { return idUsuarioReporta; }
    public void setIdUsuarioReporta(Long idUsuarioReporta) {
        this.idUsuarioReporta = idUsuarioReporta;
    }
    public Long getIdUsuarioAsignado() { return idUsuarioAsignado; }
    public void setIdUsuarioAsignado(Long idUsuarioAsignado) {
        this.idUsuarioAsignado = idUsuarioAsignado;
    }
    public String getIncidencia() { return incidencia; }
    public void setIncidencia(String incidencia) { this.incidencia = incidencia; }
    public String getPropiedad() { return propiedad; }
    public void setPropiedad(String propiedad) { this.propiedad = propiedad; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    public String getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public String getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(String fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
}
