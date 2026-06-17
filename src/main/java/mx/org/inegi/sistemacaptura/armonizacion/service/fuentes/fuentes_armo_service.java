/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuente_save_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuentes_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.fuentes.fuentes_armo_repository;
import mx.org.inegi.sistemacaptura.repository.procesos.procesos_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class fuentes_armo_service {

    @Autowired
    private fuentes_armo_repository repository;

    @Autowired
    private procesos_repo procesosRepo;

    @Transactional
    public fuentes_armo_enty createFuente(fuente_save_dto dto) {
        validarDto(dto);

        String acronimo = dto.getAcronimo().trim();
        validarAcronimoExisteEnProcesos(acronimo);

        String fuente = dto.getFuente().trim();
        String url = normalizarTexto(dto.getUrl());
        String edicion = normalizarTexto(dto.getEdicion());
        String comentarioS = normalizarTexto(dto.getComentarioS());
        String comentarioA = normalizarTexto(dto.getComentarioA());
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());

        String idFuente = construirIdFuente(acronimo, fuente, edicion, url);

        if (idFuenteSeleccion == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "idFuenteSeleccion es obligatorio");
        }

        if (repository.existsByIdFuenteSeleccion(idFuenteSeleccion)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La fuente de seleccion ya esta vinculada en armonizacion");
        }

        if (repository.existsByIdFuente(idFuente)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La fuente ya existe en armonizacion");
        }

        repository.insertFuente(
                acronimo,
                fuente,
                url,
                edicion,
                comentarioS,
                comentarioA,
                idFuenteSeleccion);

        return repository.findByIdFuenteSeleccion(idFuenteSeleccion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "La fuente se inserto pero no pudo recuperarse"));
    }

    public Optional<fuentes_armo_enty> getFuenteById(String idFuente) {
        return repository.findByIdFuente(idFuente);
    }

    public boolean existsFuenteById(String idFuente) {
        return repository.existsByIdFuente(idFuente);
    }

    public boolean existsFuenteByIdFuenteSeleccion(String idFuenteSeleccion) {
        return repository.existsByIdFuenteSeleccion(idFuenteSeleccion);
    }

    public Optional<fuentes_armo_enty> getFuenteByIdFuenteSeleccion(
            String idFuenteSeleccion) {
        return repository.findByIdFuenteSeleccion(idFuenteSeleccion);
    }

    @Transactional
    public fuentes_armo_enty updateFuente(fuente_save_dto dto) {
        validarDto(dto);

        String acronimo = dto.getAcronimo().trim();
        validarAcronimoExisteEnProcesos(acronimo);

        String fuente = dto.getFuente().trim();
        String url = normalizarTexto(dto.getUrl());
        String edicion = normalizarTexto(dto.getEdicion());
        String comentarioS = normalizarTexto(dto.getComentarioS());
        String comentarioA = normalizarTexto(dto.getComentarioA());
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());

        if (idFuenteSeleccion == null || idFuenteSeleccion.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "idFuenteSeleccion es obligatorio para actualizar");
        }

        int updated = repository.updateFuenteByIdFuenteSeleccion(
                acronimo,
                fuente,
                url,
                edicion,
                comentarioS,
                comentarioA,
                idFuenteSeleccion);

        if (updated == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Fuente no encontrada en armonizacion");
        }

        return repository.findByIdFuenteSeleccion(idFuenteSeleccion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "La fuente se actualizo pero no pudo recuperarse"));
    }

    public String construirIdFuentePublic(fuente_save_dto dto) {
        validarDto(dto);

        return construirIdFuente(
                dto.getAcronimo(),
                dto.getFuente(),
                dto.getEdicion(),
                dto.getUrl());
    }

    public boolean existsFuenteByData(fuente_save_dto dto) {
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());

        if (idFuenteSeleccion != null) {
            return repository.existsByIdFuenteSeleccion(idFuenteSeleccion);
        }

        String idFuente = construirIdFuentePublic(dto);
        return repository.existsByIdFuente(idFuente);
    }

    public Optional<fuentes_armo_enty> getFuenteByData(fuente_save_dto dto) {
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());

        if (idFuenteSeleccion != null) {
            Optional<fuentes_armo_enty> fuente =
                    repository.findByIdFuenteSeleccion(idFuenteSeleccion);
            if (fuente.isPresent()) {
                return fuente;
            }
        }

        String idFuente = construirIdFuentePublic(dto);
        return repository.findByIdFuente(idFuente);
    }

    private void validarDto(fuente_save_dto dto) {
        if (dto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe proporcionar los datos de la fuente");
        }

        if (dto.getAcronimo() == null || dto.getAcronimo().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        if (dto.getFuente() == null || dto.getFuente().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fuente es obligatoria");
        }
    }

    private String construirIdFuente(
            String acronimo,
            String fuente,
            String edicion,
            String url) {
        return String.format(
                "%s-%s-%s-%s",
                limpiarSegmento(acronimo),
                limpiarSegmento(fuente),
                limpiarSegmento(edicion),
                limpiarSegmento(url));
    }

    private String limpiarSegmento(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim();
    }

    private String normalizarTexto(String valor) {
        if (valor == null) {
            return null;
        }

        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    private void validarAcronimoExisteEnProcesos(String acronimo) {
        if (acronimo == null || acronimo.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        String acronimoLimpio = acronimo.trim();

        if (!procesosRepo.existsById(acronimoLimpio)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo '" + acronimoLimpio
                    + "' no existe en la tabla de procesos de produccion");
        }
    }
}