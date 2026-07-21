package mx.org.inegi.sistemacaptura.armonizacion.entity.tickets;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "tickets", schema = "usuarios")
public class tickets_variables_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(name = "id_a", nullable = false)
    private String idA;

    @Column(name = "id_usuario_reporta", nullable = false)
    private Long idUsuarioReporta;

    @Column(name = "id_usuario_asignado")
    private Long idUsuarioAsignado;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String incidencia;

    @Column
    private String propiedad;

    @Column(nullable = false)
    private String estatus;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;

    @PrePersist
    public void antesDeGuardar() {
        LocalDateTime ahora = LocalDateTime.now();
        fechaCreacion = ahora;
        fechaActualizacion = ahora;
        if (estatus == null || estatus.trim().isEmpty()) {
            estatus = "pendiente";
        }
    }

    @PreUpdate
    public void antesDeActualizar() {
        fechaActualizacion = LocalDateTime.now();
    }

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
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public LocalDateTime getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
}
