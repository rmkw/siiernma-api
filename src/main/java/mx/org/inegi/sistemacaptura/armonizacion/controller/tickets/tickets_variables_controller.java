package mx.org.inegi.sistemacaptura.armonizacion.controller.tickets;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tickets.tickets_variables_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.tickets.tickets_variables_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/armo/tickets-variables")
public class tickets_variables_controller {

    @Autowired
    private tickets_variables_service ticketsService;

    @GetMapping("/variable/{idA}")
    public ResponseEntity<?> obtenerPorVariable(@PathVariable String idA) {
        try {
            return ResponseEntity.ok(ticketsService.obtenerPorVariable(idA));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al consultar los tickets: " + e.getMessage());
        }
    }

    @GetMapping("/asignado/{idUsuario}")
    public ResponseEntity<?> obtenerAsignadosA(@PathVariable Long idUsuario) {
        try {
            return ResponseEntity.ok(ticketsService.obtenerAsignadosA(idUsuario));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al consultar los tickets: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtener(@RequestParam(required = false) String estatus) {
        try {
            return ResponseEntity.ok(estatus == null || estatus.trim().isEmpty()
                    ? ticketsService.obtenerTodos()
                    : ticketsService.obtenerPorEstatus(estatus));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al consultar los tickets: " + e.getMessage());
        }
    }

    @GetMapping("/{idTicket}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idTicket) {
        Optional<tickets_variables_dto> ticket = ticketsService
                .obtenerPorId(idTicket);
        return ticket.isPresent()
                ? ResponseEntity.ok(ticket.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe el ticket con id_ticket: " + idTicket);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody tickets_variables_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ticketsService.crear(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el ticket: " + e.getMessage());
        }
    }

    @PutMapping("/{idTicket}")
    public ResponseEntity<?> actualizar(@PathVariable Long idTicket,
            @RequestBody tickets_variables_dto dto) {
        try {
            return ResponseEntity.ok(ticketsService.actualizar(idTicket, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el ticket: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idTicket}")
    public ResponseEntity<?> eliminar(@PathVariable Long idTicket) {
        try {
            ticketsService.eliminar(idTicket);
            return ResponseEntity.ok("Ticket eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el ticket: " + e.getMessage());
        }
    }
}
