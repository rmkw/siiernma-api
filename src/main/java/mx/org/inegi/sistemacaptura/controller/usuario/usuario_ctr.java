/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LUIS.CASTANEDAL
 */
package mx.org.inegi.sistemacaptura.controller.usuario;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.service.usuario.usuario_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class usuario_ctr {

    @Autowired
    private usuario_services service;

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarUsuario(
            @RequestBody usuario_enty usuario) {
        try {
            usuario_enty nuevoUsuario = service.registrarUsuario(usuario);

            Map<String, Object> response = new HashMap<String, Object>();
            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("usuario", nuevoUsuario);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.<String, Object>singletonMap(
                            "error",
                            e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<usuario_enty>> getAllUsuarios() {
        List<usuario_enty> usuarios = service.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<usuario_enty> getUsuarioById(@PathVariable Long id) {
        usuario_enty usuario = service.getUsuarioById(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }
}