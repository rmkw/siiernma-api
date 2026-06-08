/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.procesos;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.org.inegi.sistemacaptura.entity.procesos.procesos_dto;
import mx.org.inegi.sistemacaptura.entity.procesos.procesos_enty;
import mx.org.inegi.sistemacaptura.repository.procesos.procesos_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class procesos_services {
    @Autowired
    private procesos_repo repo;

    public List<procesos_enty> obtenerTodos() {
        return repo.findAll();
    }

    public List<procesos_dto> obtenerPorunidad(String unidad) {
        return repo.findProcesosConConteoVariablesByUnidad(unidad);
    }

    public List<procesos_dto> obtenerTodosPorUnidad(String unidad) {
        return repo.findProcesosConConteoVariablesByUnidad(unidad);
    }

    public ResponseEntity<?> registrarProceso(procesos_enty nuevoProceso) {
        String acronimo = nuevoProceso.getAcronimo();

        if (repo.existsById(acronimo)) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("message", "Ya existe un proceso con ese acronimo");

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }

        repo.save(nuevoProceso);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Collections.singletonMap(
                        "message",
                        "Proceso registrado exitosamente"));
    }

    public Long contarProcesos() {
        return repo.count();
    }
}
