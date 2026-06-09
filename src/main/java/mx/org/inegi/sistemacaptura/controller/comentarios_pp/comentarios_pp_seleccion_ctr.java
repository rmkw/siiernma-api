/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.comentarios_pp;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import mx.org.inegi.sistemacaptura.entity.comentarios_pp.comentarios_pp_seleccion_dto;
import mx.org.inegi.sistemacaptura.entity.comentarios_pp.comentarios_pp_seleccion_enty;
import mx.org.inegi.sistemacaptura.service.comentarios_pp.comentarios_pp_seleccion_services;
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
@RequestMapping("/api/sele/comentarios-pp")
public class comentarios_pp_seleccion_ctr {

    @Autowired
    private comentarios_pp_seleccion_services service;

    @GetMapping("/buscar")
    public ResponseEntity<comentarios_pp_seleccion_enty> obtenerPorAcronimo(
            @RequestParam String acronimo) {
        return ResponseEntity.ok(service.obtenerPorAcronimo(acronimo));
    }

    @PostMapping("/guardar")
    public ResponseEntity<comentarios_pp_seleccion_enty> guardarComentario(
            @RequestBody comentarios_pp_seleccion_dto dto) {
        return ResponseEntity.ok(service.guardarComentario(dto));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<comentarios_pp_seleccion_enty> actualizarComentario(
            @RequestBody comentarios_pp_seleccion_dto dto) {
        return ResponseEntity.ok(service.actualizarComentario(dto));
    }

    @PostMapping("/guardar-o-actualizar")
    public ResponseEntity<comentarios_pp_seleccion_enty> guardarOActualizar(
            @RequestBody comentarios_pp_seleccion_dto dto) {
        return ResponseEntity.ok(service.guardarOActualizar(dto));
    }
}