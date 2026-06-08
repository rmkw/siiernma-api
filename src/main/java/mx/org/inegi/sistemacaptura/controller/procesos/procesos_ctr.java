/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.procesos;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.Collections;
import java.util.List;
import mx.org.inegi.sistemacaptura.entity.procesos.procesos_dto;
import mx.org.inegi.sistemacaptura.entity.procesos.procesos_enty;
import mx.org.inegi.sistemacaptura.service.procesos.procesos_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/procesos")
public class procesos_ctr {
    @Autowired
    private procesos_services service;

    @GetMapping
    public List<procesos_enty> getTodosLosProcesos() {
        return service.obtenerTodos();
    }

    @GetMapping("/buscar")
    public List<procesos_dto> buscarPorunidad(
            @RequestParam(value = "unidad_administrativa", required = false)
            String unidad) {
        if (unidad == null || unidad.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Falta el parametro unidad_administrativa");
        }

        return service.obtenerTodosPorUnidad(unidad);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProceso(
            @RequestBody procesos_enty nuevoProceso) {
        return service.registrarProceso(nuevoProceso);
    }

    @GetMapping("/count")
    public ResponseEntity<?> countProcesos() {
        return ResponseEntity.ok(
                Collections.singletonMap("total", service.contarProcesos()));
    }
}
