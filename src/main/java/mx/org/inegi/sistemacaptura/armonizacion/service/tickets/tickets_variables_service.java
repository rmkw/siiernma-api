package mx.org.inegi.sistemacaptura.armonizacion.service.tickets;

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tickets.tickets_variables_dto;

public interface tickets_variables_service {

    List<tickets_variables_dto> obtenerTodos();

    List<tickets_variables_dto> obtenerPorVariable(String idA);

    List<tickets_variables_dto> obtenerPorEstatus(String estatus);

    List<tickets_variables_dto> obtenerAsignadosA(Long idUsuario);

    Optional<tickets_variables_dto> obtenerPorId(Long idTicket);

    tickets_variables_dto crear(tickets_variables_dto dto);

    tickets_variables_dto actualizar(Long idTicket, tickets_variables_dto dto);

    void eliminar(Long idTicket);
}
