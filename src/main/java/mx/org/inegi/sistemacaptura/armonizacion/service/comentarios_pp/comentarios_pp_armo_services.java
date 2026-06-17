/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.comentarios_pp;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.comentarios_pp.comentarios_pp_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.comentarios_pp.comentarios_pp_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.comentarios_pp.comentarios_pp_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class comentarios_pp_armo_services {

    @Autowired
    private comentarios_pp_armo_repo repo;

    public comentarios_pp_armo_enty obtenerPorAcronimo(String acronimo) {
        if (isBlank(acronimo)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Falta el acronimo");
        }

        return repo.findByAcronimo(acronimo.trim())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontro comentario para el acronimo: " + acronimo));
    }

    public comentarios_pp_armo_enty guardarComentario(
            comentarios_pp_armo_dto dto) {
        if (dto == null || isBlank(dto.getAcronimo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        String acronimo = dto.getAcronimo().trim();

        if (repo.existsById(acronimo)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un comentario para ese acronimo. Usa actualizar.");
        }

        comentarios_pp_armo_enty entity = new comentarios_pp_armo_enty();
        entity.setAcronimo(acronimo);
        entity.setComentarioS(dto.getComentarioS());

        return repo.save(entity);
    }

    public comentarios_pp_armo_enty actualizarComentario(
            comentarios_pp_armo_dto dto) {
        if (dto == null || isBlank(dto.getAcronimo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        String acronimo = dto.getAcronimo().trim();

        comentarios_pp_armo_enty existing = repo.findByAcronimo(acronimo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe comentario para actualizar con el acronimo: "
                        + dto.getAcronimo()));

        existing.setComentarioS(dto.getComentarioS());

        return repo.save(existing);
    }

    public comentarios_pp_armo_enty guardarOActualizar(
            comentarios_pp_armo_dto dto) {
        if (dto == null || isBlank(dto.getAcronimo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        String acronimo = dto.getAcronimo().trim();
        Optional<comentarios_pp_armo_enty> existenteOpt =
                repo.findByAcronimo(acronimo);

        comentarios_pp_armo_enty entity;
        if (existenteOpt.isPresent()) {
            entity = existenteOpt.get();
        } else {
            entity = new comentarios_pp_armo_enty();
        }

        entity.setAcronimo(acronimo);
        entity.setComentarioS(dto.getComentarioS());

        return repo.save(entity);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
