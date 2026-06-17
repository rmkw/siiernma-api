/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.controller.comentarios_pp;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import mx.org.inegi.sistemacaptura.armonizacion.entity.comentarios_pp.comentarios_pp_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.comentarios_pp.comentarios_pp_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.service.comentarios_pp.comentarios_pp_armo_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/armo/comentarios-pp")
public class comentarios_pp_armo_ctr {

    @Autowired
    private comentarios_pp_armo_services service;

    @GetMapping("/buscar")
    public ResponseEntity<comentarios_pp_armo_enty> obtenerPorAcronimo(
            @RequestParam String acronimo) {
        return ResponseEntity.ok(service.obtenerPorAcronimo(acronimo));
    }

    @PostMapping("/guardar")
    public ResponseEntity<comentarios_pp_armo_enty> guardarComentario(
            @RequestBody comentarios_pp_armo_dto dto) {
        return ResponseEntity.ok(service.guardarComentario(dto));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<comentarios_pp_armo_enty> actualizarComentario(
            @RequestBody comentarios_pp_armo_dto dto) {
        return ResponseEntity.ok(service.actualizarComentario(dto));
    }

    @PostMapping("/guardar-o-actualizar")
    public ResponseEntity<comentarios_pp_armo_enty> guardarOActualizar(
            @RequestBody comentarios_pp_armo_dto dto) {
        return ResponseEntity.ok(service.guardarOActualizar(dto));
    }
}
