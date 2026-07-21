package mx.org.inegi.sistemacaptura.armonizacion.service.tickets;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tickets.tickets_variables_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tickets.tickets_variables_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.tickets.tickets_variables_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import mx.org.inegi.sistemacaptura.repository.usuario.usuario_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class tickets_variables_service_impl implements tickets_variables_service {

    private static final List<String> ESTATUS_VALIDOS = Arrays.asList(
            "pendiente", "en_proceso", "completado", "cancelado");

    @Autowired
    private tickets_variables_repo ticketsRepo;

    @Autowired
    private variables_armo_repo variablesRepo;

    @Autowired
    private usuario_repo usuariosRepo;

    @Override
    public List<tickets_variables_dto> obtenerTodos() {
        return ticketsRepo.findAllByOrderByFechaCreacionDesc()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<tickets_variables_dto> obtenerPorVariable(String idA) {
        validarTexto(idA, "id_a");
        return ticketsRepo.findByIdAOrderByFechaCreacionDesc(idA)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<tickets_variables_dto> obtenerPorEstatus(String estatus) {
        validarEstatus(estatus);
        return ticketsRepo.findByEstatusOrderByFechaCreacionDesc(estatus)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public List<tickets_variables_dto> obtenerAsignadosA(Long idUsuario) {
        validarUsuario(idUsuario, "id_usuario_asignado");
        return ticketsRepo.findByIdUsuarioAsignadoOrderByFechaCreacionDesc(
                idUsuario)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<tickets_variables_dto> obtenerPorId(Long idTicket) {
        return ticketsRepo.findById(idTicket).map(this::convertirADto);
    }

    @Override
    public tickets_variables_dto crear(tickets_variables_dto dto) {
        validarTicket(dto);
        tickets_variables_enty ticket = convertirAEntidad(dto);
        ticket.setIdTicket(null);
        return convertirADto(ticketsRepo.save(ticket));
    }

    @Override
    public tickets_variables_dto actualizar(Long idTicket,
            tickets_variables_dto dto) {
        validarTicket(dto);
        tickets_variables_enty existente = ticketsRepo.findById(idTicket)
                .orElseThrow(() -> new RuntimeException(
                "No existe el ticket con id_ticket: " + idTicket));

        existente.setIdA(dto.getIdA().trim());
        existente.setIdUsuarioReporta(dto.getIdUsuarioReporta());
        existente.setIdUsuarioAsignado(dto.getIdUsuarioAsignado());
        existente.setIncidencia(dto.getIncidencia().trim());
        existente.setPropiedad(limpiarTexto(dto.getPropiedad()));
        existente.setEstatus(dto.getEstatus().trim());
        actualizarFechaResolucion(existente, dto.getEstatus());
        return convertirADto(ticketsRepo.save(existente));
    }

    @Override
    public void eliminar(Long idTicket) {
        if (!ticketsRepo.existsById(idTicket)) {
            throw new RuntimeException(
                    "No existe el ticket con id_ticket: " + idTicket);
        }
        ticketsRepo.deleteById(idTicket);
    }

    private void validarTicket(tickets_variables_dto dto) {
        if (dto == null) {
            throw new RuntimeException("El ticket no puede ser nulo");
        }
        validarTexto(dto.getIdA(), "id_a");
        validarTexto(dto.getIncidencia(), "incidencia");
        if (dto.getIdUsuarioReporta() == null) {
            throw new RuntimeException("El id_usuario_reporta es obligatorio");
        }
        if (!variablesRepo.existsById(dto.getIdA().trim())) {
            throw new RuntimeException("No existe la variable con id_a: "
                    + dto.getIdA());
        }
        validarUsuario(dto.getIdUsuarioReporta(), "id_usuario_reporta");
        if (dto.getIdUsuarioAsignado() != null) {
            validarUsuario(dto.getIdUsuarioAsignado(), "id_usuario_asignado");
        }
        if (dto.getEstatus() == null || dto.getEstatus().trim().isEmpty()) {
            dto.setEstatus("pendiente");
        }
        validarEstatus(dto.getEstatus());
    }

    private void validarUsuario(Long idUsuario, String campo) {
        if (!usuariosRepo.existsById(idUsuario)) {
            throw new RuntimeException("No existe el usuario de " + campo
                    + ": " + idUsuario);
        }
    }

    private void validarEstatus(String estatus) {
        if (estatus == null || !ESTATUS_VALIDOS.contains(estatus.trim())) {
            throw new RuntimeException("El estatus no es válido");
        }
    }

    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new RuntimeException("El campo " + campo + " es obligatorio");
        }
    }

    private String limpiarTexto(String valor) {
        return valor == null || valor.trim().isEmpty() ? null : valor.trim();
    }

    private void actualizarFechaResolucion(tickets_variables_enty ticket,
            String estatus) {
        if ("completado".equals(estatus) && ticket.getFechaResolucion() == null) {
            ticket.setFechaResolucion(LocalDateTime.now());
        }
        if (!"completado".equals(estatus)) {
            ticket.setFechaResolucion(null);
        }
    }

    private tickets_variables_dto convertirADto(tickets_variables_enty entity) {
        tickets_variables_dto dto = new tickets_variables_dto();
        dto.setIdTicket(entity.getIdTicket());
        dto.setIdA(entity.getIdA());
        dto.setIdUsuarioReporta(entity.getIdUsuarioReporta());
        dto.setIdUsuarioAsignado(entity.getIdUsuarioAsignado());
        dto.setIncidencia(entity.getIncidencia());
        dto.setPropiedad(entity.getPropiedad());
        dto.setEstatus(entity.getEstatus());
        dto.setFechaCreacion(entity.getFechaCreacion() == null ? null
                : entity.getFechaCreacion().toString());
        dto.setFechaActualizacion(entity.getFechaActualizacion() == null ? null
                : entity.getFechaActualizacion().toString());
        dto.setFechaResolucion(entity.getFechaResolucion() == null ? null
                : entity.getFechaResolucion().toString());
        return dto;
    }

    private tickets_variables_enty convertirAEntidad(tickets_variables_dto dto) {
        tickets_variables_enty entity = new tickets_variables_enty();
        entity.setIdA(dto.getIdA().trim());
        entity.setIdUsuarioReporta(dto.getIdUsuarioReporta());
        entity.setIdUsuarioAsignado(dto.getIdUsuarioAsignado());
        entity.setIncidencia(dto.getIncidencia().trim());
        entity.setPropiedad(limpiarTexto(dto.getPropiedad()));
        entity.setEstatus(dto.getEstatus().trim());
        actualizarFechaResolucion(entity, dto.getEstatus());
        return entity;
    }
}
