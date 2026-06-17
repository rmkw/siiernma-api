/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.controller.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.HashMap;
import java.util.Map;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuente_save_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuentes_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.service.fuentes.fuentes_armo_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/armo/fuentes")
public class fuentes_armo_controller {

    @Autowired
    private fuentes_armo_service service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public fuentes_armo_enty createFuente(@RequestBody fuente_save_dto dto) {
        return service.createFuente(dto);
    }

    @GetMapping("/{idFuente}")
    public fuentes_armo_enty getFuenteById(@PathVariable String idFuente) {
        return service.getFuenteById(idFuente)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fuente no encontrada"));
    }

    @GetMapping("/exists/{idFuente}")
    public Map<String, Boolean> existsFuenteById(
            @PathVariable String idFuente) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("exists", service.existsFuenteById(idFuente));
        return response;
    }

    @GetMapping("/exists-by-id-fuente-seleccion/{idFuenteSeleccion}")
    public Map<String, Boolean> existsFuenteByIdFuenteSeleccion(
            @PathVariable String idFuenteSeleccion) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("exists", service.existsFuenteByIdFuenteSeleccion(idFuenteSeleccion));
        return response;
    }

    @PostMapping("/exists-by-data")
    public ResponseEntity<Map<String, Object>> existsFuenteByData(
            @RequestBody fuente_save_dto dto) {
        fuentes_armo_enty fuente = service.getFuenteByData(dto).orElse(null);

        Map<String, Object> response = new HashMap<String, Object>();

        if (fuente != null) {
            response.put("exists", true);
            response.put("idFuente", fuente.getIdFuente());
            response.put("idFuenteSeleccion", fuente.getIdFuenteSeleccion());
            return ResponseEntity.ok(response);
        }

        String idFuenteCalculado = service.construirIdFuentePublic(dto);

        response.put("exists", false);
        response.put("idFuente", idFuenteCalculado);
        response.put("idFuenteSeleccion", dto.getIdFuenteSeleccion());

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public fuentes_armo_enty updateFuente(@RequestBody fuente_save_dto dto) {
        return service.updateFuente(dto);
    }

    @GetMapping("/by-id-fuente-seleccion")
    public fuentes_armo_enty getFuenteByIdFuenteSeleccion(
            @RequestParam String idFuenteSeleccion) {
        return service.getFuenteByIdFuenteSeleccion(idFuenteSeleccion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fuente no encontrada por idFuenteSeleccion"));
    }
}
