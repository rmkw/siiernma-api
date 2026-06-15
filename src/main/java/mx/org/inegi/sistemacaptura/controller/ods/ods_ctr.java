/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.ods;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_indicador_enty;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_meta_enty;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_objetivo_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_traduccion_dto;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_indicador_repo;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_meta_repo;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_objetivo_repo;
import mx.org.inegi.sistemacaptura.service.ods.ods_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ods")
public class ods_ctr {

    @Autowired
    private ods_services service;

    @Autowired
    private cat_objetivo_repo objetivo_repo;

    @Autowired
    private cat_meta_repo meta_repo;

    @Autowired
    private cat_indicador_repo indicador_repo;

    @GetMapping("/objetivos")
    public List<cat_objetivo_enty> getAllObjetivos() {
        return objetivo_repo.findAll();
    }

    @GetMapping("/metas/{idObjetivo}")
    public List<cat_meta_enty> getMetasByObjetivo(
            @PathVariable Integer idObjetivo) {
        return meta_repo.findByIdObjetivo(idObjetivo);
    }

    @GetMapping("/indicadores/{idObjetivo}/{idMeta}")
    public List<cat_indicador_enty> getIndicadoresByMeta(
            @PathVariable("idObjetivo") Integer idObjetivo,
            @PathVariable String idMeta) {
        return indicador_repo.findByIdObjetivoAndIdMeta(idObjetivo, idMeta);
    }

    @PostMapping
    public ResponseEntity<?> crearRelacion(@RequestBody ods_enty relacion) {
        try {
            ods_enty nueva = service.save(relacion);
            return ResponseEntity.ok(nueva);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<String, String>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/tabla/{idA}")
    public List<ods_traduccion_dto> getTablaPorIdA(@PathVariable String idA) {
        return service.getTablaByIdA(idA);
    }

    @GetMapping("/{idA}")
    public List<ods_enty> obtenerPorIdA(@PathVariable String idA) {
        return service.getByIdA(idA);
    }

    @DeleteMapping("/{id}")
    public void eliminarRelacion(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
